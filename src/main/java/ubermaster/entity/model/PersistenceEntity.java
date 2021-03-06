package ubermaster.entity.model;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Serpye
 *
 * @code PersistenceEntity class represents an entity, that
 * is gotten from data base
 * */
public class PersistenceEntity
{
/*::|       FIELD       :~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
    protected long object_id;
    protected long parent_id;
    protected String description;
    protected String name;
    protected Map<String, Object> attributes;
    protected Map<BigInteger, Long> references;
    protected Class<? extends BaseEntity> classType;

    protected Date lifeSpan;
/*::|       CONSTRUCTOR     :~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
    public PersistenceEntity()
    {
        attributes = new HashMap<>();
        references = new HashMap<>();
    }
/*::|       SUB_CLASS       :~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
/*::|       F / P       :~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
    public void setLifeSpan(Date lifeSpan)
    {
        this.lifeSpan = lifeSpan;
    }

    public Date getLifeSpan()
    {
        return lifeSpan;
    }

    public void setClassType(final Class<? extends BaseEntity> CLASS) {
        classType = CLASS;
    }

    public Class<? extends BaseEntity> getClassType() {
        return classType;
    }

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

    public String toString()
    {
        return "ClassType : " + classType + "\tID : " + object_id
                + "\tName : " + name + "\tDescription : " + description;
    }
}
