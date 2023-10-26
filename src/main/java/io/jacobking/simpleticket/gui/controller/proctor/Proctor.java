package io.jacobking.simpleticket.gui.controller.proctor;

import io.jacobking.simpleticket.gui.controller.proctor.impl.CompanyProctor;
import io.jacobking.simpleticket.gui.controller.proctor.impl.DepartmentProctor;
import io.jacobking.simpleticket.gui.controller.proctor.impl.EmployeeProctor;
import io.jacobking.simpleticket.gui.controller.proctor.impl.TicketProctor;

public class Proctor {

    private static final Proctor instance = new Proctor();

    private final TicketProctor ticketProctor;
    private final EmployeeProctor employeeProctor;
    private final DepartmentProctor departmentProctor;
    private final CompanyProctor companyProctor;

    private Proctor() {
        this.ticketProctor = new TicketProctor();
        this.employeeProctor = new EmployeeProctor();
        this.departmentProctor = new DepartmentProctor();
        this.companyProctor = new CompanyProctor();
    }

    public static Proctor getInstance() {
        if (instance == null)
            return new Proctor();
        return instance;
    }

    public TicketProctor ticket() {
        return ticketProctor;
    }

    public EmployeeProctor employee() {
        return employeeProctor;
    }

    public DepartmentProctor department() {
        return departmentProctor;
    }

    public CompanyProctor company() {
        return companyProctor;
    }
}
