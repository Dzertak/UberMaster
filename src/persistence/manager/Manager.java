package persistence.manager;

import entity.model.BaseEntity;
import persistence.PersistenceEntity;

import java.io.Closeable;

public interface Manager extends Closeable
{
	String GET_ATTR_TYPES = "{call getAttrTypeIds(?, ?)}";
	String GET_ATTR_COUNT = "{call getAttrTypeCount(?, ?)}";
	String GET_ENTITY = "{call getEntity(?, ?)}";
	String CONNECTION = "jdbc:oracle:thin:USER/PASS@localhost:1521:XE";

	void createEntity(PersistenceEntity persistenceEntity, final Class<? extends BaseEntity> CLASS);

	PersistenceEntity getEntity(long id, final Class<? extends BaseEntity> CLASS);
}
