package io.jacobking.simpleticket.gui.navigation;

public enum Route {

    DASHBOARD("io/jacobking/simpleticket/dashboard.fxml"),
    NEW_TICKET("io/jacobking/simpleticket/new_ticket.fxml"),
    NEW_DEPARTMENT("io/jacobking/simpleticket/new_department.fxml"),
    NEW_EMPLOYEE("io/jacobking/simpleticket/new_employee.fxml"),
    NEW_COMPANY("io/jacobking/simpleticket/new_company.fxml"),
    QUICK_UPDATE("io/jacobking/simpleticket/quick_update.fxml"),
    TICKET_VIEWER("io/jacobking/simpleticket/ticket_viewer.fxml"),
    ABOUT("io/jacobking/simpleticket/about.fxml");

    private final String path;

    Route(final String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
