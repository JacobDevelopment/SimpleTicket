package io.jacobking.simpleticket.gui.screen.impl.company;

import io.jacobking.simpleticket.gui.controller.impl.portal.CompanyPortalController;
import io.jacobking.simpleticket.gui.model.CompanyModel;
import io.jacobking.simpleticket.gui.navigation.Route;
import io.jacobking.simpleticket.gui.screen.Screen;

public class CompanyPortalScreen extends Screen {
    public CompanyPortalScreen() {
        super(Route.COMPANY_PORTAL);
    }

    @Override
    public void display(boolean isModal, Object... objects) {
        if (objects.length == 0) {
            setController(new CompanyPortalController(null));
            super.display(isModal, objects);
            return;
        }

        final CompanyModel model = (CompanyModel) objects[0];
        if (model != null) {
            setController(new CompanyPortalController(model));
            super.display(isModal, objects);
            return;
        }

        setController(new CompanyPortalController(null));
        super.display(isModal, objects);

    }
}
