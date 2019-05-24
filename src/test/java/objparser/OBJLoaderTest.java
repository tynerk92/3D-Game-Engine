package objparser;

import base.BaseTest;
import objparser.loader.OBJLoader;
import org.junit.Test;
import org.lwjglx.util.vector.Vector2f;
import org.lwjglx.util.vector.Vector3f;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OBJLoaderTest extends BaseTest {
    public static final String OBJ_TEST_FILE_PATH = "src/test/resources/models/cube.obj";
    public static final String MTL_TEST_FILE_PATH = "src/test/resources/materials/";

    @Test
    public void testLoader() {
        OBJModel model = OBJLoader.loadOBJFile(OBJ_TEST_FILE_PATH, MTL_TEST_FILE_PATH);

        List<Vector3f> vertices = model.getVertices();
        Map<Integer, Vector3f> normals = model.getNormals();
        Map<Integer, Vector2f> textureCoords = model.getTextureCoords();
        List<Integer> indices = model.getIndices();

        assertEquals("There are an incorrect number of vertices.", 8, vertices.size());
        for (int i = 0; i < normals.size(); i++) {
            if (normals.get(i) != null) {
            }
        }
        assertEquals("There are an incorrect number of normals.", 8, normals.size());
        assertEquals("There are an incorrect number of texture coordinates.", 8, textureCoords.size());
        assertEquals("There are an incorrect number of indices.", 36, indices.size());
        assertEquals("There are an incorrect number of material properties.", 8, vertices.size());

        assertObject(model);
    }

    private void assertObject(OBJModel model) {
        OBJObject cube = model.getObjects().get(0);
        assertEquals("This object has the wrong name.", "cube", cube.getName());
        assertGroup(cube);
    }

    private void assertGroup(OBJObject cube) {
        OBJGroup group = cube.getGroups().get(0);
        assertEquals("The group has the wrong number of indices", 36, group.getIndicesCount());
        assertName(group);
        assertFaces(group);
        assertMaterial(group);
    }

    private void assertName(OBJGroup group) {
        assertEquals("This group has the wrong name.", "cube", group.getName());
    }

    private void assertMaterial(OBJGroup group) {
        MTLMaterial expectedMaterial = new MTLMaterial("cube");
        expectedMaterial.setDiffuseReflectivity(new TestUtils.Vector3f(0.640000f, 0.275014f, 0.592579f));
        expectedMaterial.setAmbientReflectivity(new TestUtils.Vector3f(1.000000f, 1.000000f, 1.000000f));
        expectedMaterial.setSpecularReflectivity(new TestUtils.Vector3f(0.500000f, 0.371402f, 0.475650f));
        expectedMaterial.setSpecularExponent(37.254902f);
        expectedMaterial.setAlpha(1.0f);
        assertEquals("This group has the wrong material.", expectedMaterial.toString().trim(), group.getMaterial().toString().trim());
    }

    private void assertFaces(OBJGroup group) {
        Iterator<OBJFace> iterator = group.getFaces().iterator();
        assertNextFace(group, iterator, new int[]{2,1,1,4,2,2,1,3,3});
        assertNextFace(group, iterator, new int[]{8,4,4,6,5,5,5,6,6});
        assertNextFace(group, iterator, new int[]{5,7,6,2,1,1,1,8,3});
        assertNextFace(group, iterator, new int[]{6,5,5,3,9,7,2,1,1});
        assertNextFace(group, iterator, new int[]{3,9,7,8,10,4,4,11,2});
        assertNextFace(group, iterator, new int[]{1,8,3,8,10,4,5,7,6});
        assertNextFace(group, iterator, new int[]{2,1,1,3,9,7,4,2,2});
        assertNextFace(group, iterator, new int[]{8,4,4,7,12,8,6,5,5});
        assertNextFace(group, iterator, new int[]{5,7,6,6,5,5,2,1,1});
        assertNextFace(group, iterator, new int[]{6,5,5,7,12,8,3,9,7});
        assertNextFace(group, iterator, new int[]{3,9,7,7,12,8,8,10,4});
        assertNextFace(group, iterator, new int[]{1,8,3,4,11,2,8,10,4});
    }

    private void assertNextFace(OBJGroup group, Iterator<OBJFace> iterator, int[] values) {
        OBJFace face = iterator.next();
        OBJFace expected = getFace(group, values);
        assertTrue("The face " + face + " does not belong in the group " + group.getName() + " and does not match " + expected,
                expected.equals(face));
    }

    private OBJFace getFace(OBJGroup group, int... values) {
        int[] array1 = Arrays.copyOfRange(values, 0, 3);
        return new OBJFace(
                array1,
                Arrays.copyOfRange(values, 3, 6),
                Arrays.copyOfRange(values, 6, 9),
        group.getMaterial());
    }

    private static class MyVector extends Vector3f {
        public MyVector(float x, float y, float z) {
            super(x, y, z);
        }

        public MyVector(Vector3f vector) {
            super(vector.x, vector.y, vector.z);
        }

        @Override
        public boolean equals(Object obj) {
            MyVector other = (MyVector) obj;
            if (other.x != x) return false;
            if (other.y != y) return false;
            if (other.z != z) return false;
            return true;
        }
    }
}