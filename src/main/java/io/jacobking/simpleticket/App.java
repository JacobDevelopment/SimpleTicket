package io.jacobking.simpleticket;

import io.jacobking.simpleticket.core.SimpleTicket;
import io.jacobking.simpleticket.gui.navigation.Navigation;
import io.jacobking.simpleticket.gui.navigation.Route;
import io.jacobking.simpleticket.utility.DateUtil;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.InputStream;

public class App extends Application {

    public static void main(String[] args) {
       launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println(DateUtil.nowLocalDateTime());
        final SimpleTicket ticket = SimpleTicket.getInstance();
        if (ticket.isStable()) {
            Navigation.getInstance().display(Route.DASHBOARD, false);
        }
    }

    public static InputStream getResourcesAsStream(final String path) {
        return App.class.getResourceAsStream(path);
    }


}
