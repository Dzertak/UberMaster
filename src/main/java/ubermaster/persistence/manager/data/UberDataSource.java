package ubermaster.persistence.manager.data;

import oracle.jdbc.OracleConnection;
import org.apache.tomcat.jdbc.pool.ConnectionPool;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class UberDataSource {
    private static Logger log = Logger.getLogger(UberDataSource.class.getName());
    private static ConnectionPool connectionPool;
    private final DataSource dataSource;

    @Autowired
    public UberDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        connectionPool = dataSource.getPool();
    }

    public static OracleConnection getConnection() throws SQLException {
        try {
            return connectionPool.getConnection().unwrap(OracleConnection.class);
        } catch (SQLException ex) {
            log.log(Level.SEVERE, ex.getMessage(), ex);
            throw new SQLException(ex.getMessage());
        }
    }

}


