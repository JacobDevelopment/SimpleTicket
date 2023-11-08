package io.jacobking.simpleticket.archiver;

import io.jacobking.simpleticket.database.Database;
import io.jacobking.simpleticket.database.service.ServiceType;
import io.jacobking.simpleticket.gui.alert.Alerts;
import io.jacobking.simpleticket.tables.pojos.Ticket;
import io.jacobking.simpleticket.tables.pojos.TicketComments;
import org.jooq.impl.DSL;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static io.jacobking.simpleticket.tables.TicketComments.TICKET_COMMENTS;

public class TicketArchive {

    private final StringBuilder stringBuilder = new StringBuilder();

    private final int id;
    private final String subject;
    private final String status;
    private final String priority;
    private final int employeeId;
    private final String date;
    private final String fileName;
    private String source;

    public TicketArchive(Ticket ticket) {
        this.id = ticket.getId();
        this.subject = ticket.getSubject();
        this.status = ticket.getStatus();
        this.priority = ticket.getPriority();
        this.employeeId = ticket.getEmployeeId();
        this.date = ticket.getCreatedOn();
        this.fileName = String.format("[Ticket-%s] %s", id, date);

        stringBuilder.append(this).append("\n\n------------------------------------").append("\n\n");
        generateComments();
    }

    private void generateComments() {
        final var comments = getComments();
        if (comments.isEmpty()) {
            stringBuilder.append("No tickets were fetched related to this ticket, or an error occurred when archiving.");
            return;
        }

        comments.forEach(comment -> {
            stringBuilder.append(String.format("[%s] [%s]: %s\n",
                    comment.getId(),
                    comment.getDate(),
                    comment.getComment())
            );
        });
    }

    public void generateFile(final String archiveDirectory) {
        this.source = String.format("%s%s%s.txt", archiveDirectory,
                System.getProperty("file.separator"),
                fileName.replaceAll("/", "-")
        );

        final File file = new File(source);
        if (file.exists()) {
            Alerts.showError("There was an error generating the archive.", "It seems this archive already exists.");
            return;
        }

        try {
            if (file.createNewFile()) {
                printContents(file);
            }
        } catch (IOException e) {
            Alerts.showException(e.fillInStackTrace(), "Generating Archive File Failed", "Please read the stacktrace.");
        }
    }

    private void printContents(final File file) {
        try (final PrintWriter writer = new PrintWriter(file, StandardCharsets.UTF_8)) {
            writer.print(stringBuilder);
        } catch (IOException e) {
            Alerts.showException(e.fillInStackTrace(), "Failed to write contents to archive.", "Please read the stacktrace.");
        }
    }

    private List<TicketComments> getComments() {
        return Database.fetchAll(
                ServiceType.TICKET_COMMENTS,
                DSL.condition(TICKET_COMMENTS.TICKET_ID.eq(id))
        );
    }

    public boolean fileExists() {
        return new File(source).exists();
    }

    @Override
    public String toString() {
        return "TicketArchive{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", status='" + status + '\'' +
                ", priority='" + priority + '\'' +
                ", employeeId=" + employeeId +
                ", date='" + date + '\'' +
                '}';
    }
}
