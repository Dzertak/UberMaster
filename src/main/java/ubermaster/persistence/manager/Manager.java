package ubermaster.persistence.manager;

import ubermaster.entity.model.BaseEntity;
import ubermaster.persistence.PersistenceEntity;

import java.io.Closeable;

public interface Manager
{
    String GET_ATTR_TYPES = "{call getAttrTypeIds(?, ?)}";
    String GET_ATTR_COUNT = "{call getAttrTypeCount(?, ?)}";
    String GET_ENTITY = "{call getEntity(?, ?)}";

    void createEntity(PersistenceEntity persistenceEntity, final Class<? extends BaseEntity> CLASS);

    PersistenceEntity getEntity(long id, final Class<? extends BaseEntity> CLASS);
}
