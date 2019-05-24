package shaders;

import entities.Camera;
import entities.Light;
import objparser.MTLMaterial;
import org.lwjgl.opengl.GL31;
import org.lwjglx.util.vector.Matrix4f;
import toolbox.Maths;

import java.util.ArrayList;
import java.util.List;

public class StaticShader extends ShaderProgram {

    private static List<Integer> ubos = new ArrayList<>();

    private static final String VERTEX_FILE = "src/main/resources/shaders/vertexShader.vert";
    private static final String FRAGMENT_FILE = "src/main/resources/shaders/fragmentShader.frag";

    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_lightPosition;
    private int location_lightColor;
    private int location_materialData;

    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_lightPosition = super.getUniformLocation("lightPosition");
        location_lightColor = super.getUniformLocation("lightColor");
        location_materialData = super.getUniformLocation("materialData");
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
        super.bindAttribute(2, "normal");
    }

    public void loadTransformationMatrix(Matrix4f matrix) {
        super.loadMatrix(location_transformationMatrix, matrix);
    }

    public void loadProjectionMatrix(Matrix4f matrix) {
        super.loadMatrix(location_projectionMatrix, matrix);
    }

    public void loadViewMatrix(Camera camera) {
        super.loadMatrix(location_viewMatrix, Maths.createViewMatrix(camera));
    }

    public void loadLight(Light light) {
        super.loadVector(location_lightPosition, light.getPosition());
        super.loadVector(location_lightColor, light.getColor());
    }

    public void loadMaterialData(MTLMaterial material) {
//        if (material != null) {
//            super.loadFloatArray(location_materialData, material.getData());
//        }

        super.loadFloat(location_materialData, material.getDiffuseReflectivity().getX());
    }

    protected int loadMaterialUniformBuffers() {
        int index = GL31.glGetUniformBlockIndex(getProgramID(), "Material");
        GL31.glUniformBlockBinding(getProgramID(), index, 0);
        int uboID = GL31.glGenBuffers();
        ubos.add(uboID);
        GL31.glBindBuffer(GL31.GL_UNIFORM_BUFFER, uboID);
        GL31.glBufferData(GL31.GL_UNIFORM_BUFFER, 96, GL31.GL_DYNAMIC_DRAW);
        GL31.glBindBuffer(GL31.GL_UNIFORM_BUFFER, 0);
        GL31.glBindBufferBase(GL31.GL_UNIFORM_BUFFER, 0, uboID);
        return uboID;
    }

    @Override
    public void cleanUp() {
        super.cleanUp();
        for (int ubo : ubos) {
            GL31.glDeleteBuffers(ubo);
        }
    }
}
