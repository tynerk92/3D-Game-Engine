package objparser.loader;

import objparser.*;
import org.lwjglx.util.vector.Vector2f;
import org.lwjglx.util.vector.Vector3f;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OBJLoader {
    private static final String TOKEN_VERTEX = "v";
    private static final String TOKEN_TEXTURE = "vt";
    private static final String TOKEN_NORMAL = "vn";
    private static final String TOKEN_FACE = "f";
    private static final String TOKEN_OBJECT = "o";
    private static final String TOKEN_GROUP = "g";
    private static final String TOKEN_USEMTL = "usemtl";
    private static final String TOKEN_MATERIAL_LIB = "mtllib";
    private static final String TOKEN_COMMENT = "#";

    public static OBJModel loadOBJFile(String objFileName, String mtlPath) {
        OBJModel model = new OBJModel();
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(objFileName)))) {
            Map<String, MTLMaterial> materialMap = null;

            List<Vector2f> textureCoords = new ArrayList<>();
            List<Vector3f> normals = new ArrayList<>();

            OBJObject currentObject = null;
            OBJGroup currentGroup = null;

            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                String[] tokens = currentLine.split(" ");
                switch(tokens[0]) {
                    case TOKEN_VERTEX:
                        /*
                         * Add the vertices straight to the model
                         * because everything is based on the vertex positions
                         */
                        model.addToVertices(new Vector3f(
                                Float.parseFloat(tokens[1]),
                                Float.parseFloat(tokens[2]),
                                Float.parseFloat(tokens[3])));
                        break;
                    case TOKEN_TEXTURE:
                        textureCoords.add(new Vector2f(
                                Float.parseFloat(tokens[1]),
                                Float.parseFloat(tokens[2])));
                        break;
                    case TOKEN_NORMAL:
                        normals.add(new Vector3f(
                                Float.parseFloat(tokens[1]),
                                Float.parseFloat(tokens[2]),
                                Float.parseFloat(tokens[3])));
                        break;
                    case TOKEN_FACE:
                        if (currentGroup == null) {
                            currentGroup = new OBJGroup("nogroup");
                        }
                        addFaceToCurrentGroup(currentGroup, tokens);
                        break;
                    case TOKEN_OBJECT:
                        if (currentObject != null) {
                            model.addObject(currentObject); // Add previous object to our model
                        }
                        currentObject = new OBJObject(tokens[1]);
                        break;
                    case TOKEN_GROUP:
                        if (currentGroup != null) {
                            currentObject.addGroup(currentGroup); // Add previous group to our object
                        }
                        currentGroup = new OBJGroup(tokens[1]);
                        break;
                    case TOKEN_USEMTL:
                        if (currentGroup == null) {
                            currentGroup = new OBJGroup("group_no_name");
                        }
                        currentGroup.setMaterial((materialMap != null) ? materialMap.get(tokens[1]) : null);
                        break;
                    case TOKEN_MATERIAL_LIB:
                         try {
                             materialMap = MTLLoader.loadMTLFile(mtlPath + tokens[1]);
                         } catch (FileNotFoundException e) {
                            e.printStackTrace();
                         }
                        break;
                    case TOKEN_COMMENT:
                        // Do Nothing
                        break;
                    default:
                        break;
                }
            }

            // Add trailing data to our model
            assert currentObject != null;
            assert currentGroup != null;
            currentObject.addGroup(currentGroup);
            model.addObject(currentObject);

            for (OBJObject object : model.getObjects())
                for (OBJGroup group : object.getGroups())
                    for (OBJFace face : group.getFaces()) {
                        int[] vertexPointers = face.getVertices();
                        int[] normalPointers = face.getNormals();
                        int[] textureCoordPointers = face.getTextureCoords();

                        // Add normals in the same position as the corresponding vertex
                        model.putInNormals(vertexPointers[0], normals.get(normalPointers[0]));
                        model.putInNormals(vertexPointers[1], normals.get(normalPointers[1]));
                        model.putInNormals(vertexPointers[2], normals.get(normalPointers[2]));

                        model.putInTextureCoords(vertexPointers[0], textureCoords.get(textureCoordPointers[0]));
                        model.putInTextureCoords(vertexPointers[1], textureCoords.get(textureCoordPointers[1]));
                        model.putInTextureCoords(vertexPointers[2], textureCoords.get(textureCoordPointers[2]));

                        // Each face point is based on the vertex index
                        model.addToIndices(vertexPointers[0]);
                        model.addToIndices(vertexPointers[1]);
                        model.addToIndices(vertexPointers[2]);
                    }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return model;
    }

    private static void addFaceToCurrentGroup(OBJGroup currentGroup, String[] tokens) {
        List<int[]> data = new ArrayList<>();
        currentGroup.addToIndicesCount(tokens.length - 1);
        // Currently, we only support triangles
        for (int i = 1; i < tokens.length; i++) {
            String[] elements = tokens[i].split("/");
            int vertexIndex = Integer.parseInt(elements[0]);
            int textureIndex = Integer.parseInt(elements[1]);
            int normalIndex = Integer.parseInt(elements[2]);
            data.add(new int[]{vertexIndex, textureIndex, normalIndex});
        }
        MTLMaterial material = currentGroup.getMaterial();
        currentGroup.addToFaces(new OBJFace(data.get(0), data.get(1), data.get(2), material));
    }
}
