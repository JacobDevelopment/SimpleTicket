package io.jacobking.simpleticket.database.service.impl;

import io.jacobking.simpleticket.database.service.Service;
import io.jacobking.simpleticket.tables.pojos.Company;
import io.jacobking.simpleticket.tables.records.CompanyRecord;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.TableField;

import java.util.List;

import static io.jacobking.simpleticket.Tables.COMPANY;

public class CompanyService implements Service<Company> {
    @Override
    public boolean insert(DSLContext context, Company value) {
        return context.insertInto(COMPANY)
                .set(new CompanyRecord(value))
                .onConflictDoNothing()
                .execute() == SUCCESS_CODE;
    }

    @Override
    public Company insertReturning(DSLContext context, Company value) {
        return context.insertInto(COMPANY)
                .set(new CompanyRecord(value))
                .onConflictDoNothing()
                .returning()
                .fetchOneInto(Company.class);
    }

    @Override
    public boolean delete(DSLContext context, int id) {
        return context.deleteFrom(COMPANY)
                .where(COMPANY.ID.eq(id))
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
    public boolean update(DSLContext context, Company value) {
        return context.update(COMPANY)
                .set(new CompanyRecord(value))
                .where(COMPANY.ID.eq(value.getId()))
                .execute() == SUCCESS_CODE;
    }

    @Override
    public Company fetch(DSLContext context, int id) {
        return context.selectFrom(COMPANY)
                .where(COMPANY.ID.eq(id))
                .fetchOneInto(Company.class);
    }

    @Override
    public Company fetchByCondition(DSLContext context, Condition condition) {
        throw new UnsupportedOperationException("CompanyService does not support fetchByCondition!");
    }

    @Override
    public List<Company> fetchAll(DSLContext context) {
        return context.selectFrom(COMPANY)
                .fetchInto(Company.class);
    }

    @Override
    public List<Company> fetchAll(DSLContext context, Condition condition) {
        throw new UnsupportedOperationException("CompanyService does not support fetchAll(Condition)!");
    }
}
