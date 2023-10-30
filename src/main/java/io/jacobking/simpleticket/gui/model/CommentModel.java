package io.jacobking.simpleticket.gui.model;

import io.jacobking.simpleticket.tables.pojos.TicketComments;
import io.jacobking.simpleticket.utility.DateUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CommentModel {

    private final StringProperty dateProperty;
    private final StringProperty commentProperty;

    public CommentModel(final String date, final String comment) {
        this.dateProperty = new SimpleStringProperty(date);
        this.commentProperty = new SimpleStringProperty(comment);
    }

    public CommentModel(final String comment) {
        this(DateUtil.now(), comment);
    }

    public CommentModel(final TicketComments pojo) {
        this(pojo.getDate(), pojo.getComment());
    }

    public String getDate() {
        return dateProperty.getValueSafe();
    }

    public String getComment() {
        return commentProperty.getValueSafe();
    }

    public StringProperty dateProperty() {
        return dateProperty;
    }

    public StringProperty commentProperty() {
        return commentProperty;
    }
}
