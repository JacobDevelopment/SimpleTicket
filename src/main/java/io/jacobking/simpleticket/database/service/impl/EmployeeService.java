package io.jacobking.simpleticket.database.service.impl;

import io.jacobking.simpleticket.database.service.Service;
import io.jacobking.simpleticket.tables.pojos.Employee;
import io.jacobking.simpleticket.tables.records.EmployeeRecord;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.TableField;

import java.util.List;

import static io.jacobking.simpleticket.tables.Employee.EMPLOYEE;

public class EmployeeService implements Service<Employee> {
    @Override
    public boolean insert(DSLContext context, Employee value) {
        return context.insertInto(EMPLOYEE)
                .set(new EmployeeRecord(value))
                .onConflictDoNothing()
                .execute() == SUCCESS_CODE;
    }

    @Override
    public Employee insertReturning(DSLContext context, Employee value) {
        return context.insertInto(EMPLOYEE)
                .set(new EmployeeRecord(value))
                .onConflictDoNothing()
                .returning()
                .fetchOneInto(Employee.class);
    }

    @Override
    public boolean delete(DSLContext context, int id) {
        return context.deleteFrom(EMPLOYEE)
                .where(EMPLOYEE.ID.eq(id))
                .execute() == SUCCESS_CODE;
    }

    @Override
    public <R extends Record, V> boolean update(DSLContext context, TableField<R, V> field, V value, Condition condition) {
        return context.update(EMPLOYEE)
                .set(field, value)
                .where(condition)
                .execute() == SUCCESS_CODE;
    }

    @Override
    public boolean update(DSLContext context, Employee value) {
        return context.update(EMPLOYEE)
                .set(new EmployeeRecord(value))
                .where(EMPLOYEE.ID.eq(value.getId()))
                .execute() == SUCCESS_CODE;
    }

    @Override
    public Employee fetch(DSLContext context, int id) {
        return context.selectFrom(EMPLOYEE)
                .where(EMPLOYEE.ID.eq(id))
                .fetchOneInto(Employee.class);
    }

    @Override
    public Employee fetchByCondition(DSLContext context, Condition condition) {
        throw new UnsupportedOperationException("Employee fetchByCondition not implemented!");
    }

    @Override
    public List<Employee> fetchAll(DSLContext context) {
        return context.selectFrom(EMPLOYEE)
                .fetchInto(Employee.class);
    }

    @Override
    public List<Employee> fetchAll(DSLContext context, Condition condition) {
        throw new UnsupportedOperationException("Employee fetchAll(Condition) not implemented!");

    }
}
