package io.jacobking.simpleticket.database.connector;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

public class JOOQConnector {

    private final SQLiteConnector connector;

    private DSLContext context = null;

    public JOOQConnector(final SQLiteConnector connector) {
        this.connector = connector;
        setContext();
    }

    private void setContext() {
        if (connector.isConnected()) {
            this.context = DSL.using(connector.getConnection(), SQLDialect.SQLITE);
        }
    }

    public DSLContext getContext() {
        return context;
    }

}
