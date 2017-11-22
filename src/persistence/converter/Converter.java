package persistence.converter;

import entity.model.BaseEntity;
import persistence.PersistenceEntity;

public interface Converter {
    PersistenceEntity convertToEntuty(BaseEntity baseEntity);
    BaseEntity convertToModel(PersistenceEntity persistenceEntity);
}
