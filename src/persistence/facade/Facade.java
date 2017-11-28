package persistence.facade;

import entity.model.BaseEntity;
import persistence.PersistenceEntity;
import persistence.converter.Converter;
import persistence.converter.ConverterImpl;
import persistence.manager.Manager;
import persistence.manager.ManagerImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * @author Serpye
 * */
public class Facade
{
	private Converter converter = new ConverterImpl();
	private Manager manager;
	private final HashMap<Long, PersistenceEntity> CACHE = new HashMap<>();

	public Facade(final String USER, final String PASSWD) throws SQLException, ClassNotFoundException
	{
		manager = new ManagerImpl(USER, PASSWD);
	}

	/**
	 * 	Method that inserts entity to database
	 *
	 *	@param baseEntity an entity that will be inserted to the database
	 *
	 *  @param isUpdate — if it's true and entity is already exist, the entity will updated
	 * */
	public void createEntity(BaseEntity baseEntity, boolean isUpdate)
	{
		PersistenceEntity persistenceEntity = converter.convertToEntity(baseEntity);
		manager.createEntity(persistenceEntity);
	}

	/**
	 * 	Method that inserts entity to database
	 *
	 * 	@param id — the identification number of an entity
	 *
	 *  @return entity from database
	 * */
	public <T extends BaseEntity> T getEntity(long id, final Class<? extends BaseEntity> CLASS)
	{
		if (CACHE.containsKey(id))
			return (T) converter.convertToModel(CACHE.get(id), CLASS);

		PersistenceEntity persistenceEntity = manager.getEntity(id, CLASS);
		CACHE.put(id, persistenceEntity);

		return (T)converter.convertToModel(persistenceEntity, CLASS);
	}

	public void dispose() throws IOException
	{
		manager.close();
	}
}
