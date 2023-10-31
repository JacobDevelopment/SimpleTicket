package io.jacobking.simpleticket.gui.navigation;

public enum Route {

    DASHBOARD("dashboard.fxml"),

    NEW_TICKET("new_ticket.fxml"),
    TICKET_VIEWER("ticket_viewer.fxml"),

    DEPARTMENT_PORTAL("department_portal.fxml"),
    COMPANY_PORTAL("company_portal.fxml"),

    ABOUT("about.fxml");

    private final String path;

    Route(final String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
