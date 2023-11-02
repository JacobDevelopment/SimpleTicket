package io.jacobking.simpleticket.gui.screen;

import io.jacobking.simpleticket.App;
import io.jacobking.simpleticket.core.Version;
import io.jacobking.simpleticket.gui.controller.Controller;
import io.jacobking.simpleticket.gui.navigation.Route;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public abstract class Screen {

    private static final String ICON_PATH = "icons/icon.png";
    private static final String TITLE = String.format("SimpleTicket - %s", Version.getCurrent());
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


        buildScene();

        if (scene == null)
            return;

        this.stage = new Stage();
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.setTitle(TITLE);
        setIcon(stage);
        if (isModal) {
            stage.initModality(Modality.APPLICATION_MODAL);
        }

        stage.setScene(scene);
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

    private void buildScene() {
        try {
            this.scene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private FXMLLoader getLoader() {
        final String path = route.getPath();
        final URL url = App.class.getResource(path);
        this.loader = new FXMLLoader(url);
        return loader;
    }

    private void setIcon(final Stage stage) {
        try (final InputStream stream = App.class.getResourceAsStream(ICON_PATH)) {
            if (stream != null) {
                stage.getIcons().add(new Image(stream));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Stage getStage() {
        return stage;
    }

    public Controller getController() {
        return controller;
    }
}
