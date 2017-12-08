package ubermaster.persistence.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ubermaster.entity.model.BaseEntity;
import ubermaster.persistence.PersistenceEntity;
import ubermaster.persistence.converter.impl.ConverterImpl;
import ubermaster.persistence.manager.impl.ManagerImpl;

import java.util.HashMap;

/**
 * @author Serpye
 */
@Component
public class Facade
{
/*::|		FIELD		:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
    @Autowired
    private ConverterImpl converter;

    @Autowired
    private ManagerImpl manager;

    private final HashMap<Long, PersistenceEntity> CACHE = new HashMap<>();
/*::|		CONSTRUCTOR		:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
    public Facade()
    {
        manager = new ManagerImpl();
        converter = new ConverterImpl();
    }
/*::|		SUB_CLASS		:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
/*::|		F / P		:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
    /**
     * Method that inserts entity to database
     *
     * @param baseEntity an entity that will be inserted to the database
     * @param isUpdate   — if it's true and entity is already exist, the entity will updated
     */
    public void createEntity(BaseEntity baseEntity, boolean isUpdate) {
        PersistenceEntity persistenceEntity = converter.convertToEntity(baseEntity);
        manager.createEntity(persistenceEntity, baseEntity.getClass());
    }

    /**
     * Method that inserts entity to database
     *
     * @param id — the identification number of an entity
     * @return entity from database
     */
    public <T extends BaseEntity> T getEntity(long id, final Class<? extends BaseEntity> CLASS) {
        if (CACHE.containsKey(id))
            return (T) converter.convertToModel(CACHE.get(id), CLASS);

        PersistenceEntity persistenceEntity = manager.getEntity(id, CLASS);
        CACHE.put(id, persistenceEntity);

        return (T) converter.convertToModel(persistenceEntity, CLASS);
    }

    public void deleteEntity(long id) {
        manager.deleteEntity(id);
    }
}
