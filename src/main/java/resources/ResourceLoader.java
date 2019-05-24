package resources;

import models.TexturedModel;
import objparser.OBJModel;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import textures.ModelTexture;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class ResourceLoader {

    private List<Integer> vaos = new ArrayList<>();
    private List<Integer> vbos = new ArrayList<>();
    private List<Integer> textures = new ArrayList<>();

    public TexturedModel loadOBJModelToVAO(OBJModel model, int textureID) {
        int vaoID = createVAO();
        vaos.add(vaoID);
        bindIndicesBuffer(model.getIndexArray());
        storeDataInAttributeList(0, 3, model.getVertexArray());
        storeDataInAttributeList(1, 2, model.getTextureCoordArray());
        storeDataInAttributeList(2, 3, model.getNormalArray());
        unbindVAO();
        return new TexturedModel(vaoID, model, new ModelTexture(textureID));
    }

    private void bindIndicesBuffer(int[] indices) {
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, storeDataInIntBuffer(indices), GL15.GL_STATIC_DRAW);
    }

    private int createVAO() {
        int vaoID = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoID);
        return vaoID;
    }

    private void storeDataInAttributeList(int attributeNumber, int coordinateSize, float[] data) {
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, storeDataInFloatBuffer(data), GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attributeNumber, coordinateSize, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    private void unbindVAO() {
        GL30.glBindVertexArray(0);
    }

    private FloatBuffer storeDataInFloatBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    private IntBuffer storeDataInIntBuffer(int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    public void cleanUp() {
        vaos.forEach(vaoID -> GL30.glDeleteVertexArrays(vaoID));
        vbos.forEach(vboID -> GL15.glDeleteBuffers(vboID));
        textures.forEach(textureID -> GL11.glDeleteTextures(textureID));
    }

    public int loadTexture(String fileName) {
        Texture texture = null;

        try {
            texture = TextureLoader.getTexture("PNG", new FileInputStream("src/main/resources/textures/" + fileName + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int textureID = texture.getTextureID();
        textures.add(textureID);
        return textureID;
    }
}
