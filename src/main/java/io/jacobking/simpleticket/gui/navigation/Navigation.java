package io.jacobking.simpleticket.gui.navigation;


import io.jacobking.simpleticket.gui.alert.Alerts;
import io.jacobking.simpleticket.gui.screen.Screen;

import java.util.HashMap;
import java.util.Map;

public class Navigation {

    private static final Navigation instance = new Navigation();

    private final Map<Route, Screen> navigationMap;

    private Navigation() {
        this.navigationMap = new HashMap<>();
    }

    private Route activeRoute;

    public static Navigation getInstance() {
        if (instance == null)
            return new Navigation();
        return instance;
    }

    public void register(final Route route, final Screen screen) {
        navigationMap.putIfAbsent(route, screen);
    }

    public Screen getScreen(final Route route) {
        return navigationMap.getOrDefault(route, null);
    }

    public void display(final Route route, final boolean isModal, final Object... objects) {
        final Screen screen = getScreen(route);
        if (screen == null) {
            Alerts.showError("Could not find screen.", "Possible null route: " + route);
            return;
        }

        if (!isModal) {
            close(activeRoute);
        }

        this.activeRoute = route;
        screen.display(isModal, objects);
    }

    public void close(final Route route) {
        final Screen screen = getScreen(route);
        if (screen == null) {
            return;
        }
        screen.close();
    }
}
