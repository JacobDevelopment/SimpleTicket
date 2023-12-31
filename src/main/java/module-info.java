module io.jacobking.simpleticket {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jooq;
    requires java.desktop;
    requires org.apache.commons.io;
    requires org.xerial.sqlitejdbc;

    opens io.jacobking.simpleticket to javafx.fxml, org.jooq, org.apache.commons.io;
    exports io.jacobking.simpleticket;

    opens io.jacobking.simpleticket.gui.controller.impl to javafx.fxml;
    exports io.jacobking.simpleticket.gui.controller.impl;

    opens io.jacobking.simpleticket.tables.records to org.jooq;
    exports io.jacobking.simpleticket.tables.records;

    opens io.jacobking.simpleticket.tables.pojos to org.jooq;
    exports io.jacobking.simpleticket.tables.pojos;

    opens io.jacobking.simpleticket.gui.view.table to javafx.fxml;
    exports io.jacobking.simpleticket.gui.view.table;

    opens io.jacobking.simpleticket.gui.view.combobox to javafx.fxml;
    exports io.jacobking.simpleticket.gui.view.combobox;
}