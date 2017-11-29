package ubermaster.persistence.manager;

import oracle.jdbc.pool.OracleDataSource;
import ubermaster.annotation.ObjectType;
import oracle.jdbc.OracleCallableStatement;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import ubermaster.entity.model.BaseEntity;
import ubermaster.persistence.PersistenceEntity;
import ubermaster.persistence.converter.ConverterImpl;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;

public class ManagerImpl implements Manager
{
	private Connection connection;

	public ManagerImpl(final String USER, final String PASSWD) throws SQLException, ClassNotFoundException
	{

		OracleDataSource dataSource= new OracleDataSource();

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
		OracleCallableStatement calStat;
		ResultSet resultSet;

		try
		{
			HashMap<String, Object> hashMap = (HashMap<String, Object>) persistenceEntity.getAttributes();
			String[] elements = new String[3 + (hashMap.size() << 1)];
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
				elements[i] = ConverterImpl.convertObjectToString(hashMap.get(attrID));
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
				// There're params for BaseEntity
				if (attr_id.equals("-1"))
					persistenceEntity.setName(resultSet.getString(1));

				// There're params for Attributes
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
