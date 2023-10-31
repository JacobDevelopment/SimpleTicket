package io.jacobking.simpleticket.core;

import io.jacobking.simpleticket.database.Database;
import io.jacobking.simpleticket.gui.navigation.Navigation;
import io.jacobking.simpleticket.gui.navigation.Route;
import io.jacobking.simpleticket.gui.screen.impl.AboutScreen;
import io.jacobking.simpleticket.gui.screen.impl.DashboardScreen;
import io.jacobking.simpleticket.gui.screen.impl.company.NewCompanyScreen;
import io.jacobking.simpleticket.gui.screen.impl.department.DepartmentPortalScreen;
import io.jacobking.simpleticket.gui.screen.impl.employee.NewEmployeeScreen;
import io.jacobking.simpleticket.gui.screen.impl.ticket.NewTicketScreen;
import io.jacobking.simpleticket.gui.screen.impl.ticket.QuickUpdateScreen;
import io.jacobking.simpleticket.gui.screen.impl.ticket.TicketViewerScreen;
import javafx.application.Platform;

public class SimpleTicket {

    private static final SimpleTicket instance = new SimpleTicket();

    private final Config config;
    private final Database database;
    private final Navigation navigation;

    private SimpleTicket() {
        this.config = Config.getInstance();
        this.database = Database.getInstance();
        this.navigation = Navigation.getInstance();
        if (isStable())
            initializeNavigation();
    }

    public static SimpleTicket getInstance() {
        if (instance == null)
            return new SimpleTicket();
        return instance;
    }

    public boolean isStable() {
        return database.isStable();
    }

    private void initializeNavigation() {
        navigation.register(Route.DASHBOARD, new DashboardScreen());
        navigation.register(Route.NEW_TICKET, new NewTicketScreen());
        navigation.register(Route.NEW_EMPLOYEE, new NewEmployeeScreen());
        navigation.register(Route.NEW_COMPANY, new NewCompanyScreen());
        navigation.register(Route.QUICK_UPDATE, new QuickUpdateScreen());
        navigation.register(Route.TICKET_VIEWER, new TicketViewerScreen());
        navigation.register(Route.ABOUT, new AboutScreen());
        navigation.register(Route.DEPARTMENT_PORTAL, new DepartmentPortalScreen());
    }

    public Navigation getNavigation() {
        return navigation;
    }

    public void shutdown() {
        System.out.println("Shutdown Execution");
        Platform.runLater(database::close);
        Platform.exit();
    }

}
