DROP TABLE IF EXISTS deposits;
DROP TABLE IF EXISTS banks;
DROP TABLE IF EXISTS clients;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START 100000;

CREATE TABLE clients
(
  id        INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  name      VARCHAR NOT NULL,
  shortName VARCHAR NOT NULL,
  address   VARCHAR NOT NULL,
  form      VARCHAR NOT NULL
);

CREATE TABLE banks
(
  id   INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  name VARCHAR NOT NULL,
  bic  VARCHAR NOT NULL
);
CREATE UNIQUE INDEX users_unique_bic_idx
  ON banks (bic);

CREATE TABLE deposits
(
  id        INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  client_id INTEGER          NOT NULL,
  bank_id   INTEGER          NOT NULL,
  date      DATE             NOT NULL,
  interest  DOUBLE PRECISION NOT NULL,
  period    INT              NOT NULL,
  FOREIGN KEY (client_id) REFERENCES clients (id) ON DELETE CASCADE,
  FOREIGN KEY (bank_id) REFERENCES banks (id) ON DELETE CASCADE
);

