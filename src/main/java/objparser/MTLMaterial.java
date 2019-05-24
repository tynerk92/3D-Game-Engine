package objparser;

import org.lwjglx.util.vector.Vector3f;

public class MTLMaterial {

    public static final int DATA_SIZE = 12;

    private float alpha;
    private float specularExponent;
    private String name;
    private Vector3f ambientReflectivity;
    private Vector3f diffuseReflectivity;
    private Vector3f specularReflectivity;

    public MTLMaterial(String name) {
        this.name = name;
    }

    public float[] getData() {
        return new float[]{
            diffuseReflectivity.getX(),
            diffuseReflectivity.getY(),
            diffuseReflectivity.getZ(), 1.0f,
//            specularReflectivity.getX(),
//            specularReflectivity.getY(),
//            specularReflectivity.getZ(), specularExponent,
//            ambientReflectivity.getX(),
//            ambientReflectivity.getY(),
//            ambientReflectivity.getZ(), 1
        };
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public void setAmbientReflectivity(Vector3f ambientReflectivity) {
        this.ambientReflectivity = ambientReflectivity;
    }

    public void setDiffuseReflectivity(Vector3f diffuseReflectivity) {
        this.diffuseReflectivity = diffuseReflectivity;
    }

    public void setSpecularReflectivity(Vector3f specularReflectivity) {
        this.specularReflectivity = specularReflectivity;
    }

    public void setSpecularExponent(float specularExponent) {
        this.specularExponent = specularExponent;
    }

    public String getName() {
        return name;
    }

    public float getAlpha() {
        return alpha;
    }

    public float getSpecularExponent() {
        return specularExponent;
    }

    public Vector3f getAmbientReflectivity() {
        return ambientReflectivity;
    }

    public Vector3f getDiffuseReflectivity() {
        return diffuseReflectivity;
    }

    public Vector3f getSpecularReflectivity() {
        return specularReflectivity;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MTLMaterial) {
            MTLMaterial material = (MTLMaterial) obj;

            if (material.diffuseReflectivity.x != diffuseReflectivity.x ||
                    material.diffuseReflectivity.y != diffuseReflectivity.y ||
                    material.diffuseReflectivity.z != diffuseReflectivity.z)
                return false;


            if (material.specularReflectivity.x != specularReflectivity.x ||
                    material.diffuseReflectivity.y != diffuseReflectivity.y ||
                    material.diffuseReflectivity.z != diffuseReflectivity.z)
                return false;


            if (material.diffuseReflectivity.x != diffuseReflectivity.x ||
                    material.diffuseReflectivity.y != diffuseReflectivity.y ||
                    material.diffuseReflectivity.z != diffuseReflectivity.z)
                return false;

            if (material.alpha != alpha) return false;

            if (material.name != name) return false;

            if (material.specularExponent != specularExponent) return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Name: " + name + "\n");
        builder.append("Ambient Reflectivity: " + ambientReflectivity + "\n");
        builder.append("Diffuse Reflectivity: " + diffuseReflectivity + "\n");
        builder.append("Specular Reflectivity: " + specularReflectivity + "\n");
        builder.append("Specular Exponent: " + specularExponent + "\n");
        builder.append("Alpha: " + alpha + "\n");
        return builder.toString();
    }
}
