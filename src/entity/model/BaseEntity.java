package entity.model;

import annotation.Attribute;
import entity.attr.BaseEntityAttr;

import java.lang.reflect.Field;
import java.util.HashMap;

public abstract class BaseEntity {

	public interface Model extends BaseEntityAttr{

	}

	@Attribute(Model.NAME_ATTR)
	protected String name;

	@Attribute(Model.DESCRIPTION)
	protected String description;

	@Attribute(Model.OBJECT_ID)
	protected long object_id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getObject_id() {
		return object_id;
	}

	public void setObject_id(long object_id) {
		this.object_id = object_id;
	}

	public abstract void fillAttributeFields(HashMap<String, Object> hashMap);

	@Override
	public String toString() {
		return "BaseEntity{" +
				"\nname='" + name + '\'' +
				",\ndescription='" + description + '\'' +
				",\nobject_id=" + object_id +
				'}';
	}
}
