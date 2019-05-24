package objparser;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a detached object such as separate buildings.
 */
public class OBJObject {
    String name;
    private final List<OBJGroup> groups = new ArrayList();

    public OBJObject(String name) {
        this.name = name;
    }

    public void addGroup(OBJGroup currentGroup) {
        groups.add(currentGroup);
    }

    public List<OBJGroup> getGroups() {
        return groups;
    }

    public String getName() {
        return name;
    }
}
