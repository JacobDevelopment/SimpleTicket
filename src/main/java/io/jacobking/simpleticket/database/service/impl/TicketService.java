package io.jacobking.simpleticket.database.service.impl;

import io.jacobking.simpleticket.database.service.Service;
import io.jacobking.simpleticket.tables.pojos.Ticket;
import io.jacobking.simpleticket.tables.records.TicketRecord;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.TableField;

import java.util.List;

import static io.jacobking.simpleticket.tables.Ticket.TICKET;


public class TicketService implements Service<Ticket> {
    @Override
    public boolean insert(DSLContext context, Ticket value) {
        return context.insertInto(TICKET)
                .set(new TicketRecord(value))
                .onConflictDoNothing()
                .execute() == SUCCESS_CODE;
    }

    @Override
    public Ticket insertReturning(DSLContext context, Ticket value) {
        return context.insertInto(TICKET)
                .set(new TicketRecord(value))
                .onConflictDoNothing()
                .returning()
                .fetchOneInto(Ticket.class);
    }

    @Override
    public boolean delete(DSLContext context, int id) {
        return context.deleteFrom(TICKET)
                .where(TICKET.ID.eq(id))
                .execute() == SUCCESS_CODE;
    }

    @Override
    public <R extends Record, V> boolean update(DSLContext context, TableField<R, V> field, V value, Condition condition) {
        return context.update(TICKET)
                .set(field, value)
                .where(condition)
                .execute() == SUCCESS_CODE;
    }

    @Override
    public boolean update(DSLContext context, Ticket value) {
        return context.update(TICKET)
                .set(new TicketRecord(value))
                .where(TICKET.ID.eq(value.getId()))
                .execute() == SUCCESS_CODE;
    }

    @Override
    public Ticket fetch(DSLContext context, int id) {
        return context.selectFrom(TICKET)
                .where(TICKET.ID.eq(id))
                .fetchOneInto(Ticket.class);
    }

    @Override
    public Ticket fetchByCondition(DSLContext context, Condition condition) {
        return context.selectFrom(TICKET)
                .where(condition)
                .fetchOneInto(Ticket.class);
    }

    @Override
    public List<Ticket> fetchAll(DSLContext context) {
        return context.selectFrom(TICKET)
                .fetchInto(Ticket.class);
    }

    @Override
    public List<Ticket> fetchAll(DSLContext context, Condition condition) {
        return context.selectFrom(TICKET)
                .where(condition)
                .fetchInto(Ticket.class);
    }

}
