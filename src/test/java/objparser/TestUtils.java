package objparser;

public class TestUtils {
    public static class Vector3f extends org.lwjglx.util.vector.Vector3f {
        public Vector3f(float x, float y, float z) {
            super(x, y, z);
        }

        public Vector3f(org.lwjglx.util.vector.Vector3f vector) {
            super(vector.x, vector.y, vector.z);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Vector3f) {
                Vector3f other = (Vector3f) obj;
                if (other.x != x) return false;
                if (other.y != y) return false;
                if (other.z != z) return false;
            }
            return true;
        }
    }

    public static void success() {
        System.out.println("\033[32;1;0mTest Passed\033[0m");
    }
}
