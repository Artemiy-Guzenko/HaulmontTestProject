DROP SCHEMA PUBLIC CASCADE;

create table bank
(
    id        bigint identity primary key,
    bank_name varchar(25) not null
);


CREATE TABLE client
(
    id             bigint identity primary key,
    last_name      VARCHAR(25) NOT NULL,
    first_name     VARCHAR(25) NOT NULL,
    middle_name    VARCHAR(25),
    phone_number   VARCHAR(11) unique,
    email          VARCHAR(50),
    passport       VARCHAR(10) unique,
    fk_client_bank bigint foreign key references bank (id)
);

CREATE TABLE credit
(
    id             bigint identity primary key,
    credit_limit   DOUBLE,
    interest_rate  int,
    fk_credit_bank bigint foreign key references bank (id)
);

create table credit_offer
(
    id         bigint identity primary key,
    credit_sum double,
    fk_credit bigint foreign key  references credit (id),
    fk_client  bigint foreign key references client (id)
);

create table payment
(
    id bigint identity primary key,
    payment_amount double,
    payment_date date,
    payment_body double,
    interest_repayment double,
    fk_credit_offer bigint foreign key references credit_offer (id)
);