package ubermaster.persistence.manager;

import ubermaster.entity.model.BaseEntity;
import ubermaster.entity.model.PersistenceEntity;

public interface Manager
{
/*::|       FIELD       :~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
    String GET_ENTITY = "{call getEntity(?, ?)}";
    String GET_TYPED_ENTITIES = "{call getTypedEntities(?, ?)}";
    String GET_POKE_ORDERS = "{call getPokeOrders(?, ?)}";
    String GET_USER = "{call getUser(?, ?, ?)}";
    String GET_USER_BY_PHONE = "{call getUserByPhone(?, ?)}";
    String DELETE_ENTITY = "delete from Objects where object_id = ?";
    String INSERT_ENTITY = "{call insertEntity(?)}";
    String GET_ORDER_BY_PROFESSION = "call getOrdersByProfession(?, ?)";

    String ATTR_OBJECT_ID = "-1";
    String ATTR_OBJECT_TYPE_ID = "-2";
    String ATTR_NAME = "-3";
    String ATTR_DESCR = "-4";

    String ARRAY_ENTITIES = "ARRAYENTITES";
    String ARRAY_STRING = "ARRAY";
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
     * Deletes entity from data base by entity id
     *
     * @param id — entity id
     * */
    void deleteEntity(long id);

    /**
     * Updates entity in data base
     *
     * @param persistenceEntity — an entity
     *
     * @param _class — a type of instance
     * */
    void updateEntity(PersistenceEntity persistenceEntity, Class<? extends BaseEntity> _class);
}
