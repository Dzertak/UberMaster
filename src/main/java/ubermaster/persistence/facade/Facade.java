package ubermaster.persistence.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;
import ubermaster.entity.model.*;
import ubermaster.persistence.converter.impl.ConverterImpl;
import ubermaster.persistence.manager.Manager;
import ubermaster.persistence.manager.impl.ManagerImpl;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
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

    private final PE_Cache CACHE;
    public Facade()
    {
        CACHE = new PE_Cache(0, 1, 10000);
        CACHE.autoCleanCache();
    }

    /**
     * Method that inserts entity to database
     *
     * @param baseEntity an entity that will be inserted to the database
     */
    public void createEntity(BaseEntity baseEntity) throws IOException, SAXException, ParserConfigurationException, SQLException
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

        CACHE.put(persistenceEntity);

        T entity = converter.convertToModel(persistenceEntity, CLASS);

        if (Master.class.isAssignableFrom(CLASS))
            addParamsToMaster((Master)entity);

        else if (Order.class.isAssignableFrom(CLASS))
           addParamsToOrder((Order)entity);

        return entity;
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
        PersistenceEntity persistenceEntity = CACHE.getUser(phoneNumber, password);
        if (persistenceEntity != null)
            return converter.convertToModel(persistenceEntity);

        persistenceEntity = manager.getUserByPhonePass(phoneNumber, password);
        if (persistenceEntity == null)
            return null;

        T entity = converter.convertToModel(persistenceEntity);

        if (entity instanceof Master)
            addParamsToMaster((Master) entity);

        return entity;
    }

    /**
     * Method that gets user from DB by phone
     *
     * @param phoneNumber — phone number of user
     * @return entity that can be Poke or Master
     */
    public <T extends User> T getUserByPhone(String phoneNumber)
    {
        PersistenceEntity persistenceEntity = CACHE.getUser(phoneNumber);
        if (persistenceEntity != null)
            return converter.convertToModel(persistenceEntity);

    //==:   DB
        persistenceEntity = manager.getUserByPhone(phoneNumber);
        if (persistenceEntity == null)
            return null;

        T entity = converter.convertToModel(persistenceEntity);

        if (entity instanceof Master)
            addParamsToMaster((Master)entity);

        return entity;
    }

    /**
     * Method that deletes entity from database
     *
     * @param id — the identification number of an entity
     */
    public void deleteEntity(long id)
    {
        manager.simpleQuery(Manager.CON_DELETE, id);
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
        {
            sqcT[itera] = converter.convertToModel(sqcPE[itera], _class);

            if (sqcT[itera] instanceof Master)
                addParamsToMaster((Master)sqcT[itera]);

            else if (sqcT[itera] instanceof Order)
                addParamsToOrder((Order)sqcT[itera]);
        }

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
    public <T extends BaseEntity> T[] getUserOrders(long id, byte userType)
    {
    //--:   DB
        PersistenceEntity sqcPE[] = manager.getUserOrders(id, userType, null);

        if (sqcPE == null)
            return null;

        int length = sqcPE.length;
        T sqcT[] = (T[])new BaseEntity[length];

        for (int itera = 0; itera < length; ++itera)
        {
            sqcT[itera] = converter.convertToModel(sqcPE[itera], Order.class);
            addParamsToOrder((Order)sqcT[itera]);
        }

        return sqcT;
    }

    public <T extends BaseEntity> T[] getOrdersByProfession(String profession)
    {
        return getOrdersByLParam(Manager.CON_LST_PROFESSION, profession);
    }

    public <T extends BaseEntity> T[] getOrdersByStatus(String status)
    {
        return getOrdersByLParam(Manager.CON_LST_STATUS, status);
    }

    public <T extends BaseEntity> T[] getSortOrders(boolean isASC)
    {
        return getOrdersByLParam
            (
                Manager.CON_ATTR_SORT,
                Boolean.toString(isASC)
            );
    }

    private <T extends BaseEntity> T[] getOrdersByLParam(byte condition, String value)
    {
    //--:   DB
        PersistenceEntity sqcPE[] = manager.getOrdersByParam(condition, value);

        if (sqcPE == null)
            return null;

        int length = sqcPE.length;
        T sqcT[] = (T[])new BaseEntity[length];

        for (int itera = 0; itera < length; ++itera)
        {
            sqcT[itera] = converter.convertToModel(sqcPE[itera], Order.class);
            addParamsToOrder((Order) sqcT[itera]);
        }

        return sqcT;
    }

    public void setBlocked(long id, boolean isBlocked)
    {
    //==:CACHE
        PersistenceEntity persistenceEntity = CACHE.get(id);
        if (persistenceEntity != null)
        {
            HashMap<String, Object> attributes = (HashMap<String, Object>) persistenceEntity.getAttributes();
            attributes.put(BlockedUser.Model.IS_BLOCKED, isBlocked);
        }

    //--:   DB
        manager.updateEntity(id, BlockedUser.Model.IS_BLOCKED, isBlocked);
    }

    /**
     * Method sets order status
     *
     * @param id — an order id
     * @param mid — a master id
     *             if mid == -1 then master will deleted
     *             else if mid == -2 then master will still same
     * @param status — new status of order
     * */
    public void setOrderStatus(long id, long mid, String status)
    {
    //==:CACHE
        PersistenceEntity persistenceEntity = CACHE.get(id);
        if (persistenceEntity != null)
        {
            HashMap<String, Object> attributes = (HashMap<String, Object>) persistenceEntity.getAttributes();
            attributes.put(Order.Model.MASTER_REF, mid);
            attributes.put(Order.Model.STATUS, status);
        }

    //--:   DB
        manager.updateEntity
            (
                id,
                Order.Model.STATUS,
                status,
                Order.Model.MASTER_REF,
                mid
            );
    }

    /**
     * Method sets user picture
     *
     * @param id — an user id
     * @param picture — an user picture
     * */
    public void setUserPicture(long id, String picture)
    {
    //==:CACHE
        PersistenceEntity persistenceEntity = CACHE.get(id);
        if (persistenceEntity != null)
        {
            HashMap<String, Object> attributes = (HashMap<String, Object>) persistenceEntity.getAttributes();
            attributes.put(User.Model.PICTURE, picture);
        }

    //--:   DB
        manager.updateEntity(id, User.Model.PICTURE, picture);
    }

    public void updateEntity(BaseEntity entity)
    {
        PersistenceEntity persistenceEntity = converter.convertToEntity(entity);

        CACHE.put(persistenceEntity);

    //--:   DB
        HashMap<String, Object> attributes = (HashMap<String, Object>) persistenceEntity.getAttributes();
        Object sqcParam[] = new Object[4 + (attributes.size() << 1)];

        int itera = 0;
        for (String key : attributes.keySet())
        {
            sqcParam[itera] = key;
            ++itera;
            sqcParam[itera] = attributes.get(key);
            ++itera;
        }

        sqcParam[itera] = "-3";
        ++itera;
        sqcParam[itera] = persistenceEntity.getName();
        ++itera;
        sqcParam[itera] = "-4";
        ++itera;
        sqcParam[itera] = persistenceEntity.getDescription();

        manager.updateEntity(persistenceEntity.getObject_id(), sqcParam);
    }

    private void addParamsToMaster(Master master)
    {
        String value = manager.simpleQuery(Manager.CON_MASTER_AVER, master.getObject_id());
        try
        {
            master.setAverMark((byte)ConverterImpl.convertStringToObject(value, byte.class));
        }

        catch (ParseException exc)
        {
            exc.printStackTrace();
        }
    }

    private void addParamsToOrder(Order order)
    {
        long id = order.getObject_id();

        String masterName = manager.simpleQuery(Manager.CON_MASTER_NAME, id);
        order.setMasterName(masterName);

        String pokeID = manager.simpleQuery(Manager.CON_POKE_ID, id);
        order.setPokeId(Long.parseLong(pokeID));
    }

    public boolean getBUserStatus(long id)
    {
        return Boolean.parseBoolean(manager.simpleQuery(Manager.CON_BUSER_STATUS, id));
    }

    public <T extends BaseEntity> T[] getMasterComments(long id, int count)
    {
    //--:   DB
        PersistenceEntity sqcPE[] = manager.getUserOrders(id, Manager.LAST_MASTER_COMMENTED_ORDERS, count);

        if (sqcPE == null)
            return null;

        int length = sqcPE.length;
        T sqcT[] = (T[])new BaseEntity[length];

        for (int itera = 0; itera < length; ++itera)
        {
            sqcT[itera] = converter.convertToModel(sqcPE[itera], Order.class);
            addParamsToOrder((Order) sqcT[itera]);
        }

        return sqcT;
    }
}
