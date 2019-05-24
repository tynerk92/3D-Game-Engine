package objparser.loader;

import objparser.MTLMaterial;
import org.lwjglx.util.vector.Vector3f;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class MTLLoader {

    private final static Logger LOGGER = Logger.getLogger(MTLLoader.class.getName());
    private static final String TOKEN_NEWMTL = "newmtl";
    private static final String TOKEN_DIFFUSE = "Kd";
    private static final String TOKEN_AMBIENT = "Ka";
    private static final String TOKEN_SPECULAR = "Ks";
    private static final String TOKEN_ALPHA = "d";
    private static final String TOKEN_EXPONENT = "Ns";
    private static final String TOKEN_COMMENT = "#";

    public static Map<String, MTLMaterial> loadMTLFile(String fileName) throws IOException {
        Map<String, MTLMaterial> materials = new HashMap();

        BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));
            String currentLine;
            MTLMaterial currentMaterial = null;
            while ((currentLine = reader.readLine()) != null) {
                String[] tokens = currentLine.split(" ");
                switch (tokens[0]) {
                    case TOKEN_NEWMTL:
                        materials.put(tokens[1], new MTLMaterial(tokens[1]));
                        currentMaterial = materials.get(tokens[1]);
                        break;
                    case TOKEN_AMBIENT:
                        assert currentMaterial != null;
                        materials.get(currentMaterial.getName()).setAmbientReflectivity(new Vector3f(
                                Float.parseFloat(tokens[1]),
                                Float.parseFloat(tokens[2]),
                                Float.parseFloat(tokens[3])));
                        break;
                    case TOKEN_DIFFUSE:
                        assert currentMaterial != null;
                        materials.get(currentMaterial.getName()).setDiffuseReflectivity(new Vector3f(
                                Float.parseFloat(tokens[1]),
                                Float.parseFloat(tokens[2]),
                                Float.parseFloat(tokens[3])));
                        break;
                    case TOKEN_SPECULAR:
                        assert currentMaterial != null;
                        materials.get(currentMaterial.getName()).setSpecularReflectivity(new Vector3f(
                                Float.parseFloat(tokens[1]),
                                Float.parseFloat(tokens[2]),
                                Float.parseFloat(tokens[3])));
                        break;
                    case TOKEN_EXPONENT:
                        assert currentMaterial != null;
                        materials.get(currentMaterial.getName()).setSpecularExponent(Float.parseFloat(tokens[1]));
                        break;
                    case TOKEN_ALPHA:
                        assert currentMaterial != null;
                        materials.get(currentMaterial.getName()).setAlpha(Float.parseFloat(tokens[1]));
                        break;
                    case TOKEN_COMMENT:
                        // Do nothing
                        break;
                    default:
                        if (!currentLine.startsWith(TOKEN_COMMENT)) {
                            LOGGER.fine("Skipping: " + currentLine);
                        }
                        break;
                }
            }
        return materials;
    }
}
