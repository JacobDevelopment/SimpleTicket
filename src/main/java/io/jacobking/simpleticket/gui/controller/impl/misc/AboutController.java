package io.jacobking.simpleticket.gui.controller.impl.misc;

import io.jacobking.simpleticket.core.Version;
import io.jacobking.simpleticket.gui.controller.Controller;
import io.jacobking.simpleticket.gui.navigation.Navigation;
import io.jacobking.simpleticket.gui.navigation.Route;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class AboutController extends Controller {

    private static final String GITHUB_URL = "https://github.com/JacobDevelopment/SimpleTicket";

    @FXML private Label appVersionLabel;
    @FXML private Label jdkVersionLabel;
    @FXML private Label fxVersionLabel;
    @FXML private Hyperlink githubRepo;

    public AboutController() {
        super(Navigation.getInstance());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        githubRepo.setOnAction(event -> openWebpage(GITHUB_URL));
        appVersionLabel.setText(Version.getCurrent());
        jdkVersionLabel.setText(Version.getJDKVersion());
        fxVersionLabel.setText(Version.getFXVersion());
    }

    @FXML
    private void onClose() {
        getNavigation().close(Route.ABOUT);
    }

    private void openWebpage(final String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
