package objparser;

import org.lwjglx.util.vector.Vector2f;
import org.lwjglx.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a group of objects such as a city by a group of arrays that will be converted into Vertex Buffer Objects.
 */
public class OBJModel {
    private final List<OBJObject> objects = new ArrayList<>();

    private List<Vector3f> vertices = new ArrayList<>();
    private Map<Integer, Vector3f> normals;
    private Map<Integer, Vector2f> textureCoords;
    private List<Integer> indices;

    public OBJModel() {
        normals = new HashMap<>();
        textureCoords = new HashMap<>();
        indices = new ArrayList<>();
    }

    public void addObject(OBJObject object) {
        objects.add(object);
    }

    public void addToVertices(Vector3f vertex) {
        vertices.add(vertex);
    }

    public void putInNormals(int index, Vector3f normal) {
        normals.put(index, normal);
    }

    public void putInTextureCoords(int index, Vector2f textureCoord) {
        textureCoords.put(index, textureCoord);
    }

    public void addToIndices(Integer index) {
        indices.add(index);
    }

    public float[] getVertexArray() {
        float[] data = new float[vertices.size() * 3];
        int index = 0;
        for (Vector3f vertex : vertices) {
            data[index++] = vertex.x;
            data[index++] = vertex.y;
            data[index++] = vertex.z;
        }
        return data;
    }

    public float[] getNormalArray() {
        float[] data = new float[normals.size() * 3];
        int index = 0;
        for (Integer mapIndex : normals.keySet()) {
            Vector3f normal = normals.get(mapIndex);
            data[index++] = normal.x;
            data[index++] = normal.y;
            data[index++] = normal.z;
        }
        return data;
    }

    public float[] getTextureCoordArray() {
        float[] data = new float[textureCoords.size() * 2];
        int index = 0;
        for (Integer mapIndex : textureCoords.keySet()) {
            Vector2f textureCoord = textureCoords.get(mapIndex);
            data[index++] = textureCoord.x;
            data[index++] = textureCoord.y;
        }
        return data;
    }

    public int[] getIndexArray() {
        return indices.stream()
                .mapToInt(Integer::intValue)
                .toArray();
    }

    public List<OBJObject> getObjects() {
        return objects;
    }

    public List<Vector3f> getVertices() {
        return vertices;
    }

    public Map<Integer, Vector3f> getNormals() {
        return normals;
    }

    public Map<Integer, Vector2f> getTextureCoords() {
        return textureCoords;
    }

    public List<Integer> getIndices() {
        return indices;
    }
}
