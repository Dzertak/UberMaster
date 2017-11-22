package persistence;

import java.util.HashMap;
import java.util.Map;

public class PersistenceEntity {
    protected long object_id;
    protected long parent_id;
    protected String description;
    protected String name;
    protected Map attributes;
    protected Map references;

    public long getObject_id() {
        return this.object_id;
    }
    public void setObject_id(long object_id) {
        this.object_id = object_id;
    }

    public long getParent_id() {
        return this.parent_id;
    }

    public void setParent_id(long parent_id) {
        this.parent_id = parent_id;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map getAttributes() {
        return new HashMap(this.attributes);
    }

    public void setAttributes(Map attributes) {
        this.attributes = attributes;
    }

    public Map getReferences() {
        return new HashMap(this.references);
    }

    public void setReferences(Map references) {
        this.references = references;
    }
}
