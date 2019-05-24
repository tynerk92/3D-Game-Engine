package objparser;

import java.util.Arrays;

/**
 * Represents a single face on a group.
 * Guaranteed to be a triangle.
 */
public class OBJFace {

    public static final int VERTEX_SIZE = 9;
    public static final int NORMAL_SIZE = 9;
    public static final int TEXTURE_COORD_SIZE = 6;

    private final int[] vertices = new int[3];
    private final int[] normals = new int[3];
    private final int[] textureCoords = new int[3];
    private final MTLMaterial material;

    /**
     * Stores OBJ face declaration into the proper index array.
     *
     * Parameters here come in looking exactly like they do in the .OBJ file
     *
     * Parameters are stored offset by -1 so that these indices can be
     * used directly in the data lists inside OBJLoader
     *
     * @param f1
     * @param f2
     * @param f3
     *
     */
    public OBJFace(int[] f1, int[] f2, int[] f3, MTLMaterial material) {
        vertices[0] = f1[0] - 1;
        vertices[1] = f2[0] - 1;
        vertices[2] = f3[0] - 1;
        textureCoords[0] = f1[1] - 1;
        textureCoords[1] = f2[1] - 1;
        textureCoords[2] = f3[1] - 1;
        normals[0] = f1[2] - 1;
        normals[1] = f2[2] - 1;
        normals[2] = f3[2] - 1;

        this.material = material;
    }

    public int[] getVertices() {
        return vertices;
    }

    public int[] getNormals() {
        return normals;
    }

    public int[] getTextureCoords() {
        return textureCoords;
    }

    public MTLMaterial getMaterial() {
        return material;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof OBJFace) {
            OBJFace other = (OBJFace) obj;
            if (!Arrays.equals(vertices, other.vertices)) {
                return false;
            }
            if (!Arrays.equals(normals, other.normals)) {
                return false;
            }
            if (!Arrays.equals(textureCoords, other.textureCoords)) {
                return false;
            }
            if (!material.equals(other.material)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Array[" + vertices[0] + ", " + vertices[1]  + ", " + vertices[2] + "]");
        builder.append("\n");
        builder.append("Array[" + normals[0] + ", " + normals[1]  + ", " + normals[2] + "]");
        builder.append("\n");
        builder.append("Array[" + textureCoords[0] + ", " + textureCoords[1]  + "]");
        builder.append("\n");
        builder.append(material);
        builder.append("\n");
        return builder.toString();
    }
}
