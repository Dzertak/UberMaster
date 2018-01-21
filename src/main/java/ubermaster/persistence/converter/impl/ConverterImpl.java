package ubermaster.persistence.converter.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import ubermaster.entity.model.*;
import ubermaster.persistence.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 *  @author Serpye
 *
 *  @code ConverterImpl class is realization of interface Converter.
 *  It is created for converting PersistenceEntity instance to child class
 *  of BaseEntity class and conversely
 * */
@Component
public class ConverterImpl implements Converter
{
/*::|       FIELD       :~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
    private static Logger log = Logger.getLogger(ConverterImpl.class.getName());
/*::|       CONSTRUCTOR     :~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
/*::|       SUB_CLASS       :~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
/*::|       F / P       :~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
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

        else if (Admin.class.isAssignableFrom(CLASS))
            entity = new Admin();

        HashMap<String, Object> hashMap =
                (HashMap<String, Object>) persistenceEntity.getAttributes();

        BaseEntity.fillAttributeFields(hashMap, entity);
        entity.setObject_id(persistenceEntity.getObject_id());
        entity.setName(persistenceEntity.getName());
        entity.setDescription(persistenceEntity.getDescription());

        return (T) entity;
    }

    public <T extends BaseEntity> T convertToModel(PersistenceEntity persistenceEntity)
    {
        return convertToModel(persistenceEntity, persistenceEntity.getClassType());
    }

    public static String convertObjectToString(Object value)
    {
        final Class CLASS = value.getClass();

        if (String.class.isAssignableFrom(CLASS))
            return (String) value;

        if (Date.class.isAssignableFrom(CLASS))
            return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(value);

        if (Integer.class.isAssignableFrom(CLASS))
            return Integer.toString((int) value);

        if (Boolean.class.isAssignableFrom(CLASS))
            return Boolean.toString((boolean) value);

        if (Long.class.isAssignableFrom(CLASS))
            return Long.toString((long) value);

        if (Byte.class.isAssignableFrom(CLASS))
            return Byte.toString((byte)value);

        if (Short.class.isAssignableFrom(CLASS))
            return Short.toString((short)value);

        return null;
    }

    public static Object convertStringToObject(final String VALUE, final Class CLASS) throws ParseException {
        if (int.class.isAssignableFrom(CLASS))
            return Integer.parseInt(VALUE);

        if (boolean.class.isAssignableFrom(CLASS))
            return Boolean.parseBoolean(VALUE);

        try
        {
            if (Date.class.isAssignableFrom(CLASS))
                return new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(VALUE);
        }

        catch(ParseException exc)
        {
            log.error(exc.getMessage(), exc);
            throw new ParseException(exc.getMessage(), exc.getErrorOffset());
        }

        if (long.class.isAssignableFrom(CLASS))
            return Long.parseLong(VALUE);

        if (byte.class.isAssignableFrom(CLASS))
            return Byte.parseByte(VALUE);

        if (short.class.isAssignableFrom(CLASS))
            return Short.parseShort(VALUE);

        return VALUE;

    }
}
