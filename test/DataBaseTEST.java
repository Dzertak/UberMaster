import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.pool.OracleDataSource;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;

public class DataBaseTEST
{
	private Connection connection;

	@Before
	public void start() throws SQLException, ClassNotFoundException
	{
		OracleDataSource dataSource = new OracleDataSource();
		dataSource.setURL("jdbc:oracle:thin:userPR/PR@localhost:1521:XE");

		connection = dataSource.getConnection();
	}

	@Test
	public void getValueFromFunction() throws SQLException
	{
		OracleCallableStatement calStat = null;

		ResultSet rs = null;

		try
		{
			calStat = (OracleCallableStatement) connection.prepareCall("{call ttt2( ?, ? )}");
			calStat.setString(1, "1");
			calStat.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);
			calStat.execute();
			rs = calStat.getCursor(2);

			while (rs.next())
			{
				System.out.println(rs.getInt(1));
			}

		}

		finally
		{
			try { rs.close(); } catch (Exception ex) {}
			try { calStat.close(); } catch (Exception ex) {}
			try { connection.close(); } catch (Exception ex) {}
		}
	}
}
