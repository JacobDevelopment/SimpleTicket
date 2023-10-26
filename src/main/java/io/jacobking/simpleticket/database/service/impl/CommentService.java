package io.jacobking.simpleticket.database.service.impl;

import io.jacobking.simpleticket.database.service.Service;
import io.jacobking.simpleticket.tables.pojos.TicketComments;
import io.jacobking.simpleticket.tables.records.TicketCommentsRecord;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.TableField;

import java.util.List;

import static io.jacobking.simpleticket.tables.TicketComments.TICKET_COMMENTS;

public class CommentService implements Service<TicketComments> {
    @Override
    public boolean insert(DSLContext context, TicketComments value) {
        return context.insertInto(TICKET_COMMENTS)
                .set(new TicketCommentsRecord(value))
                .onConflictDoNothing()
                .execute() == SUCCESS_CODE;
    }

    @Override
    public TicketComments insertReturning(DSLContext context, TicketComments value) {
        return context.insertInto(TICKET_COMMENTS)
                .set(new TicketCommentsRecord(value))
                .onConflictDoNothing()
                .returning()
                .fetchOneInto(TicketComments.class);
    }

    @Override
    public boolean delete(DSLContext context, int id) {
        return context.deleteFrom(TICKET_COMMENTS)
                .where(TICKET_COMMENTS.ID.eq(id))
                .execute() == SUCCESS_CODE;
    }

    @Override
    public <R extends Record, V> boolean update(DSLContext context, TableField<R, V> field, V value, Condition condition) {
            throw new UnsupportedOperationException("TicketComments#update is not supported!");
    }

    @Override
    public boolean update(DSLContext context, TicketComments value) {
        throw new UnsupportedOperationException("TicketComments#update is not supported!");
    }

    @Override
    public TicketComments fetch(DSLContext context, int id) {
        return context.selectFrom(TICKET_COMMENTS)
                .where(TICKET_COMMENTS.ID.eq(id))
                .fetchOneInto(TicketComments.class);
    }

    @Override
    public TicketComments fetchByCondition(DSLContext context, Condition condition) {
        return context.selectFrom(TICKET_COMMENTS)
                .where(condition)
                .fetchOneInto(TicketComments.class);
    }

    @Override
    public List<TicketComments> fetchAll(DSLContext context) {
        throw new UnsupportedOperationException("TicketComments#fetchAll is not supported. Only retrieval by condition is.");
    }

    @Override
    public List<TicketComments> fetchAll(DSLContext context, Condition condition) {
        return context.selectFrom(TICKET_COMMENTS)
                .where(condition)
                .fetchInto(TicketComments.class);
    }
}
