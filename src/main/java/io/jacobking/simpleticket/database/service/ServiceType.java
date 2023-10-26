package io.jacobking.simpleticket.database.service;

import io.jacobking.simpleticket.database.service.impl.*;


public enum ServiceType {

    DEPARTMENT(new DepartmentService()),
    EMPLOYEE(new EmployeeService()),
    COMPANY(new CompanyService()),
    TICKET(new TicketService()),
    TICKET_COMMENTS(new CommentService());

    private final Service<?> service;

    ServiceType(Service<?> service) {
        this.service = service;
    }

    public Service<?> getService() {
        return service;
    }
}
