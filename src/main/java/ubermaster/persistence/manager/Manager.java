package ubermaster.persistence.manager;

import ubermaster.entity.model.BaseEntity;
import ubermaster.entity.model.PersistenceEntity;

import java.sql.SQLException;

public interface Manager
{
/*::|       FIELD       :~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
    String GET_ENTITY = "{call getEntity(?, ?)}";
    String GET_TYPED_ENTITIES = "{call getTypedEntities(?, ?)}";
    String GET_POKE_ORDERS = "{call getPokeOrders(?, ?)}";
    String GET_MASTER_ORDERS = "{call getMasterOrders(?, ?)}";
    String GET_USER = "{call getUser(?, ?, ?)}";
    String GET_USER_BY_PHONE = "{call getUserByPhone(?, ?)}";
    String DELETE_ENTITY = "delete from Objects where object_id = ?";
    String INSERT_ENTITY = "{call insertEntity(?)}";
    String UPDATE_ENTITY = "{call updateEntity(?)}";
    String GET_ORDER_BY_PROFESSION = "call getOrdersByProfession(?, ?)";
    String GET_ORDER_BY_STATUS = "call getOrdersByStatus(?, ?)";
    String GET_ORDER_SORT = "call getOrdersBySortDate(?, ?)";
    String GET_MASTER_AVER_MARK = "select getMasterAverMark(?) from dual";
    String GET_MASTER_NAME = "select getMasterName(?) from dual";
    String GET_POKE_ID = "select getOrderParentID(?) from dual";
    String GET_BUSER_STATUS =
            "select lst.value " +
            "from Lists lst join Attributes attr " +
            "on lst.list_value_id = attr.list_value_id " +
            "where lst.attr_id = 20 and object_id = ?";
	String GET_MASTER_COMMENTS = "call getMasterComments(?, ?, ?)";

    String ATTR_OBJECT_ID = "-1";
    String ATTR_OBJECT_TYPE_ID = "-2";
    String ATTR_NAME = "-3";
    String ATTR_DESCR = "-4";

    String ARRAY_ENTITIES = "ARRAYENTITES";
    String ARRAY_FIELD = "ARRAYFILED";
    String ARRAY = "ARRAY";

    byte MASTER_TYPE_ORDERS = 1;
    byte POKE_TYPE_ORDERS = 2;

    byte CON_LST_PROFESSION = 1;
    byte CON_LST_STATUS = 2;
    byte CON_ATTR_SORT = 3;

//==:   Simple queries const
    byte CON_MASTER_AVER = 1;
    byte CON_DELETE = 2;
    byte CON_MASTER_NAME = 3;
    byte CON_POKE_ID = 4;
    byte CON_BUSER_STATUS = 5;
/*::|       SUB_CLASS       :~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
/*::|       F / P       :~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
    /**
     * Inserts an instance of {@code BaseEntity} child class into data base
     *
     * @param persistenceEntity — an entity
     *
     * @param _class — a type of instance
     * */
    void createEntity(PersistenceEntity persistenceEntity, Class<? extends BaseEntity> _class);

    /**
     * Get an entity from data base by entity id
     *
     * @param id — entity id
     *
     * @param _class — a class type of entity
     *
     * @return an instance of {@code PersistenceEntity} class
     * */
    PersistenceEntity getEntity(long id, Class<? extends BaseEntity> _class);

    /**
     * Get an array of entities from data base by class type
     *
     * @param _class — a class type of entity
     *
     * @return an instance of array of {@code PersistenceEntity} instances
     * */
    PersistenceEntity[] getTypedEntities(Class<? extends BaseEntity> _class);

    /**
     * Get a user from data base by phone number
     *
     * @param phoneNumber — a phone number of entity
     *
     * @return an instance of {@code PersistenceEntity} instance
     * */
    PersistenceEntity getUserByPhone(String phoneNumber);

    /**
     * Get a user from data base by phone number and password
     *
     * @param phoneNumber — a phone number of entity
     * @param password  — a password of entity
     *
     * @return an instance of {@code PersistenceEntity} instance
     * */
    PersistenceEntity getUserByPhonePass(String phoneNumber, String password);

    /**
     * Get the orders of master or poke in array of {@code PersistenceEntity}
     * instances
     *
     * @param id — an id of entity
     * @param userType — int value of class type from Manager
     *
     * @return an instance of array of {@code PersistenceEntity} instances
     * */
    PersistenceEntity[] getUserOrders(long id, int userType);

    /**
     * Get the order instances by some order value
     *
     * @param condition — a byte value that identifies order value type
     * @param value
     *
     * @return an instance of array of {@code PersistenceEntity} instances
     * */
    PersistenceEntity[] getOrdersByParam(byte condition, String value);

    /**
     * Returns a string value from entity by condition
     *
     * @param CON_QUERY_VAL — a key value for entity value
     *
     * @param id — an id of entity
     *
     * @return — entity value performed like string value
     * */
    String simpleQuery(final byte CON_QUERY_VAL, long id);

    /**
     * Method is used for updating entities
     *
     * @param id — id of entity
     * @param sqcParam — an array of object couples. First couple element is attr_id
     *      of attribute and second one is value
     * */
    void updateEntity(long id, Object ... sqcParam);

    /**
     * Get master comments from DB
     *
     * @param id — master id
     * @param count — count of master comments
     *
     * @return an array of master comments
     * */
    String[] getMasterComments(long id, int count);
}
