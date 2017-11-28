package persistence.converter;

import annotation.Attribute;
import entity.model.*;
import persistence.PersistenceEntity;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;


public class ConverterImpl implements Converter
{
    public PersistenceEntity convertToEntity(BaseEntity baseEntity)
    {
        PersistenceEntity persistenceEntity = new PersistenceEntity();

        /*Field sqcField[] = baseEntity.getClass().getFields();
        LinkedList<Object> lstAttr = new LinkedList<>();
        int length = sqcField.length;
        Attribute attrib;
        try
        {
            for (int itera = 0; itera < length; ++itera)
            {
                attrib = sqcField[itera].getAnnotation(Attribute.class);
                if (attrib != null)
                    lstAttr.add(new (Long.parseLong(attrib.value()), sqcField[itera].get(baseEntity)));
            }
        }

        catch (IllegalAccessException exc)
        {               }
*/
        return persistenceEntity;
    }

    public <T extends BaseEntity> T convertToModel(PersistenceEntity persistenceEntity, final Class<? extends BaseEntity> CLASS)
    {
        BaseEntity entity = null;

        if (Order.class.isAssignableFrom(CLASS))
            entity = new Order();

        else if (Master.class.isAssignableFrom(CLASS))
            entity = new Master();

        else if (Poke.class.isAssignableFrom(CLASS))
            entity = new Poke();

        else if (User.class.isAssignableFrom(CLASS))
            entity = new User();

        else if (Admin.class.isAssignableFrom(CLASS))
            entity = new Admin();

        HashMap<String, Object> hashMap = (HashMap<String, Object>) persistenceEntity.getAttributes();

        entity.fillAttributeFields(hashMap);
        entity.setObject_id(persistenceEntity.getObject_id());
        entity.setName(persistenceEntity.getName());
        entity.setDescription(persistenceEntity.getDescription());

        return (T)entity;
    }
}
