package io.jacobking.simpleticket.gui.view.combobox;

import io.jacobking.simpleticket.object.StatusType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class StatusComboBox extends ComboBox<StatusType> {

    private final ObservableList<StatusType> statusList = FXCollections.observableArrayList();

    public StatusComboBox() {
        build();
        overrideCellFactory();
    }

    private void build() {
        statusList.addAll(StatusType.values());
        setItems(statusList);
    }

    private void overrideCellFactory() {
        setCellFactory(new Callback<>() {
            @Override
            public ListCell<StatusType> call(ListView<StatusType> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(StatusType item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null && !empty) {
                            setText(item.getDescriptor());
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
    }

}
