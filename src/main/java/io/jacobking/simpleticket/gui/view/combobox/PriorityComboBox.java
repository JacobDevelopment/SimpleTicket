package io.jacobking.simpleticket.gui.view.combobox;

import io.jacobking.simpleticket.object.PriorityType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class PriorityComboBox extends ComboBox<PriorityType> {

    private final ObservableList<PriorityType> priorityList = FXCollections.observableArrayList();

    public PriorityComboBox() {
        build();
        overrideCellFactory();
    }

    private void build() {
        priorityList.addAll(PriorityType.values());
        setItems(priorityList);
    }

    private void overrideCellFactory() {
        setCellFactory(new Callback<>() {
            @Override
            public ListCell<PriorityType> call(ListView<PriorityType> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(PriorityType item, boolean empty) {
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
