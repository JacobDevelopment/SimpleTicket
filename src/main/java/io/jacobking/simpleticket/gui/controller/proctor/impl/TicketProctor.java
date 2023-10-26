package io.jacobking.simpleticket.gui.controller.proctor.impl;

import io.jacobking.simpleticket.database.Database;
import io.jacobking.simpleticket.database.service.ServiceType;
import io.jacobking.simpleticket.gui.controller.proctor.ProctorImpl;
import io.jacobking.simpleticket.gui.model.TicketModel;
import io.jacobking.simpleticket.tables.pojos.Ticket;

import java.util.List;

public class TicketProctor extends ProctorImpl<Ticket, TicketModel> {
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


}
