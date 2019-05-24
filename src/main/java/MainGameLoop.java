import entities.Camera;
import entities.Entity;
import entities.Light;
import models.TexturedModel;
import objparser.OBJModel;
import objparser.loader.OBJLoader;
import org.lwjgl.opengl.GL;
import org.lwjglx.util.vector.Vector3f;
import render.MasterRenderer;
import resources.ResourceLoader;
import textures.ModelTexture;
import window.DisplayManager;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;

public class MainGameLoop {

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    public static void main(String[] args) {
        DisplayManager.createDisplay(WIDTH, HEIGHT);
        GL.createCapabilities();
        ResourceLoader loader = new ResourceLoader();

        Camera camera = new Camera();
        Light light = new Light(new Vector3f(0, 0, 20f), new Vector3f(1f, 1f, 1f));
        ModelTexture texture = new ModelTexture(loader.loadTexture("texture"));
        OBJModel objModel = OBJLoader.loadOBJFile("src/main/resources/models/tree.obj", "src/main/resources/materials/");
        TexturedModel texturedModel = loader.loadOBJModelToVAO(objModel, texture.getTextureID());
        Entity entity = new Entity(texturedModel, new Vector3f(0, 0, 0), 0, 0, 0, 1);

        MasterRenderer renderer = new MasterRenderer();
        while (!DisplayManager.closeRequested()) {
            entity.increaseRotation(0, 0.2f, 0);
            camera.move();

            renderer.processEntity(entity);
            renderer.render(light, camera);

            DisplayManager.swapBuffers();

            // Poll for window events. The key callback above will only be invoked during this call.
            glfwPollEvents();
        }

        loader.cleanUp();
        renderer.cleanUp();
        DisplayManager.cleanUp();
    }
}
