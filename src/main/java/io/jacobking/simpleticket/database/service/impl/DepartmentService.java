package io.jacobking.simpleticket.database.service.impl;

import io.jacobking.simpleticket.database.service.Service;
import io.jacobking.simpleticket.tables.pojos.Department;
import io.jacobking.simpleticket.tables.records.DepartmentRecord;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.TableField;

import java.util.List;

import static io.jacobking.simpleticket.Tables.COMPANY;
import static io.jacobking.simpleticket.tables.Department.DEPARTMENT;

public class DepartmentService implements Service<Department> {
    @Override
    public boolean insert(DSLContext context, Department value) {
        return context.insertInto(DEPARTMENT)
                .set(new DepartmentRecord(value))
                .onConflictDoNothing()
                .execute() == SUCCESS_CODE;
    }

    @Override
    public Department insertReturning(DSLContext context, Department value) {
        return context.insertInto(DEPARTMENT)
                .set(new DepartmentRecord(value))
                .onConflictDoNothing()
                .returning()
                .fetchOneInto(Department.class);
    }

    @Override
    public boolean delete(DSLContext context, int id) {
        return context.deleteFrom(DEPARTMENT)
                .where(DEPARTMENT.ID.eq(id))
                .execute() == SUCCESS_CODE;
    }

    @Override
    public <R extends Record, V> boolean update(DSLContext context, TableField<R, V> field, V value, Condition condition) {
        return context.update(COMPANY)
                .set(field, value)
                .where(condition)
                .execute() == SUCCESS_CODE;
    }

    @Override
    public boolean update(DSLContext context, Department value) {
        return context.update(DEPARTMENT)
                .set(new DepartmentRecord(value))
                .where(DEPARTMENT.ID.eq(value.getId()))
                .execute() == SUCCESS_CODE;
    }

    @Override
    public Department fetch(DSLContext context, int id) {
        return context.selectFrom(DEPARTMENT)
                .where(DEPARTMENT.ID.eq(id))
                .fetchOneInto(Department.class);
    }

    @Override
    public Department fetchByCondition(DSLContext context, Condition condition) {
        throw new UnsupportedOperationException("Department fetchByCondition not implemented!");
    }

    @Override
    public List<Department> fetchAll(DSLContext context) {
        return context.selectFrom(DEPARTMENT)
                .fetchInto(Department.class);
    }

    @Override
    public List<Department> fetchAll(DSLContext context, Condition condition) {
        throw new UnsupportedOperationException("Department fetchAll(Condition) not implemented!");

    }
}
