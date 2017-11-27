package persistence.manager;

import entity.model.BaseEntity;
import persistence.PersistenceEntity;

public interface Manager
{
	void createEntity(PersistenceEntity persistenceEntity);

	PersistenceEntity getEntity(long id);
}
