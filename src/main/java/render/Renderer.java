package render;

import entities.Entity;
import models.TexturedModel;
import objparser.MTLMaterial;
import objparser.OBJGroup;
import objparser.OBJModel;
import objparser.OBJObject;
import org.lwjgl.opengl.*;
import org.lwjglx.util.vector.Matrix4f;
import shaders.StaticShader;
import toolbox.Maths;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Renderer {

    private static final List<Integer> ubos = new ArrayList<>();

    private static final float FOV = 70;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 1000;

    private Matrix4f projectionMatrix;
    private StaticShader shader;

    public Renderer(StaticShader shader) {
        this.shader = shader;
//        GL11.glEnable(GL11.GL_CULL_FACE);
//        GL11.glCullFace(GL11.GL_BACK);
        createProjectionMatrix();
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }

    public void render(Map<TexturedModel, List<Entity>> entities) {
        for (TexturedModel texturedModel : entities.keySet()) {
            OBJModel objModel = texturedModel.getOBJModel();
            prepareModel(texturedModel);
            List<Entity> batch = entities.get(texturedModel);
            for (Entity entity : batch) {
                loadEntityUniforms(entity);
                for (OBJObject object : objModel.getObjects()) {
                    OBJGroup previousGroup = null;
                    for (OBJGroup group : object.getGroups()) {
                        mapMaterialDataToUniformBuffer(group.getMaterial());
                        GL11.glDrawElements(GL11.GL_TRIANGLES, group.getIndicesCount(), GL11.GL_UNSIGNED_INT, previousGroup == null ? 0 : previousGroup.getIndicesCount() * Float.BYTES);
                        previousGroup = group;
                    }
                }
            }
            unbindTexturedModel();
        }
    }


    private void prepareModel(TexturedModel model) {
        GL30.glBindVertexArray(model.getVaoID());
        GL20.glEnableVertexAttribArray(0); // vertices
        GL20.glEnableVertexAttribArray(1); // texture coordinates
        GL20.glEnableVertexAttribArray(2); // normals
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getTextureID());
    }

    private void unbindTexturedModel() {
        GL20.glDisableVertexAttribArray(2); // normals
        GL20.glDisableVertexAttribArray(1); // texture coordinates
        GL20.glDisableVertexAttribArray(0); // vertices
        GL30.glBindVertexArray(0);
    }

    private void loadEntityUniforms(Entity entity) {
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(
                entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
        shader.loadTransformationMatrix(transformationMatrix);
    }

    private void mapMaterialDataToUniformBuffer(MTLMaterial material) {
        // Edit buffer data
        FloatBuffer buffer = GL31.glMapBuffer(GL31.GL_UNIFORM_BUFFER, GL31.GL_WRITE_ONLY).asFloatBuffer();
        buffer.rewind();
        buffer.put(material.getData());
        buffer.flip();
        GL31.glUnmapBuffer(GL31.GL_UNIFORM_BUFFER);
    }

    public void prepare() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    }

    private void createProjectionMatrix() {
        float aspectRatio = (float) 1280 / (float) 720;
        float y_scale = (float)(1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio;
        float x_scale =  y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;

        projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
        projectionMatrix.m33 = 0;
    }

    public void cleanUp() {
        for (int ubo : ubos) {
            GL31.glDeleteBuffers(ubo);
        }
    }
}
