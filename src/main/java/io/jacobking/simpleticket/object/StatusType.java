package io.jacobking.simpleticket.object;

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
}
