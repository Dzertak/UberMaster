package persistence.facade;

import entity.model.BaseEntity;
import persistence.PersistenceEntity;
import persistence.converter.Converter;
import persistence.converter.ConverterImpl;
import persistence.manager.Manager;
import persistence.manager.ManagerImpl;

import java.util.HashMap;

/**
 * @author Serpye
 * */
public class Facade
{
	private Converter converter = new ConverterImpl();
	private Manager manager = new ManagerImpl();

	private HashMap<Long, PersistenceEntity> cache = new HashMap<>();

	/**
	 * 	Method that inserts entity to database
	 *
	 *	@param baseEntity an entity that will be inserted to the database
	 * */
	public void createEntity(BaseEntity baseEntity)
	{
		PersistenceEntity persistenceEntity = converter.convertToEntity(baseEntity);
		manager.createEntity(persistenceEntity);
	}

	/**
	 * 	Method that inserts entity to database
	 *
	 * 	@param id — the identification number of an entity
	 *	@param CLASS — a class that defines what entity will be returned
	 *
	 *  @return entity from database
	 * */
	public <T extends BaseEntity> T getEntity(long id, final Class<? extends BaseEntity> CLASS)
	{
		if (cache.containsKey(id))
			return (T) converter.convertToModel(cache.get(id), CLASS);

		PersistenceEntity persistenceEntity = manager.getEntity(id);
		cache.put(id, persistenceEntity);

		return (T)converter.convertToModel(persistenceEntity, CLASS);
	}
}
