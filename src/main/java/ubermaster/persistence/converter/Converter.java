package ubermaster.persistence.converter;

import ubermaster.entity.model.BaseEntity;
import ubermaster.persistence.PersistenceEntity;


public interface Converter {
    PersistenceEntity convertToEntity(BaseEntity baseEntity);

    <T extends BaseEntity> T convertToModel(PersistenceEntity persistenceEntity, final Class<? extends BaseEntity> CLASS);
}
