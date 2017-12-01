package ubermaster;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class OracleConnector
{
	private static Connection connection;

	public static void create()
		throws SQLException, ClassNotFoundException,
		IllegalAccessException, InstantiationException
	{
		create("userPR", "PR");
	}

	public static void create(final String USER, final String PASSWD)
		throws SQLException, ClassNotFoundException,
		IllegalAccessException, InstantiationException
	{
		Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();

		String URL = "jdbc:oracle:thin:@//localhost:1521/XE";
		Properties info = new Properties( );
		info.put("user", USER);
		info.put("password", PASSWD);

		connection = DriverManager.getConnection(URL, info);
	}

	public static void close() throws SQLException
	{
		connection.close();
	}

	public static Connection getConnection()
	{
		return connection;
	}
}
