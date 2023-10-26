package io.jacobking.simpleticket.gui.view.combobox;

import io.jacobking.simpleticket.gui.model.DepartmentModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class DepartmentComboBox extends ComboBox<DepartmentModel> {

    private final ObservableList<DepartmentModel> departmentList = FXCollections.observableArrayList();

    public DepartmentComboBox() {
        build();
        overrideCellFactory();
        setDepartmentBoxConverter();
    }

    private void build() {
        setItems(departmentList);
    }

    private void overrideCellFactory() {
        setCellFactory(new Callback<>() {
            @Override
            public ListCell<DepartmentModel> call(ListView<DepartmentModel> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(DepartmentModel item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null && !empty) {
                            setText(item.getTitle());
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
    }

    private void setDepartmentBoxConverter() {
        setConverter(new StringConverter<>() {
            @Override
            public String toString(DepartmentModel object) {
                if (object == null)
                    return "";
                return object.getTitle();
            }

            @Override
            public DepartmentModel fromString(String string) {
                return null;
            }
        });
    }


}
