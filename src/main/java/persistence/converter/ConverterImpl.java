package persistence.converter;

import entity.model.BaseEntity;
import persistence.PersistenceEntity;

public class ConverterImpl implements Converter {
    public PersistenceEntity convertToEntity(BaseEntity baseEntity) {
        PersistenceEntity persistenceEntity = new PersistenceEntity();
        persistenceEntity.setObject_id(baseEntity.getObject_id());
        persistenceEntity.setName(baseEntity.getName());
        persistenceEntity.setDescription(baseEntity.getDescription());
        return persistenceEntity;
    }
    public BaseEntity convertToModel(PersistenceEntity persistenceEntity) {
        BaseEntity baseEntity = new BaseEntity();
        baseEntity.setObject_id(persistenceEntity.getObject_id());
        baseEntity.setName(persistenceEntity.getName());
        baseEntity.setDescription(persistenceEntity.getDescription());
        return baseEntity;
    }
}
