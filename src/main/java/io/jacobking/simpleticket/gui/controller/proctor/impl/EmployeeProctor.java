package io.jacobking.simpleticket.gui.controller.proctor.impl;

import io.jacobking.simpleticket.database.Database;
import io.jacobking.simpleticket.database.service.ServiceType;
import io.jacobking.simpleticket.gui.controller.proctor.ProctorImpl;
import io.jacobking.simpleticket.gui.model.EmployeeModel;
import io.jacobking.simpleticket.tables.pojos.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class EmployeeProctor extends ProctorImpl<Employee, EmployeeModel> {
    @Override
    public void fetch() {
        final List<Employee> employeeList = Database.fetchAll(ServiceType.EMPLOYEE);
        if (employeeList.isEmpty())
            return;

        employeeList.forEach(employee -> {
            if (employee.getId() == 0)
                return;
            final EmployeeModel model = new EmployeeModel(employee);
            modelList.add(model);
        });
    }

    @Override
    public void create(Employee employee) {
        final Employee returningEmployee = insert(employee);
        if (returningEmployee != null) {
            final EmployeeModel model = new EmployeeModel(returningEmployee);
            modelList.add(model);
        }
    }

    @Override
    public Employee insert(Employee employee) {
        return Database.insertReturning(ServiceType.EMPLOYEE, employee);
    }

    @Override
    public void delete(int id) {
        if (Database.delete(ServiceType.EMPLOYEE, id)) {
            getModelList().removeIf(model -> model.getId() == id);
        }
    }

    public ObservableList<EmployeeModel> getModelList(final Predicate<EmployeeModel> predicate) {
        return getModelList().stream()
                .filter(predicate)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }


}
