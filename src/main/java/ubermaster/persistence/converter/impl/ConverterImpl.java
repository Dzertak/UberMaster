package ubermaster.persistence.converter.impl;

import org.springframework.stereotype.Component;
import ubermaster.entity.model.*;
import ubermaster.persistence.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        {
            entity = new Master();
            ((User)entity).setClassType(Master.class);
        }

        else if (Poke.class.isAssignableFrom(CLASS))
        {
            entity = new Poke();
            ((User)entity).setClassType(Poke.class);
        }

        else if (User.class.isAssignableFrom(CLASS))
        {
            entity = new User();
            ((User)entity).setClassType(User.class);
        }

        else if (Admin.class.isAssignableFrom(CLASS))
        {
            entity = new Admin();
            ((User)entity).setClassType(Admin.class);
        }

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

        return null;
    }

    public static Object convertStringToObject(final String VALUE, final Class CLASS) throws ParseException {
        if (int.class.isAssignableFrom(CLASS))
            return Integer.parseInt(VALUE);

        if (boolean.class.isAssignableFrom(CLASS))
            return Boolean.parseBoolean(VALUE);

        try {
            if (Date.class.isAssignableFrom(CLASS))
                return new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(VALUE);
        } catch(ParseException ex) {
            log.log(Level.SEVERE, ex.getMessage(), ex);
            throw new ParseException(ex.getMessage(), ex.getErrorOffset());
        }

        if (long.class.isAssignableFrom(CLASS))
            return Long.parseLong(VALUE);

        return VALUE;

    }
}
