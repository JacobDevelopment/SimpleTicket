package io.jacobking.simpleticket.object;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public enum StatusType {

    OPEN("Open"),
    RESOLVED("Resolved"),
    IN_PROGRESS("In Progress"),
    PAUSED("Paused");

    private final String descriptor;
    StatusType(String descriptor) {
        this.descriptor = descriptor;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public static ObservableList<String> getObservableList() {
        final ObservableList<String> list = FXCollections.observableArrayList();
        for (final StatusType type : values()) {
            list.add(type.getDescriptor());
        }
        return list;
    }
}
