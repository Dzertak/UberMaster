package persistence.converter;

import entity.model.BaseEntity;
import persistence.PersistenceEntity;

public interface Converter
{
    PersistenceEntity convertToEntity(BaseEntity baseEntity);

    <T extends BaseEntity> T convertToModel(PersistenceEntity persistenceEntity, final Class<? extends BaseEntity> CLASS);
}
