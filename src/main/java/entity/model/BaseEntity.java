package entity.model;

import annotation.AttrType;
import entity.attr.BaseEntityAttr;

public class BaseEntity {

	public interface Model extends BaseEntityAttr{

	}

	@AttrType(Model.NAME_ATTR)
	protected String name;

	@AttrType(Model.DESCRIPTION)
	protected String description;

	@AttrType(Model.OBJECT_ID)
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

	@Override
	public String toString() {
		return "BaseEntity{" +
				"name='" + name + '\'' +
				", description='" + description + '\'' +
				", object_id=" + object_id +
				'}';
	}
}
