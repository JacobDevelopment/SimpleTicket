package io.jacobking.simpleticket.gui.alert;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class Alerts {

    private static final String DEFAULT_CONFIRM_HEADER = "Are you sure you want to delete this item?";
    private static final String DEFAULT_CONFIRM_CONTENT = "This item will be deleted permanently and cannot be recovered.";
    private static final String DEFAULT_HEADER = "An error occurred.";
    private static final String NOT_SELECTED = "Item not selected.";

    private Alerts() {

    }

    public static AlertBuilder error(final String header, final String content) {
        return new AlertBuilder(Alert.AlertType.ERROR)
                .with(header, content);
    }

    public static void notSelected() {
        error(DEFAULT_HEADER, NOT_SELECTED).show();
    }

    public static AlertBuilder info(final String header, final String content) {
        return new AlertBuilder()
                .with(header, content);
    }

    public static AlertBuilder warning(final String header, final String content) {
        return new AlertBuilder(Alert.AlertType.WARNING)
                .with(header, content);
    }

    public static AlertBuilder confirmation(final String header, final String content) {
        return new AlertBuilder(Alert.AlertType.CONFIRMATION)
                .with(header, content);
    }

    public static void showError(final String header, final String content) {
        new AlertBuilder(Alert.AlertType.ERROR)
                .with(header, content)
                .show();
    }

    public static void showInfo(final String header, final String content) {
        new AlertBuilder()
                .with(header, content)
                .show();
    }

    public static void showWarning(final String header, final String content) {
        new AlertBuilder(Alert.AlertType.WARNING)
                .with(header, content)
                .show();
    }

    public static void showException(final Throwable throwable, final String header, final String content) {
        new AlertBuilder(throwable, header, content)
                .show();
    }

    public static Optional<ButtonType> showConfirmation(final String header, final String content) {
        return new AlertBuilder(Alert.AlertType.CONFIRMATION)
                .with(header, content)
                .setButtons(ButtonType.YES, ButtonType.NO)
                .showAndWait();
    }

    public static Optional<ButtonType> showDefaultConfirmation() {
        return showConfirmation(DEFAULT_CONFIRM_HEADER, DEFAULT_CONFIRM_CONTENT);
    }

}
