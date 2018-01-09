package entityGenerator.sql;

import oracle.jdbc.OracleCallableStatement;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import entityGenerator.entity.Entity;

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
/*::|		CONSTRUCTOR		:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
	private SQL_Manager(){}
/*::|		SUB_CLASS		:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
/*::|		F / P		:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
	public static void insertEntity(Entity sqcEntity[])
	{
		final int LEN_SQC_ENTITY = sqcEntity.length;
		for (int i = 0; i < LEN_SQC_ENTITY; ++i)
			insertEntity(sqcEntity[i].getAllParamAsArray());
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
			exc.printStackTrace();
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
			exc.printStackTrace();
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
			exc.printStackTrace();
		}
	}
}
