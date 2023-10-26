module io.jacobking.simpleticket {
    requires javafx.controls;
    requires javafx.fxml;


    opens io.jacobking.simpleticket to javafx.fxml;
    exports io.jacobking.simpleticket;
}