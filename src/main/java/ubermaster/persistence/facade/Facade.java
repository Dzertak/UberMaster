package ubermaster.persistence.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ubermaster.entity.model.*;
import ubermaster.persistence.converter.impl.ConverterImpl;
import ubermaster.persistence.manager.Manager;
import ubermaster.persistence.manager.impl.ManagerImpl;

import java.util.HashMap;

/**
 * @author Serpye
 *
 * The Facade class {@code Facade} is intended for controlling of
 * {@code Manager} and {@code Converter}
 */
@Component
public class Facade
{
    @Autowired
    private ConverterImpl converter;
    @Autowired
    private ManagerImpl manager;
    private final HashMap<Long, PersistenceEntity> CACHE = new HashMap<>();

    /**
     * Method that inserts entity to database
     *
     * @param baseEntity an entity that will be inserted to the database
     */
    public void createEntity(BaseEntity baseEntity)
    {
        PersistenceEntity persistenceEntity = converter.convertToEntity(baseEntity);
        manager.createEntity(persistenceEntity, baseEntity.getClass());
    }

    /**
     * Method that gets entity from database
     *
     * @param id — the identification number of an entity
     * @return entity from database
     */
    public <T extends BaseEntity> T getEntity
    (
            long id,
            final Class<? extends BaseEntity> CLASS
    )
    {
        if (CACHE.containsKey(id))
            return converter.convertToModel(CACHE.get(id), CLASS);

        PersistenceEntity persistenceEntity = manager.getEntity(id, CLASS);

        if (persistenceEntity == null)
            return null;

        CACHE.put(id, persistenceEntity);

        return converter.convertToModel(persistenceEntity, CLASS);
    }

    /**
     * Method that gets user from DB
     *
     * @param phoneNumber — phone number of user
     * @param password    — user password
     * @return entity that can be Poke or Master
     */
    public <T extends User> T getUser(String phoneNumber, String password)
    {
    //--:   Checking for presenting entity in the CACHE
        final byte NOT_FOUND = 0;
        final byte PHONE_NUMBER_EQUALS = 1;
        final byte PASS_EQUALS = 2;
        final byte ALL_EQUALS = 3;
        for (long id : CACHE.keySet())
        {
            PersistenceEntity persistenceEntity = CACHE.get(id);
            HashMap<String, Object> attributes = (HashMap<String, Object>) persistenceEntity.getAttributes();
            byte condition = NOT_FOUND;
            for (String attrID : attributes.keySet())
            {
                if
                (
                    attrID.equals(User.Model.PHONE_NUMBER)
                        &&
                    attributes.get(attrID).equals(phoneNumber)
                )
                    condition |= PHONE_NUMBER_EQUALS;

                else if
                (
                    attrID.equals(User.Model.PASSWORD)
                        &&
                    attributes.get(attrID).equals(password)
                )
                    condition |= PASS_EQUALS;

                if (condition == ALL_EQUALS)
                    return converter.convertToModel(persistenceEntity);
            }
        }

        PersistenceEntity persistenceEntity =
                manager.getUserByPhonePass(phoneNumber, password);

        if (persistenceEntity == null)
            return null;

        return converter.convertToModel(persistenceEntity);
    }

    /**
     * Method that gets user from DB by phone
     *
     * @param phoneNumber — phone number of user
     * @return entity that can be Poke or Master
     */
    public <T extends User> T getUserByPhone(String phoneNumber)
    {
        PersistenceEntity persistenceEntity = manager.getUserByPhone(phoneNumber);

        if (persistenceEntity == null)
            return null;

        return converter.convertToModel(persistenceEntity);
    }

    /**
     * Method that deletes entity from database
     *
     * @param id — the identification number of an entity
     */
    public void deleteEntity(long id)
    {
        manager.deleteEntity(id);
    }

    /**
     * Method get typed entity for data base
     *
     * @param _class — type of entities
     *
     * @return an array of typed entity
     */
    public <T extends BaseEntity> T[] getTypedEntities(Class<? extends BaseEntity> _class)
    {
    //--:   DB
        PersistenceEntity sqcPE[] = manager.getTypedEntities(_class);

        if (sqcPE == null)
            return null;

        int length = sqcPE.length;
        T sqcT[] = (T[])new BaseEntity[length];

        for (int itera = 0; itera < length; ++itera)
            sqcT[itera] = converter.convertToModel(sqcPE[itera], _class);

        return sqcT;
    }

    /**
     * Method get all {@code Order} instances by {@code Poke} or
     * {@code Master} entity id
     *
     * @param id — Poke id
     * @param userType — a constant param of user from {@code Manager} class
     * @return an array of {@code Order} instances
     */
    public <T extends BaseEntity> T[] getUserOrders(long id, int userType)
    {
    //--:   DB
        PersistenceEntity sqcPE[] = manager.getUserOrders(id, userType);

        if (sqcPE == null)
            return null;

        int length = sqcPE.length;
        T sqcT[] = (T[])new BaseEntity[length];

        for (int itera = 0; itera < length; ++itera)
            sqcT[itera] = converter.convertToModel(sqcPE[itera], Order.class);

        return sqcT;
    }

    public <T extends BaseEntity> T[] getOrdersByProfession(String profession)
    {
        //--:   DB
        PersistenceEntity sqcPE[] = manager.getOrdersByProfession(profession);

        if (sqcPE == null)
            return null;

        int length = sqcPE.length;
        T sqcT[] = (T[])new BaseEntity[length];

        for (int itera = 0; itera < length; ++itera)
            sqcT[itera] = converter.convertToModel(sqcPE[itera], Order.class);

        return sqcT;
    }
}
