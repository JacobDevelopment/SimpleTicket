package io.jacobking.simpleticket.gui.controller.proctor.non_impl;

import io.jacobking.simpleticket.database.Database;
import io.jacobking.simpleticket.gui.model.SettingsModel;

public class SettingsProctor {

    public SettingsModel fetch() {
        return new SettingsModel(Database.retrieve());
    }

}
