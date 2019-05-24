package objparser;

import base.BaseTest;
import objparser.loader.MTLLoader;
import org.junit.Test;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class MTLLoaderTest extends BaseTest {
    @Test
    public void testLoader() throws IOException {
        Map<String, MTLMaterial> materialMap = MTLLoader.loadMTLFile("src/test/resources/materials/tree.mtl");
        assertEquals("There are not enough materials in the map.", 2, materialMap.keySet().size());
        Iterator iterator = materialMap.keySet().iterator();

        MTLMaterial barkMaterial = materialMap.get(iterator.next());
        assertEquals("The material has the wrong name.", "Bark", barkMaterial.getName());
        assertEquals("The diffuse reflectivity is wrong.", new TestUtils.Vector3f(0.207595f, 0.138513f, 0.055181f), barkMaterial.getDiffuseReflectivity());
        assertEquals("The ambient reflectivity is wrong.", new TestUtils.Vector3f(0, 0, 0), new TestUtils.Vector3f(barkMaterial.getAmbientReflectivity()));
        assertEquals("The specular reflectivity is wrong.", new TestUtils.Vector3f(0, 0, 0.953f), new TestUtils.Vector3f(barkMaterial.getSpecularReflectivity()));
        assertEquals(1f, barkMaterial.getAlpha(), 0f);
        assertEquals(256f, barkMaterial.getSpecularExponent(), 0f);

        MTLMaterial treeMaterial = materialMap.get(iterator.next());
        assertEquals("The material has the wrong name.", "Tree", treeMaterial.getName());
        assertEquals("The diffuse reflectivity is wrong.", new TestUtils.Vector3f(0.256861f, 0.440506f, 0.110769f), new TestUtils.Vector3f(treeMaterial.getDiffuseReflectivity()));
        assertEquals("The ambient reflectivity is wrong.", new TestUtils.Vector3f(0, 0, 0), new TestUtils.Vector3f(treeMaterial.getAmbientReflectivity()));
        assertEquals("The specular reflectivity is wrong.", new TestUtils.Vector3f(0, 0, 0.753f), new TestUtils.Vector3f(treeMaterial.getSpecularReflectivity()));
        assertEquals(0.3f, treeMaterial.getAlpha(), 0f);
        assertEquals(15f, treeMaterial.getSpecularExponent(), 0f);
    }
}