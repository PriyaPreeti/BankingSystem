CREATE TABLE ACCOUNT
(
    ACCOUNT_NUMBER BIGINT GENERATED BY DEFAULT AS IDENTITY,
    BALANCE NUMERIC NOT NULL,
    ACCOUNT_STATUS VARCHAR NOT NULL,
    CUSTOMER_ID BIGINT REFERENCES CUSTOMER(ID),
    CREATED_AT TIMESTAMP NOT NULL,
    PRIMARY KEY (ACCOUNT_NUMBER)
);