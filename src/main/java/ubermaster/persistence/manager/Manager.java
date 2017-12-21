package ubermaster.persistence.manager;

import ubermaster.entity.model.BaseEntity;
import ubermaster.entity.model.PersistenceEntity;

public interface Manager
{
    String GET_ENTITY = "{call getEntity(?, ?)}";
    String GET_TYPED_ENTITIES = "{call getTypedEntities(?, ?)}";
    String GET_POKE_ORDERS = "{call getPokeOrders(?, ?)}";
    String GET_USER = "{call getUser(?, ?, ?)}";
    String DELETE_ENTITY = "delete from Objects where object_id = ?";
    String INSERT_ENTITY = "{call insertEntity(?)}";

    String ATTR_OBJECT_ID = "-1";
    String ATTR_OBJECT_TYPE_ID = "-2";
    String ATTR_NAME = "-3";
    String ATTR_DESCR = "-4";

    String ARRAY_ENTITIES = "ARRAYENTITES";
    String ARRAY_STRING = "ARRAY";

    void createEntity(PersistenceEntity persistenceEntity, final Class<? extends BaseEntity> CLASS);

    PersistenceEntity getEntity(long id, final Class<? extends BaseEntity> CLASS);

    void deleteEntity(long id);

    void updateEntity(PersistenceEntity persistenceEntity, final Class<? extends BaseEntity> CLASS);
}
