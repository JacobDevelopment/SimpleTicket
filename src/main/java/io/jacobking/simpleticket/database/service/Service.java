package io.jacobking.simpleticket.database.service;

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.TableField;

import java.util.List;

public interface Service<T> {

    int SUCCESS_CODE = 1;

    boolean insert(final DSLContext context, final T value);

    T insertReturning(final DSLContext context, final T value);

    boolean delete(final DSLContext context, final int id);

    <R extends Record, V> boolean update(final DSLContext context, final TableField<R, V> field, final V value, final Condition condition);

    boolean update(final DSLContext context, final T value);

    T fetch(final DSLContext context, final int id);

    T fetchByCondition(final DSLContext context, final Condition condition);

    List<T> fetchAll(final DSLContext context);

    List<T> fetchAll(final DSLContext context, final Condition condition);

}
