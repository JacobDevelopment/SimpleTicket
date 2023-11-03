package io.jacobking.simpleticket.database.core;

import io.jacobking.simpleticket.App;
import io.jacobking.simpleticket.core.Version;
import io.jacobking.simpleticket.gui.alert.Alerts;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.*;

public class DatabaseInitializer {
    private static final String CURRENT_SQL = "sql/base.sql";
    private static final String UPGRADE_SQL = "sql/v0.1.2.sql";

    private final Version currentVersion = Version.getCurrent();

    private final Version schemaVersion = new Version();

    private final Connection connection;

    public DatabaseInitializer(final Connection connection) {
        this.connection = connection;
        establishVersion();
    }

    private void establishVersion() {
        if (this.connection != null) {
            try (final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM CONFIGURATION WHERE ID = 1;")) {

                final ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    schemaVersion.setMajor(resultSet.getInt(2));
                    schemaVersion.setMinor(resultSet.getInt(3));
                    schemaVersion.setPatch(resultSet.getInt(4));
                    schemaVersion.setSuffix(resultSet.getString(5));
                }

                compareVersions();
            } catch (SQLException e) {
                Alerts.showException(e.fillInStackTrace(), "SQLException", "Could not execute prepared statement when grabbing versioning schemantics.");
            }
        }
    }

    // This allows us to add columns if the update requires it.
    private void compareVersions() {
        final int comparison = currentVersion.compareTo(schemaVersion);
        if (comparison > 0) {
            executeQueries(UPGRADE_SQL);
        } else if (comparison < 0) {
            executeQueries(CURRENT_SQL);
        }
    }

    public void executeQueries(final String path) {
        final String[] queries = getSplitQueries(path);
        if (queries.length == 0) {
            Alerts.showError("Empty Split Queries", "sql/base.sql has no queries!");
            return;
        }

        for (final String query : queries) {
            final String sql = query.concat(";");
            System.out.println(sql);
            executeQuery(sql);
        }
    }

    private void executeQuery(final String sql) {
        if (this.connection == null)
            return;

        try (final Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            Alerts.showException(e.fillInStackTrace(), "SQL Exception", "There was an error executing a query.");
        }
    }

    private String[] getSplitQueries(final String path) {
        try {
            final String base = getQueries(path);
            if (base.isEmpty()) {
                return new String[0];
            }

            return base.split(";");
        } catch (IOException e) {
            return new String[0];
        }
    }

    private String getQueries(final String path) throws IOException {
        final InputStream stream = App.class.getResourceAsStream(path);
        return (stream != null) ?
                IOUtils.toString(stream, StandardCharsets.UTF_8)
                : "";
    }

}
