package io.jacobking.simpleticket.database.connector;

import io.jacobking.simpleticket.core.Version;
import io.jacobking.simpleticket.database.core.DatabaseInitializer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnector {

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private final String url;

    private Connection connection = null;

    public SQLiteConnector(final String url) {
        this.url = String.format("jdbc:sqlite:%s", url);
        setConnection();
    }

    private void setConnection() {
        try {
            this.connection = DriverManager.getConnection(url);

            if (this.connection != null) {
                createInitializer();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        if (this.connection == null) {
            setConnection();
            return getConnection();
        }

        return connection;
    }

    private void createInitializer() {
        new DatabaseInitializer(connection);
    }


    public boolean isConnected() {
        return this.connection != null;
    }
}
