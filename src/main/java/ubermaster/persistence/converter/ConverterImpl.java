package ubermaster.persistence.converter;

import ubermaster.entity.model.*;
import ubermaster.persistence.PersistenceEntity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


public class ConverterImpl implements Converter
{
    public PersistenceEntity convertToEntity(BaseEntity baseEntity)
    {
        PersistenceEntity persistenceEntity = new PersistenceEntity();

        persistenceEntity.setName(baseEntity.getName());
        persistenceEntity.setObject_id(baseEntity.getObject_id());
        persistenceEntity.setDescription(baseEntity.getDescription());

        persistenceEntity.setAttributes(baseEntity.getAllFields());

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

    public static String convertObjectToString(Object value)
    {
        final Class CLASS = value.getClass();

        if (String.class.isAssignableFrom(CLASS))
            return (String)value;

        if (Date.class.isAssignableFrom(CLASS))
            return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(value);

        if (Integer.class.isAssignableFrom(CLASS))
            return Integer.toString((int)value);

        if (Boolean.class.isAssignableFrom(CLASS))
            return Boolean.toString((boolean)value);

        return null;
    }
}
