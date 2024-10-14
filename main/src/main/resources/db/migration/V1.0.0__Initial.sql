CREATE SEQUENCE demo.dummy_id_seq
    START WITH 1
    INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS demo.dummy
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(64) NOT NULL,
    dummy_data  TEXT
);