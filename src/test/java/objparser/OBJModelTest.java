package objparser;

import base.BaseTest;
import org.junit.Test;
import org.lwjglx.util.vector.Vector2f;
import org.lwjglx.util.vector.Vector3f;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

public class OBJModelTest extends BaseTest {

    OBJModel model = new OBJModel();

    @Override
    public void beforeTest() {
        super.beforeTest();

        model.addToVertices(new Vector3f(1, 0, 0));
        model.addToVertices(new Vector3f(0, 1, 0));
        model.addToVertices(new Vector3f(0, 0, 1));

        model.putInNormals(0, new Vector3f(1, 0, 0));
        model.putInNormals(1, new Vector3f(0, 1, 0));
        model.putInNormals(2, new Vector3f(0, 0, 1));

        model.putInTextureCoords(0, new Vector2f(1, 0));
        model.putInTextureCoords(1, new Vector2f(0, 1));

        model.addToIndices(0);
        model.addToIndices(1);
        model.addToIndices(2);

        model.addToIndices(2);
        model.addToIndices(3);
        model.addToIndices(0);
    }

    @Test
    public void testGetVertexArray() {
        assertTrue(Arrays.equals(new float[]{1, 0, 0, 0, 1, 0, 0, 0, 1}, model.getVertexArray()));
    }

    @Test
    public void testGetNormalArray() {
        assertTrue(Arrays.equals(new float[]{1, 0, 0, 0, 1, 0, 0, 0, 1}, model.getNormalArray()));
    }

    @Test
    public void testGetTextureCoordArray() {
        assertTrue(Arrays.equals(new float[]{1, 0, 0, 1}, model.getTextureCoordArray()));
    }

    @Test
    public void testGetIndexArray() {
        assertTrue(Arrays.equals(new int[]{0, 1, 2, 2, 3, 0}, model.getIndexArray()));
    }
}
