package ubermaster;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Locale;

public class OracleConnector
{
	private static Connection connection;

	public static void create()
		throws SQLException, ClassNotFoundException,
		IllegalAccessException, InstantiationException
	{
		create("userPR2", "PR2");
	}

	public static void create(final String USER, final String PASSWD)
		throws SQLException, ClassNotFoundException,
		IllegalAccessException, InstantiationException
	{
		Locale.setDefault(new Locale("es","ES"));
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
