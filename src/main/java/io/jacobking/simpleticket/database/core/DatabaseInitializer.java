package io.jacobking.simpleticket.database.core;

import io.jacobking.simpleticket.App;
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
            System.out.println("Empty Current Queries");
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
            e.printStackTrace();
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
