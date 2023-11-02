package io.jacobking.simpleticket.gui.alert;

import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Optional;

public class AlertBuilder {

    private Alert alert;

    public AlertBuilder(final Alert.AlertType type) {
        this.alert = new Alert(type);
    }

    public AlertBuilder() {
        this.alert = new Alert(Alert.AlertType.INFORMATION);
    }

    /**
     * Source: http://www.java2s.com/example/java/javafx/show-javafx-exception-dialog.html
     */
    public AlertBuilder(final Throwable throwable, final String header, final String content) {
        this.alert = new Alert(Alert.AlertType.ERROR);
        alert.setResizable(true);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.getDialogPane().setContent(new Label(header));

        final StringWriter stringWriter = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(stringWriter);
        throwable.printStackTrace(printWriter);

        final TextArea textArea = new TextArea(stringWriter.toString());
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        final GridPane grid = new GridPane();
        grid.setMaxWidth(Double.MAX_VALUE);
        grid.add(new Label(content), 0, 0);
        grid.add(textArea, 0, 1);
        alert.getDialogPane().setExpandableContent(grid);
        alert.getDialogPane().setExpanded(true);
    }

    public AlertBuilder with(final String header, final String content) {
        this.alert.setHeaderText(header);
        this.alert.setContentText(content);
        return this;
    }

    public AlertBuilder withHeader(final String header) {
        this.alert.setHeaderText(header);
        return this;
    }

    public AlertBuilder withContent(final String content) {
        this.alert.setContentText(content);
        return this;
    }

    public AlertBuilder setButtons(final ButtonType... buttons) {
        this.alert.getButtonTypes().clear();
        this.alert.getButtonTypes().addAll(Arrays.asList(buttons));
        return this;
    }

    public AlertBuilder onHidden(final EventHandler<DialogEvent> event) {
        this.alert.setOnHidden(event);
        return this;
    }

    public AlertBuilder onShowing(final EventHandler<DialogEvent> event) {
        this.alert.setOnShowing(event);
        return this;
    }

    public AlertBuilder onClosed(final EventHandler<DialogEvent> event) {
        this.alert.setOnCloseRequest(event);
        return this;
    }

    public AlertBuilder onHiding(final EventHandler<DialogEvent> event) {
        this.alert.setOnHidden(event);
        return this;
    }

    public Optional<ButtonType> showAndWait() {
        return this.alert.showAndWait();
    }

    public void show() {
        this.alert.show();
    }

    public void close() {
        if (this.alert != null)
            this.alert.close();
    }
}
