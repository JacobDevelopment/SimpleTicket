package io.jacobking.simpleticket.archiver;

import io.jacobking.simpleticket.database.Database;
import io.jacobking.simpleticket.database.service.ServiceType;
import io.jacobking.simpleticket.gui.alert.Alerts;
import io.jacobking.simpleticket.gui.controller.proctor.Proctor;
import io.jacobking.simpleticket.gui.model.SettingsModel;
import io.jacobking.simpleticket.tables.pojos.Ticket;
import io.jacobking.simpleticket.utility.DateUtil;

import java.time.LocalDate;

public class Archiver {

    private Archiver() {

    }

    public static void archive(final Ticket... tickets) {
        if (tickets.length == 0) {
            Alerts.showInfo("Archive Canceled", "There are no tickets to archive.");
            return;
        }

        final SettingsModel settings = Proctor.getInstance().settings();
        if (!canArchive(settings))
            return;

        final String archiveDate = settings.getAutoArchiveDate();
        final String archivePath = settings.getArchiveDirectory();
        for (final Ticket ticket : tickets) {
            final String creationDate = ticket.getCreatedOn();
            if (isBefore(creationDate, archiveDate)) {
                archive(ticket, archivePath);
            }
        }

    }

    private static void archive(final Ticket ticket, final String directory) {
        final TicketArchive archive = new TicketArchive(ticket);
        archive.generateFile(directory);

        if (archive.fileExists()) {
            final int ticketId = ticket.getId();
            Proctor.getInstance().ticket().delete(ticketId);
            Database.delete(ServiceType.TICKET_COMMENTS, ticketId);
        }
    }

    private static boolean canArchive(final SettingsModel settings) {
        if (!settings.isArchiveTickets())
            return false;

        if (!settings.isAutoArchiveTickets())
            return false;

        final String archivePath = settings.getArchiveDirectory();
        return archivePath != null && !archivePath.isEmpty();
    }

    private static boolean isBefore(final String targetDate, final String archiveDate) {
        final LocalDate target = DateUtil.parseLocalDate(targetDate);
        final LocalDate archive = DateUtil.parseLocalDate(archiveDate);
        return target.isBefore(archive);
    }

}
