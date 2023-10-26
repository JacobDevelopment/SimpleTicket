package io.jacobking.simpleticket.gui.view.table;

import io.jacobking.simpleticket.database.Database;
import io.jacobking.simpleticket.database.service.ServiceType;
import io.jacobking.simpleticket.gui.model.CompanyModel;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;

import java.util.function.BiConsumer;


public class CompanyTable extends TableView<CompanyModel> {

    private final TableColumn<CompanyModel, String> idColumn = new TableColumn<>("ID");
    private final TableColumn<CompanyModel, String> titleColumn = new TableColumn<>("Title");
    private final TableColumn<CompanyModel, String> abbreviationColumn = new TableColumn<>("Abbreviation");

    public CompanyTable() {
        setPlaceholder(getLabel());
        addColumns();
        setCellValueFactory();
    }

    @SuppressWarnings("unchecked")
    private void addColumns() {
        getColumns().addAll(idColumn, titleColumn, abbreviationColumn);
    }

    private void setCellValueFactory() {
        idColumn.setCellValueFactory(data -> data.getValue().idProperty().asString());
        titleColumn.setCellValueFactory(data -> data.getValue().titleProperty());
        abbreviationColumn.setCellValueFactory(data -> data.getValue().abbreviationProperty());
    }

    public void setEditCommits(final boolean state) {
        setEditHandler(titleColumn, (newValue, model) -> {
           model.setTitle(newValue);
        });

        setEditHandler(abbreviationColumn, (newValue, model) -> {
            model.setAbbreviation(newValue);
        });

        setEditable(state);
    }

    private void setEditHandler(final TableColumn<CompanyModel, String> column, BiConsumer<String, CompanyModel> modelConsumer) {
        column.setCellFactory(TextFieldTableCell.forTableColumn());
        column.setOnEditCommit(event -> {
            final var companyItems = event.getTableView().getItems();
            final int rowPosition = event.getTablePosition().getRow();

            final CompanyModel model = companyItems.get(rowPosition);
            if (model == null)
                return;

            modelConsumer.accept(event.getNewValue(), model);
            Database.update(ServiceType.COMPANY, model.getAsPojo());
        });
    }

    private Label getLabel() {
        final Label label = new Label("There are no companies.");
        label.getStyleClass().add("empty-label");
        return label;
    }

}
