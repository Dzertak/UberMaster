package ubermaster.service;

import org.apache.tomcat.jdbc.pool.DataSource;
import ubermaster.model.StackoverflowWebsite;
import oracle.jdbc.pool.OracleDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


@Service
public class StackoverflowService {
    private static List<StackoverflowWebsite> items = new ArrayList<>();
    @Autowired
    private DataSource dataSource;

    public List<StackoverflowWebsite> findAll() throws SQLException {
        Connection connection = dataSource.getPool().getConnection();
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("Select * from Objects");
        while (rs.next()){
            items.add(new StackoverflowWebsite(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),
                    rs.getString(5)));
        }
        return items;
    }
}
