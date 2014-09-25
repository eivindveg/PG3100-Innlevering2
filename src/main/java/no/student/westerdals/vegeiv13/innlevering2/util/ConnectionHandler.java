package no.student.westerdals.vegeiv13.innlevering2.util;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

import java.sql.Connection;
import java.sql.SQLException;

public final class ConnectionHandler {

    private static ConnectionHandler instance;
    private final MysqlDataSource dataSource = new MysqlDataSource();
    private Configuration configuration;

    private ConnectionHandler() {
        configuration = loadConfiguration();
        reconfigure();
    }

    public void reconfigure() {
        final String host = configuration.getString("database.host");
        final String user = configuration.getString("database.user");
        final String password = configuration.getString("database.password");
        final String database = configuration.getString("database.database");
        dataSource.setUrl(host);
        dataSource.setUser(user);
        dataSource.setPassword(password);
        dataSource.setDatabaseName(database);
    }

    public static ConnectionHandler getInstance() {
        if (instance == null) {
            instance = new ConnectionHandler();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    private Configuration loadConfiguration() {
        try {
            configuration = new XMLConfiguration("config.xml");
        } catch (ConfigurationException e) {
            try {
                configuration = new XMLConfiguration("conf/config.xml");
            } catch (ConfigurationException e1) {
                // TODO LOG
                throw new RuntimeException("Unable to load configuration!");
            }
        }
        return configuration;
    }
}
