CREATE TABLE IF NOT EXISTS TEST
(
    ID INTEGER PRIMARY KEY AUTOINCREMENT
);

INSERT OR IGNORE INTO TEST(ID)
VALUES (0);

CREATE TABLE IF NOT EXISTS COMPANY
(
    ID           INTEGER PRIMARY KEY AUTOINCREMENT,
    TITLE        TEXT NOT NULL,
    ABBREVIATION TEXT NOT NULL
);

INSERT OR IGNORE INTO COMPANY(ID, TITLE, ABBREVIATION)
VALUES (0, 'Unavailable', 'Unavailable');

CREATE TABLE IF NOT EXISTS DEPARTMENT
(
    ID            INTEGER PRIMARY KEY AUTOINCREMENT,
    TITLE         TEXT    NOT NULL,
    ABBREVIATION  TEXT    NOT NULL,
    SUPERVISOR_ID INTEGER NOT NULL REFERENCES EMPLOYEE (ID),
    COMPANY_ID    INTEGER NOT NULL REFERENCES COMPANY (ID)
);

INSERT OR IGNORE INTO DEPARTMENT(ID, TITLE, ABBREVIATION, SUPERVISOR_ID, COMPANY_ID)
VALUES (0, 'NONE', 'N/A', 0, 0);;

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