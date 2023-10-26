package io.jacobking.simpleticket.gui.screen.impl.company;

import io.jacobking.simpleticket.gui.controller.impl.NewCompanyController;
import io.jacobking.simpleticket.gui.navigation.Route;
import io.jacobking.simpleticket.gui.screen.Screen;

public class NewCompanyScreen extends Screen {
    public NewCompanyScreen() {
        super(Route.NEW_COMPANY);
    }

    @Override
    public void display(boolean isModal, Object... objects) {
        final NewCompanyController controller = new NewCompanyController();
        setController(controller);
        super.display(isModal, objects);
    }


}
