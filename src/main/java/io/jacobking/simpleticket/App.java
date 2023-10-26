package io.jacobking.simpleticket;

import io.jacobking.simpleticket.core.SimpleTicket;
import io.jacobking.simpleticket.gui.navigation.Navigation;
import io.jacobking.simpleticket.gui.navigation.Route;
import javafx.application.Application;
import javafx.stage.Stage;

import java.net.URLClassLoader;

public class App extends Application {

    public static void main(String[] args) {
       launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        final SimpleTicket ticket = SimpleTicket.getInstance();
        if (ticket.isStable()) {
            Navigation.getInstance().display(Route.DASHBOARD, false);
        }
    }
}
