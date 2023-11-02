package io.jacobking.simpleticket.database.core;

import io.jacobking.simpleticket.App;
import io.jacobking.simpleticket.gui.alert.Alerts;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
    private static final String CURRENT_SQL = "sql/current.sql";

    private final Connection connection;

    public DatabaseInitializer(final Connection connection) {
        this.connection = connection;
    }

    public void executeQueries() {
        final String[] queries = getSplitQueries();
        if (queries.length == 0) {
            Alerts.showError("Empty Split Queries", "sql/current.sql has no queries!");
            return;
        }

        for (final String query : queries) {
            final String sql = query.concat(";");
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

    private String[] getSplitQueries() {
        try {
            final String base = getQueries();
            if (base.isEmpty()) {
                return new String[0];
            }

            return base.split(";");
        } catch (IOException e) {
            return new String[0];
        }
    }

    private String getQueries() throws IOException {
        final InputStream stream = App.class.getResourceAsStream(CURRENT_SQL);
        return (stream != null) ?
                IOUtils.toString(stream, StandardCharsets.UTF_8)
                : "";
    }

}
