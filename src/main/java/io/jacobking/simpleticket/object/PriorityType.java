package io.jacobking.simpleticket.object;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public enum PriorityType {

    LOW("Low"),
    MEDIUM("Medium"),
    HIGH("High"),
    EMERGENCY("Emergency");

    private final String descriptor;

    PriorityType(final String descriptor) {
        this.descriptor = descriptor;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public static ObservableList<String> getObservableList() {
        final ObservableList<String> list = FXCollections.observableArrayList();
        for (final PriorityType type : values()) {
            list.add(type.getDescriptor());
        }
        return list;
    }
}
