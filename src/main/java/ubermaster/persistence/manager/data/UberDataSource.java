package ubermaster.persistence.manager.data;

import oracle.jdbc.OracleConnection;
import org.apache.tomcat.jdbc.pool.ConnectionPool;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
public class UberDataSource {
    private static ConnectionPool connectionPool;
    private final DataSource dataSource;

    @Autowired
    public UberDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        connectionPool = dataSource.getPool();
    }

    public static OracleConnection getConnection() throws SQLException {
        return connectionPool.getConnection().unwrap(OracleConnection.class);
    }

}


