package io.jacobking.simpleticket.database;

import io.jacobking.simpleticket.core.Config;
import io.jacobking.simpleticket.database.connector.JOOQConnector;
import io.jacobking.simpleticket.database.connector.SQLiteConnector;
import io.jacobking.simpleticket.database.service.ServiceDispatcher;
import io.jacobking.simpleticket.database.service.ServiceType;
import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.TableField;

import java.sql.SQLException;
import java.util.List;


public class Database {

    private static final Database instance = new Database();

    private final SQLiteConnector connector;
    private final JOOQConnector jooqConnector;
    private final ServiceDispatcher dispatcher;

    private Database() {
        this.connector = new SQLiteConnector(Config.getInstance().getProperty("url"));
        this.jooqConnector = new JOOQConnector(connector);
        this.dispatcher = new ServiceDispatcher(jooqConnector);
    }

    public static Database getInstance() {
        if (instance == null)
            return new Database();
        return instance;
    }

    public void close() {
        if (connector.getConnection() != null) {
            try {
                connector.getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isStable() {
        return (connector.isConnected());
    }

    public static <T> boolean insert(final ServiceType type, final T value) {
        return getInstance().getDispatcher().insert(type, value);
    }

    public static <T> T insertReturning(final ServiceType type, final T value) {
        return getInstance().getDispatcher().insertReturning(type, value);
    }

    public static <T> T fetch(final ServiceType type, final int id) {
        return getInstance().getDispatcher().fetch(type, id);
    }

    public static <T> T fetchByCondition(final ServiceType type, final Condition condition) {
        return getInstance().getDispatcher().fetchByCondition(type, condition);
    }

    public static <R extends Record, V> boolean update(final ServiceType type, final TableField<R, V> field, final V value, final Condition condition) {
        return getInstance().getDispatcher().update(type, field, value, condition);
    }

    public static <T> boolean update(final ServiceType type, final T object) {
        return getInstance().getDispatcher().update(type, object);
    }

    public static <T> boolean delete(final ServiceType type, final int id) {
        return getInstance().getDispatcher().delete(type, id);
    }

    public static <T> List<T> fetchAll(final ServiceType type, final Condition condition) {
        return getInstance().getDispatcher().fetchAll(type, condition);
    }

    public static <T> List<T> fetchAll(final ServiceType type) {
        return getInstance().getDispatcher().fetchAll(type);
    }


    private ServiceDispatcher getDispatcher() {
        return dispatcher;
    }

}
