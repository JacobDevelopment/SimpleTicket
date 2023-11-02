package io.jacobking.simpleticket.database.service.impl;

import io.jacobking.simpleticket.database.service.Service;
import io.jacobking.simpleticket.tables.pojos.Settings;
import io.jacobking.simpleticket.tables.records.SettingsRecord;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.TableField;

import java.util.List;

import static io.jacobking.simpleticket.Tables.SETTINGS;

public class SettingsService implements Service<Settings> {
    @Override
    public boolean insert(DSLContext context, Settings value) {
       throw new UnsupportedOperationException("SettingsService#insert not supported!");
    }

    @Override
    public Settings insertReturning(DSLContext context, Settings value) {
        throw new UnsupportedOperationException("SettingsService#insertReturning not supported!");
    }

    @Override
    public boolean delete(DSLContext context, int id) {
        throw new UnsupportedOperationException("SettingsService#delete not supported!");
    }

    @Override
    public <R extends Record, V> boolean update(DSLContext context, TableField<R, V> field, V value, Condition condition) {
        return context.update(SETTINGS)
                .set(field, value)
                .where(condition)
                .execute() == SUCCESS_CODE;
    }

    @Override
    public boolean update(DSLContext context, Settings value) {
        return context.update(SETTINGS)
                .set(new SettingsRecord(value))
                .execute() == SUCCESS_CODE;
    }

    @Override
    public Settings fetch(DSLContext context, int id) {
        throw new UnsupportedOperationException("Settings#fetch not supported, use Settings#retrieve!");
    }

    public Settings retrieve(DSLContext context) {
        return context.selectFrom(SETTINGS)
                .fetchOneInto(Settings.class);
    }

    @Override
    public Settings fetchByCondition(DSLContext context, Condition condition) {
        throw new UnsupportedOperationException("Settings#fetchByCondition not supported, use Settings#retrieve!!");
    }

    @Override
    public List<Settings> fetchAll(DSLContext context) {
        throw new UnsupportedOperationException("Settings#fetchAll not supported, use Settings#retrieve!!");

    }

    @Override
    public List<Settings> fetchAll(DSLContext context, Condition condition) {
        throw new UnsupportedOperationException("Settings#fetchAll not supported, use Settings#retrieve!!");
    }
}
