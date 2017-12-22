package ubermaster.entity.model;

import ubermaster.annotation.Attribute;
import ubermaster.entity.attr.BaseEntityAttr;

import java.lang.reflect.Field;
import java.util.HashMap;

public abstract class BaseEntity
{
    @Attribute(Model.NAME_ATTR)
    protected String name;

    @Attribute(Model.DESCRIPTION)
    protected String description;

    @Attribute(Model.OBJECT_ID)
    protected long object_id;

    private static BaseEntityController BASE_ENTITY_CONTROLLER;

    public interface Model extends BaseEntityAttr
    {                       }

    /**
     *  @author Serpye
     *
     *  The {@code BaseEntity} class is needed for getting
     *  information about {@code BaseEntity} class and its
     *  child classes and setting filed into their all instances
     * */
    private static class BaseEntityController
    {
    /*::|       FIELD       :~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
    /*::|       CONSTRUCTOR     :~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
        private BaseEntityController()
        {                           }
    /*::|       SUB_CLASS       :~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
    /*::|       F / P       :~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
        /**
         * Returns a {@code Class} instance that indicate about
         * of field class type of {@code BaseEntity} class or its
         * child classes
         *
         * @param attr_id — the {@code Attribute} annotation value,
 *       *         that indicates what exactly field type need
         *
         * @param CLASS — type of entity
         *
         * @return a class type of field
         * */
        private Class getFieldType(String attr_id, final Class<? extends BaseEntity> CLASS)
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

        /**
         * Fills all fields of {@code BaseEntity} child instance
         *
         * @param hashMap — hash map of attribute field entity values
         *
         * @param CLASS — type of entity
         *
         * @param entity — an instance of {@code BaseEntity} child class
         * */
        private void fillAttributeFields
        (
            HashMap<String, Object> hashMap,
            final Class<? extends BaseEntity> CLASS,
            BaseEntity entity
        )
        {
            Class superClass = CLASS.getSuperclass();
            if (superClass != null && !superClass.equals(BaseEntity.class))
                fillAttributeFields(hashMap, superClass, entity);

            Field sqcField[] = CLASS.getDeclaredFields();
            Attribute attrib;
            int length = sqcField.length;
            try
            {
                for (int i = 0; i < length; ++i)
                {
                    attrib = sqcField[i].getAnnotation(Attribute.class);

                    if (attrib != null && hashMap.containsKey(attrib.value()))
                        sqcField[i].set(entity, hashMap.get(attrib.value()));
                }
            }

            catch (IllegalAccessException exc)
            {
                exc.printStackTrace();
            }
        }
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public long getObject_id()
    {
        return object_id;
    }

    public void setObject_id(long object_id)
    {
        this.object_id = object_id;
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

    public static void fillAttributeFields(HashMap<String, Object> hashMap, BaseEntity entity)
    {
        if (BASE_ENTITY_CONTROLLER == null)
            BASE_ENTITY_CONTROLLER = new BaseEntityController();

        BASE_ENTITY_CONTROLLER.fillAttributeFields(hashMap, entity.getClass(), entity);
    }

    public static Class getFieldType(String attr_id, final Class<? extends BaseEntity> CLASS)
    {
        if (BASE_ENTITY_CONTROLLER == null)
            BASE_ENTITY_CONTROLLER = new BaseEntityController();

        return BASE_ENTITY_CONTROLLER.getFieldType(attr_id, CLASS);
    }
}
