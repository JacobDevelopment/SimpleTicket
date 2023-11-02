CREATE TABLE IF NOT EXISTS TEST
  (
      ID INTEGER PRIMARY KEY AUTOINCREMENT
  );

CREATE TABLE IF NOT EXISTS HOME
(
    PRIORITY_ID    INTEGER NOT NULL REFERENCES TICKET (ID),
    LAST_VIEWED_ID INTEGER NOT NULL REFERENCES TICKET (ID),
    LAST_CLOSED_ID INTEGER NOT NULL REFERENCES TICKET (ID),
    PINNED_ID      INTEGER NOT NULL REFERENCES TICKET (ID)
);

INSERT INTO HOME(PRIORITY_ID, LAST_VIEWED_ID, LAST_CLOSED_ID, PINNED_ID)
VALUES (0, 0, 0, 0);

CREATE TABLE IF NOT EXISTS SETTINGS
(
    ID INTEGER PRIMARY KEY NOT NULL,
    ARCHIVE_TICKETS        BOOLEAN NOT NULL DEFAULT FALSE,
    AUTO_ARCHIVE_TICKETS   BOOLEAN NOT NULL DEFAULT FALSE,
    AUTO_DELETE_TICKETS    BOOLEAN NOT NULL DEFAULT FALSE,
    AUTO_DELETE_DATE       TEXT    NOT NULL,
    AUTO_ARCHIVE_DATE      TEXT    NOT NULL,
    ARCHIVE_DIRECTORY      TEXT    NOT NULL,
    DONT_SHOW_CONFIRMATION BOOLEAN NOT NULL DEFAULT FALSE
);

INSERT OR IGNORE INTO SETTINGS(ID, ARCHIVE_TICKETS, AUTO_ARCHIVE_TICKETS, AUTO_DELETE_TICKETS, AUTO_DELETE_DATE,
                               AUTO_ARCHIVE_DATE, ARCHIVE_DIRECTORY, DONT_SHOW_CONFIRMATION)
VALUES (1, FALSE, FALSE, FALSE, '', '', '', FALSE);

INSERT OR IGNORE INTO TEST(ID)
VALUES (0);

CREATE TABLE IF NOT EXISTS COMPANY
(
    ID           INTEGER PRIMARY KEY AUTOINCREMENT,
    TITLE        TEXT NOT NULL,
    ABBREVIATION TEXT NOT NULL,
    DESCRIPTION  TEXT NOT NULL,
    CREATED_ON   TEXT NOT NULL
);

INSERT OR IGNORE INTO COMPANY(ID, TITLE, ABBREVIATION, DESCRIPTION, CREATED_ON)
VALUES (0, 'Unavailable', 'Unavailable', 'Unavailable', '10/31/2023');

CREATE TABLE IF NOT EXISTS DEPARTMENT
(
    ID            INTEGER PRIMARY KEY AUTOINCREMENT,
    TITLE         TEXT    NOT NULL,
    ABBREVIATION  TEXT    NOT NULL,
    DESCRIPTION   TEXT    NOT NULL,
    SUPERVISOR_ID INTEGER NOT NULL REFERENCES EMPLOYEE (ID),
    COMPANY_ID    INTEGER NOT NULL REFERENCES COMPANY (ID)
);

INSERT OR IGNORE INTO DEPARTMENT(ID, TITLE, ABBREVIATION, DESCRIPTION, SUPERVISOR_ID, COMPANY_ID)
VALUES (0, 'NONE', 'N/A', 'N/A', 0, 0);;

CREATE TABLE IF NOT EXISTS EMPLOYEE
(
    ID            INTEGER PRIMARY KEY AUTOINCREMENT,
    FIRST_NAME    TEXT    NOT NULL,
    LAST_NAME     TEXT    NOT NULL,
    DEPARTMENT_ID INTEGER NOT NULL REFERENCES DEPARTMENT (ID),
    COMPANY_ID    INTEGER NOT NULL REFERENCES COMPANY (ID),
    TITLE         TEXT    NOT NULL,
    EMAIL         TEXT    NOT NULL,
    CREATED_ON    TEXT    NOT NULL
);

INSERT OR IGNORE INTO EMPLOYEE(ID, FIRST_NAME, LAST_NAME, DEPARTMENT_ID, COMPANY_ID, TITLE, EMAIL, CREATED_ON)
VALUES (0, 'Unavailable', 'Unavailable', 0, 0, 'Unavailable', 'Unavailable', 'Unavailable');

CREATE TABLE IF NOT EXISTS TICKET
(
    ID          INTEGER PRIMARY KEY AUTOINCREMENT,
    SUBJECT     TEXT    NOT NULL,
    CREATED_ON  TEXT    NOT NULL,
    STATUS      TEXT    NOT NULL,
    PRIORITY    TEXT    NOT NULL,
    EMPLOYEE_ID INTEGER NOT NULL REFERENCES EMPLOYEE (ID)
);

CREATE TABLE IF NOT EXISTS TICKET_COMMENTS
(
    ID        INTEGER PRIMARY KEY AUTOINCREMENT,
    TICKET_ID INTEGER NOT NULL REFERENCES TICKET (ID),
    COMMENT   TEXT    NOT NULL,
    DATE      TEXT    NOT NULL
);