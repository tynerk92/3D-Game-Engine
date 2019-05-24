package objparser;

import java.util.LinkedList;

/**
 * Represents a group of rendered material such as a window or a wall.
 * Guaranteed to have one material per group.
 */
public class OBJGroup {
    private String name;
    private MTLMaterial material;
    private LinkedList<OBJFace> faces;
    private int indicesCount;

    public OBJGroup(String name) {
        faces = new LinkedList<>();
        this.name = name;
    }

    public MTLMaterial getMaterial() {
        return material;
    }

    public void setMaterial(MTLMaterial material) {
        this.material = material;
    }

    public void addToFaces(OBJFace face) {
        faces.add(face);
    }

    public LinkedList<OBJFace> getFaces() {
        return faces;
    }

    public String getName() {
        return name;
    }

    public void addToIndicesCount(int length) {
        indicesCount += length;
    }

    public int getIndicesCount() {
        return indicesCount;
    }
}
