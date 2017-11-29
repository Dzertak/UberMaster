package entity.model;

import annotation.Attribute;
import entity.attr.BaseEntityAttr;

import java.lang.reflect.Field;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

	protected final void setField(Field field, String value, Object objField) throws IllegalAccessException, ParseException
	{
		final Class CLASS = field.getType();

		if (String.class.isAssignableFrom(CLASS))
		{
			field.set(objField, value);
			return;
		}

		if (Date.class.isAssignableFrom(CLASS))
		{
			field.set(objField, new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(value));
			return;
		}

		if (Integer.class.isAssignableFrom(CLASS))
		{
			field.set(objField, Integer.parseInt(value));
			return;
		}

		if (Boolean.class.isAssignableFrom(CLASS))
		{
			field.set(objField, Boolean.parseBoolean(value));
			return;
		}
	}

	@Override
	public String toString() {
		return "BaseEntity{" +
				"\nname='" + name + '\'' +
				",\ndescription='" + description + '\'' +
				",\nobject_id=" + object_id +
				'}';
	}
}
