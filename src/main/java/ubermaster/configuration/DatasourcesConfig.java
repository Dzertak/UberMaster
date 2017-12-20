package ubermaster.configuration;


import oracle.jdbc.pool.OracleDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import java.util.Locale;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
@PropertySource("classpath:application.properties")
@ConfigurationProperties("oracle")
public class DatasourcesConfig {

    /*private static final String PROP_DATABASE_DRIVER = "db.driver";
    private static final String PROP_DATABASE_PASSWORD = "db.password";
    private static final String PROP_DATABASE_URL = "db.url";
    private static final String PROP_DATABASE_USERNAME = "db.username";
    private static final String PROP_HIBERNATE_DIALECT = "db.hibernate.dialect";
    private static final String PROP_HIBERNATE_SHOW_SQL = "db.hibernate.show_sql";
    private static final String PROP_HIBERNATE_HBM2DDL_AUTO = "db.hibernate.hbm2ddl.auto";*/
    private static final String PROP_DATABASE_DRIVER = "spring.datasource.driver-class-name";
    private static final String PROP_DATABASE_PASSWORD = "spring.datasource.password";
    private static final String PROP_DATABASE_URL = "spring.datasource.url";
    private static final String PROP_DATABASE_USERNAME = "spring.datasource.username";
    private static final String PROP_HIBERNATE_DIALECT = "db.hibernate.dialect";
    private static final String PROP_HIBERNATE_SHOW_SQL = "db.hibernate.show_sql";
    private static final String PROP_HIBERNATE_HBM2DDL_AUTO = "db.hibernate.hbm2ddl.auto";

    @Resource
    private Environment env;

    @Bean
    public OracleDataSource dataSource() throws SQLException {
        Locale.setDefault(new Locale("en","EN"));
        OracleDataSource dataSource = new OracleDataSource();

        dataSource.setPassword(env.getRequiredProperty(PROP_DATABASE_PASSWORD));
        dataSource.setURL(env.getRequiredProperty(PROP_DATABASE_URL)
                .replace("USER", env.getRequiredProperty(PROP_DATABASE_USERNAME))
                .replace("PASS", env.getRequiredProperty(PROP_DATABASE_PASSWORD)));
        dataSource.setUser(env.getRequiredProperty(PROP_DATABASE_USERNAME));
        dataSource.setDriverType(env.getRequiredProperty(PROP_DATABASE_DRIVER));

        return dataSource;
    }

    /*@Bean
    public OracleDataSource dataSource() throws SQLException {
        Locale.setDefault(new Locale("en","EN"));
        OracleDataSource dataSource = new OracleDataSource();

        dataSource.setPassword(env.getRequiredProperty(PROP_DATABASE_PASSWORD));
        dataSource.setURL(env.getRequiredProperty(PROP_DATABASE_URL)
                .replace("USER", env.getRequiredProperty(PROP_DATABASE_USERNAME))
                .replace("PASS", env.getRequiredProperty(PROP_DATABASE_PASSWORD)));
        dataSource.setUser(env.getRequiredProperty(PROP_DATABASE_USERNAME));
        dataSource.setDriverType(env.getRequiredProperty(PROP_DATABASE_DRIVER));

        return dataSource;
    }*/

    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put(PROP_HIBERNATE_DIALECT, env.getRequiredProperty(PROP_HIBERNATE_DIALECT));
        properties.put(PROP_HIBERNATE_SHOW_SQL, env.getRequiredProperty(PROP_HIBERNATE_SHOW_SQL));
        properties.put(PROP_HIBERNATE_HBM2DDL_AUTO, env.getRequiredProperty(PROP_HIBERNATE_HBM2DDL_AUTO));

        return properties;
    }


}
