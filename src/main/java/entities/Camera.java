package entities;

import static org.lwjgl.glfw.GLFW.*;
import org.lwjglx.util.vector.Vector3f;
import window.DisplayManager;

public class Camera {
    private Vector3f position = new Vector3f();
    private float pitch;
    private float yaw;
    private float roll;

    public void move() {
        if (glfwGetKey(DisplayManager.getWindow(), GLFW_KEY_W) == GLFW_PRESS) {
            position.z -= 0.06f;
        }
        if (glfwGetKey(DisplayManager.getWindow(), GLFW_KEY_A) == GLFW_PRESS) {
            position.x -= 0.06f;
        }
        if (glfwGetKey(DisplayManager.getWindow(), GLFW_KEY_S) == GLFW_PRESS) {
            position.z += 0.06f;
        }
        if (glfwGetKey(DisplayManager.getWindow(), GLFW_KEY_D) == GLFW_PRESS) {
            position.x += 0.06f;
        }
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }
}
