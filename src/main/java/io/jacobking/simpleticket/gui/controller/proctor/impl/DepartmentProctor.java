package io.jacobking.simpleticket.gui.controller.proctor.impl;

import io.jacobking.simpleticket.database.Database;
import io.jacobking.simpleticket.database.service.ServiceType;
import io.jacobking.simpleticket.gui.controller.proctor.ProctorImpl;
import io.jacobking.simpleticket.gui.model.DepartmentModel;
import io.jacobking.simpleticket.tables.pojos.Department;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DepartmentProctor extends ProctorImpl<Department, DepartmentModel> {
    @Override
    public void fetch() {
        final List<Department> departments = Database.fetchAll(ServiceType.DEPARTMENT);
        if (departments.isEmpty())
            return;

        departments.forEach(department -> {
            if (department.getId() == 0) // SKIP PLACEHOLDER DEPARTMENT.
                return;

            final DepartmentModel model = new DepartmentModel(department);
            modelList.add(model);
        });
    }

    @Override
    public void create(Department department) {
        final Department returningDepartment = insert(department);
        if (returningDepartment != null) {
            final DepartmentModel model = new DepartmentModel(returningDepartment);
            modelList.add(model);
        }
    }

    @Override
    public Department insert(Department department) {
        return Database.insertReturning(ServiceType.DEPARTMENT, department);
    }

    @Override
    public void delete(int id) {
        if (Database.delete(ServiceType.DEPARTMENT, id)) {
            getModelList().removeIf(model -> model.getId() == id);
        }
    }

    public ObservableList<DepartmentModel> getModelList(final Predicate<DepartmentModel> predicate) {
        return getModelList().stream()
                .filter(predicate)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }
}
