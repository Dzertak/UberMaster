package ubermaster.entityGenerator.sql;

import oracle.jdbc.OracleCallableStatement;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import org.apache.log4j.Logger;
import ubermaster.entityGenerator.entity.Entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static ubermaster.persistence.manager.data.UberDataSource.getConnection;

/**
 * @author Serpye
 * */
public final class SQL_Manager implements _SQL
{
/*::|		FIELD		:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
    private static Logger log = Logger.getLogger(SQL_Manager.class.getName());
/*::|		CONSTRUCTOR		:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
	private SQL_Manager(){}
/*::|		SUB_CLASS		:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
/*::|		F / P		:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
	public static void insertEntity(Entity sqcEntity[])
	{
		final int LEN_SQC_ENTITY = sqcEntity.length;
		for (int i = 0; i < LEN_SQC_ENTITY; ++i)
		{
			String sqcParam[] = sqcEntity[i].getAllParamAsArray();
			insertEntity(deleteFirst(sqcParam));
		}
	}

	private static void insertEntity(String sqcParam[])
	{
		try
		{
			Connection connection = getConnection();
			ArrayDescriptor descriptor = ArrayDescriptor.createDescriptor
					(
						"ARRAY",
						connection
					);
			ARRAY array = new ARRAY(descriptor, connection, sqcParam);

			OracleCallableStatement stmt = (OracleCallableStatement)
								connection.prepareCall(INSERT_ENTITY);
			stmt.setARRAY(1, array);
			stmt.execute();
		}

		catch (SQLException exc)
		{
			log.error(exc.getMessage(), exc);
			//exc.printStackTrace();
		}
	}

	public static void deleteEntity(long id)
	{
		try
		{
			Connection connection = getConnection();
			PreparedStatement statement = connection.prepareStatement(DELETE_ENTITY);
			statement.setString(1, Long.toString(id));
			statement.execute();
		}

		catch (SQLException exc)
		{
            log.error(exc.getMessage(), exc);
			//exc.printStackTrace();
		}
	}

	public static void deleteAllEntities()
	{
		try
		{
			Connection connection = getConnection();
			PreparedStatement statement = connection.prepareStatement(DELETE_ALL_ENTITIES);
			statement.execute();
		}

		catch (SQLException exc)
		{
            log.error(exc.getMessage(), exc);
			//exc.printStackTrace();
		}
	}

	private static String[] deleteFirst(String sqcString[])
	{
		String sqcResult[] = new String[sqcString.length - 1];

		int length = sqcResult.length;
		for (int i = 0; i < length;)
			sqcResult[i] = sqcString[++i];

		return sqcResult;
	}
}
