package persistence.converter;

import entity.model.BaseEntity;
import persistence.PersistenceEntity;

public interface Converter {
    PersistenceEntity convertToEntity(BaseEntity baseEntity);
    BaseEntity convertToModel(PersistenceEntity persistenceEntity);
}
