package io.jacobking.simpleticket.gui.screen;

import io.jacobking.simpleticket.App;
import io.jacobking.simpleticket.gui.controller.Controller;
import io.jacobking.simpleticket.gui.navigation.Route;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public abstract class Screen {

    private static final String TITLE = "Tickster";
    private final Route route;
    private Controller controller;

    private FXMLLoader loader;
    private Stage stage;
    private Scene scene;

    public Screen(final Route route, final Controller controller) {
        this.route = route;
        this.controller = controller;
    }

    public Screen(final Route route) {
        this.route = route;
        this.controller = null;
    }

    public void display(final boolean isModal, final Object... objects) {
        configure(isModal);
        show();
    }

    public void close() {
        if (stage != null) {
            stage.hide();
        }
    }

    public void configure(final boolean isModal) {
        final FXMLLoader loader = getLoader();
        if (controller != null) {
            loader.setController(controller);
        }

        if (controller == null) {
            throw new RuntimeException("Controller not set for target screen!");
        }


        buildScene(loader);

        if (scene == null)
            return;

        this.stage = new Stage();
        stage.setResizable(false);
        stage.centerOnScreen();
        if (isModal) {
            stage.initModality(Modality.APPLICATION_MODAL);
        }

        stage.setScene(scene);

        addShutdownHook();
    }

    public void setController(final Controller controller) {
        this.controller = controller;
        if (controller == null)
            return;

        if (loader == null)
            return;

        loader.setController(controller);
    }

    public void show() {
        stage.show();
    }

    private void buildScene(final FXMLLoader loader) {
        try {
            this.scene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private FXMLLoader getLoader() {
        final String routePath = route.getPath();
        try {
            final URL url = IOUtils.resourceToURL(routePath, App.class.getClassLoader());
            this.loader = new FXMLLoader(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loader;
    }

    private void addShutdownHook() {
        final Stage stage = getStage();
        if (stage != null) {
            stage.setOnCloseRequest(event -> {
            });
        }
    }

    public Stage getStage() {
        return stage;
    }

    public Controller getController() {
        return controller;
    }
}
