package ubermaster.persistence.manager;

import ubermaster.entity.model.BaseEntity;
import ubermaster.persistence.PersistenceEntity;

import java.io.Closeable;

public interface Manager
{
    String GET_ENTITY = "{call getEntity(?, ?)}";
    String DELETE_ENTITY = "delete from Objects where object_id = ?";

    void createEntity(PersistenceEntity persistenceEntity, final Class<? extends BaseEntity> CLASS);

    PersistenceEntity getEntity(long id, final Class<? extends BaseEntity> CLASS);

    void deleteEntity(long id);
}
