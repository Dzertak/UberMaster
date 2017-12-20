package ubermaster.persistence.manager;

import org.springframework.stereotype.Service;
import ubermaster.entity.model.BaseEntity;
import ubermaster.persistence.PersistenceEntity;

@Service
public interface Manager
{
/*::|		FIELD		:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
    String GET_ENTITY = "{call getEntity(?, ?)}";
    String GET_USER = "{call getUser(?, ?, ?)}";
    String DELETE_ENTITY = "delete from Objects where object_id = ?";
    String INSERT_ENTITY = "{call insertEntity(?)}";

    String ATTR_OBJECT_ID = "-1";
    String ATTR_OBJECT_TYPE_ID = "-2";
    String ATTR_NAME = "-3";
    String ATTR_DESCR = "-4";
/*::|		SUB_CLASS		:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
/*::|		F / P		:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
    void createEntity(PersistenceEntity persistenceEntity, final Class<? extends BaseEntity> CLASS);

    PersistenceEntity getEntity(long id, final Class<? extends BaseEntity> CLASS);

    void deleteEntity(long id);

    void updateEntity(PersistenceEntity persistenceEntity, final Class<? extends BaseEntity> CLASS);
}
