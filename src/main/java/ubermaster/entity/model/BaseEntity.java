package ubermaster.entity.model;

import ubermaster.annotation.Attribute;
import ubermaster.entity.attr.BaseEntityAttr;

import java.lang.reflect.Field;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

public abstract class BaseEntity
{
	public interface Model extends BaseEntityAttr
	{ 						}

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

	/*protected final void setField(final Field FIELD, String value, Object objField) throws IllegalAccessException, ParseException
	{
		final Class CLASS = FIELD.getType();

		if (String.class.isAssignableFrom(CLASS))
		{
			FIELD.set(objField, value);
			return;
		}

		if (Date.class.isAssignableFrom(CLASS))
		{
			FIELD.set(objField, new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(value));
			return;
		}

		if (Integer.class.isAssignableFrom(CLASS))
		{
			FIELD.set(objField, Integer.parseInt(value));
			return;
		}

		if (Boolean.class.isAssignableFrom(CLASS))
		{
			FIELD.set(objField, Boolean.parseBoolean(value));
			return;
		}
	}*/

	public final static Class getFieldType(String attr_id, final Class<? extends BaseEntity> CLASS)
	{
		Class superClass = CLASS.getSuperclass();
		if (superClass != null && !superClass.equals(BaseEntity.class))
		{
			Class fieldType = getFieldType(attr_id, superClass);

			if (fieldType != null)
				return fieldType;
		}

		Field sqcField[] = CLASS.getDeclaredFields();
		int length = sqcField.length;
		for (int i = 0; i < length; ++i)
		{
			Attribute attrib = sqcField[i].getAnnotation(Attribute.class);

			if (attrib != null && attrib.value().equals(attr_id))
				return sqcField[i].getType();
		}

		return null;
	}

	public abstract HashMap getAllFields();

	@Override
	public String toString()
	{
		return "BaseEntity{" +
				"\nname='" + name + '\'' +
				",\ndescription='" + description + '\'' +
				",\nobject_id=" + object_id +
				'}';
	}
}
