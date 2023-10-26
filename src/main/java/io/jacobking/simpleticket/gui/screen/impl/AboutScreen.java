package io.jacobking.simpleticket.gui.screen.impl;

import io.jacobking.simpleticket.gui.controller.impl.AboutController;
import io.jacobking.simpleticket.gui.navigation.Route;
import io.jacobking.simpleticket.gui.screen.Screen;

public class AboutScreen extends Screen {
    public AboutScreen() {
        super(Route.ABOUT, new AboutController());
    }
}
