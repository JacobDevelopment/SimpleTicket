package io.jacobking.simpleticket.gui.controller.proctor.impl;

import io.jacobking.simpleticket.database.Database;
import io.jacobking.simpleticket.database.service.ServiceType;
import io.jacobking.simpleticket.gui.controller.proctor.ProctorImpl;
import io.jacobking.simpleticket.gui.model.TicketModel;
import io.jacobking.simpleticket.tables.pojos.Ticket;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.transformation.FilteredList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketProctor extends ProctorImpl<Ticket, TicketModel> {

    private final FilteredList<TicketModel> openTickets;
    private final FilteredList<TicketModel> resolvedTickets;
    private final FilteredList<TicketModel> inProgressTickets;
    private final FilteredList<TicketModel> pausedTickets;

    public TicketProctor() {
        super();
        this.openTickets = new FilteredList<>(getModelList(), ticket -> {
            return ticket.getStatus().equalsIgnoreCase("OPEN");
        });

        this.resolvedTickets = new FilteredList<>(getModelList(), ticket -> {
            return ticket.getStatus().equalsIgnoreCase("RESOLVED");
        });

        this.inProgressTickets = new FilteredList<>(getModelList(), ticket -> {
            return ticket.getStatus().equalsIgnoreCase("IN_PROGRESS");
        });

        this.pausedTickets = new FilteredList<>(getModelList(), ticket -> {
            return ticket.getStatus().equalsIgnoreCase("PAUSED");
        });
    }

    @Override
    public void fetch() {
        final List<Ticket> ticketList = Database.fetchAll(ServiceType.TICKET);
        if (ticketList.isEmpty()) {
            return;
        }

        ticketList.forEach(ticket -> {
            final TicketModel model = new TicketModel(ticket);
            modelList.add(model);
        });
    }

    @Override
    public void create(Ticket ticket) {
        final Ticket insertedTicket = insert(ticket);
        if (insertedTicket != null) {
            final TicketModel model = new TicketModel(insertedTicket);
            modelList.add(model);
        }
    }

    @Override
    public Ticket insert(Ticket ticket) {
        return Database.insertReturning(ServiceType.TICKET, ticket);
    }

    @Override
    public void delete(int id) {
        if (Database.delete(ServiceType.TICKET, id)) {
            getModelList().removeIf(model -> model.getId() == id);
        }
    }

    public FilteredList<TicketModel> getOpenTickets() {
        return openTickets;
    }

    public FilteredList<TicketModel> getResolvedTickets() {
        return resolvedTickets;
    }

    public FilteredList<TicketModel> getInProgressTickets() {
        return inProgressTickets;
    }

    public FilteredList<TicketModel> getPausedTickets() {
        return pausedTickets;
    }
}
