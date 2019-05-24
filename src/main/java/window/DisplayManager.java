package window;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class DisplayManager {

    private static long window;

    /**
     * Create a display window using native calls.
     *
     * @param width The width of the display window
     * @param height The height of the display window
     */
    public static void createDisplay(int width, int height) {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // The window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // The window will be resizable

        window = glfwCreateWindow(width, height, "Hello World!", NULL, NULL);

        if (window == NULL) throw new RuntimeException("Failed to create the GLFW window.");

        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                glfwSetWindowShouldClose(window, true);
            }
        });

        glfwSetCursorPosCallback(window,(window, xpos, ypos)-> {
                System.out.println("x:"+xpos+"  y:"+ypos);
        });

        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            glfwGetWindowSize(window, pWidth, pHeight);

            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        }

        glfwMakeContextCurrent(window);
        GL.createCapabilities();

        glfwSwapInterval(1);

        glfwShowWindow(window);
    }

    /**
     * Free all resources consumed by the window
     */
    public static void cleanUp() {
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    /**
     * Returns true if the user has requested to close the window.
     * @return true if the user has requested to close the window.
     */
    public static boolean closeRequested() {
        return glfwWindowShouldClose(window);
    }

    /**
     * Swap to the behind-the-scene buffer.
     * This is used in double-buffered contexts only.
     */
    public static void swapBuffers() {
        glfwSwapBuffers(window);
    }

    public static long getWindow() {
        return window;
    }
}
