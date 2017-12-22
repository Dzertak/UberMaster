package ubermaster.persistence.converter;

import ubermaster.entity.model.BaseEntity;
import ubermaster.entity.model.PersistenceEntity;

/**
 *  @author Serpye
 *
 *  @code an interface that designed for converting
 *  PersistenceEntity instance to child class of BaseEntity class
 *  and conversely
 * */
public interface Converter
{
/*::|       FIELD       :~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
/*::|       SUB_CLASS       :~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
/*::|       F / P       :~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
    /**
     * Convert from BaseEntity child class instance to PersistenceEntity
     *
     * @param   baseEntity — entity
     *
     * @return converted instance of PersistenceEntity
     * */
    PersistenceEntity convertToEntity(BaseEntity baseEntity);

    /**
     * Convert from PersistenceEntity instance to
     * BaseEntity child class instance
     *
     * @param   persistenceEntity — instance of PersistenceEntity
     *
     * @return converted instance of BaseEntity
     * */
    <T extends BaseEntity> T convertToModel(PersistenceEntity persistenceEntity, final Class<? extends BaseEntity> CLASS);
}
