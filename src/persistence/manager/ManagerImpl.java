package persistence.manager;

import annotation.ObjectType;
import entity.model.BaseEntity;
import entity.model.Master;
import entity.model.Order;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.pool.OracleDataSource;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.BLOB;
import persistence.PersistenceEntity;

import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class ManagerImpl implements Manager
{
	private Connection connection;

	public ManagerImpl(final String USER, final String PASSWD) throws SQLException, ClassNotFoundException
	{
		OracleDataSource dataSource = new OracleDataSource();
		dataSource.setURL(CONNECTION.replace("USER", USER).replace("PASS", PASSWD));

		connection = dataSource.getConnection();}

	public void close()
	{
		try
		{
			connection.close();
		}

		catch (SQLException exc)
		{
			new SQLException(exc);
		}
	}

	public void createEntity(PersistenceEntity persistenceEntity, final Class<? extends BaseEntity> CLASS)
	{
		/*OracleCallableStatement calStat;
		ResultSet resultSet;

		try
		{
			HashMap<String, Object> hashMap = (HashMap<String, Object>) persistenceEntity.getAttributes();
			String[] elements = new String[3 + (hashMap.size() << 1)];
			int length = elements.length;
			elements[0] = Long.toString(persistenceEntity.getObject_id());
			elements[1] = CLASS.getAnnotation(ObjectType.class).value();
			elements[2] = persistenceEntity.getName();

			int i = 3;
			Iterator<String> iterator = hashMap.keySet().iterator();
			while (iterator.hasNext())
			{
				String attrID = iterator.next();
				elements[i] = attrID;
				++i;
				if (attrID.equals(Master.Model.SMOKE))
					elements[i] = Boolean.toString((Boolean)hashMap.get(attrID));

				// Или лучше маску использовать?
				else if (attrID.equals(Master.Model.PAYMENT))
					elements[i] = Integer.toString((int)hashMap.get(attrID));

				else if ()

				elements[i] = (String)hashMap.get(attrID);
				++i;
			}


			ArrayDescriptor descriptor = ArrayDescriptor.createDescriptor("ARRAY", connection);
			ARRAY array = new ARRAY(descriptor, connection, elements);

			OracleCallableStatement stmt = (OracleCallableStatement) connection.prepareCall("{call insertEntity(?)}");
			stmt.setARRAY(1, array);
			stmt.execute();
		}

		catch (SQLException exc)
		{
			exc.printStackTrace();
		}*/
	}


	public PersistenceEntity getEntity(long id, final Class<? extends BaseEntity> CLASS)
	{
		OracleCallableStatement calStat;
		ResultSet resultSet;
		PersistenceEntity persistenceEntity = new PersistenceEntity();
		try
		{
			calStat = (OracleCallableStatement) connection.prepareCall(GET_ENTITY);
			calStat.setString(1, Long.toString(id));
			calStat.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);
			calStat.execute();
			resultSet = calStat.getCursor(2);

			persistenceEntity.setObject_id(id);
			HashMap<String, Object> attrMap = new HashMap<>();
			while (resultSet.next())
			{
				String attr_id = resultSet.getString(2);
				if (attr_id.equals("-1"))
					persistenceEntity.setName(resultSet.getString(1));

				else
					attrMap.put(attr_id, resultSet.getString(1));
			}

			persistenceEntity.setAttributes(attrMap);
		}

		catch (SQLException exc)
		{
			exc.printStackTrace();
		}

		return persistenceEntity;
	}
}
