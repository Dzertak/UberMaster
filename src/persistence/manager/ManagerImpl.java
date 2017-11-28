package persistence.manager;

import entity.model.BaseEntity;
import entity.model.Order;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.pool.OracleDataSource;
import oracle.sql.ARRAY;
import persistence.PersistenceEntity;

import java.sql.*;
import java.util.HashMap;

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

	public void createEntity(PersistenceEntity persistenceEntity)
	{
		OracleCallableStatement calStat;
		ResultSet resultSet;

		try
		{
			calStat = (OracleCallableStatement) connection.prepareCall(GET_ATTR_TYPES);
			calStat.setString(1, Long.toString(persistenceEntity.getObject_id()));
			calStat.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);
			calStat.execute();
			resultSet = calStat.getCursor(2);
		}

		catch (SQLException exc)
		{
			exc.printStackTrace();
		}
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
