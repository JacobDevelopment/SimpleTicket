package io.jacobking.simpleticket.object;


public enum PriorityType {

    LOW("Low"),
    MEDIUM("Medium"),
    HIGH("High"),
    IMMEDIATE("Immediate"),
    EMERGENCY("Emergency");

    private final String descriptor;

     PriorityType(final String descriptor) {
        this.descriptor = descriptor;
    }

    public String getDescriptor() {
        return descriptor;
    }
}
