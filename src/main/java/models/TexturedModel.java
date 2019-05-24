package models;

import objparser.OBJModel;
import textures.ModelTexture;

public class TexturedModel {
    private int vaoID;
    private final OBJModel objModel;
    private final ModelTexture texture;

    public TexturedModel(int vaoID, OBJModel objModel, ModelTexture texture) {
        this.vaoID = vaoID;
        this.objModel = objModel;
        this.texture = texture;
    }

    public OBJModel getOBJModel() {
        return objModel;
    }

    public ModelTexture getTexture() {
        return texture;
    }

    public int getVaoID() {
        return vaoID;
    }
}
