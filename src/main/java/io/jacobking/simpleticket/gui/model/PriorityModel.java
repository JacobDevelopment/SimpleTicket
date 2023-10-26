package io.jacobking.simpleticket.gui.model;

import io.jacobking.simpleticket.object.PriorityType;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class PriorityModel {

    private final ObjectProperty<PriorityType> priority;

    public PriorityModel(final PriorityType priorityType) {
        this.priority = new SimpleObjectProperty<>(priorityType);
    }

    public void setStatus(final PriorityType priority) {
        this.priority.setValue(priority);
    }

    public PriorityType getStatusType() {
        return this.priority.getValue();
    }


}
