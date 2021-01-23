insert into BANK (bank_name) values ('Sber');
insert into BANK (BANK_NAME) values ('Otkritie');
insert into BANK (BANK_NAME) values ('Alfa');

insert into client (LAST_NAME, FIRST_NAME, MIDDLE_NAME, PHONE_NUMBER, EMAIL, PASSPORT, fk_client_bank)
values ('Ivanov', 'Ivan', 'Ivanovich', 89171231212, 'ivanich@gmail.com', 1234123456, 0);
insert into client (LAST_NAME, FIRST_NAME, MIDDLE_NAME, PHONE_NUMBER, EMAIL, PASSPORT, fk_client_bank)
values ('Ivanov', 'Ivan', 'Stepanich', 89171232121, 'stepatich@gmail.com', 1234126246, 0);
insert into client (LAST_NAME, FIRST_NAME, MIDDLE_NAME, PHONE_NUMBER, EMAIL, PASSPORT, fk_client_bank)
values ('Tolkien', 'Kolan', 'Vasilich', 89171253121, 'truetolkien@mail.ru', 1234127264, 0);
insert into CLIENT (LAST_NAME, FIRST_NAME, MIDDLE_NAME, PHONE_NUMBER, EMAIL, PASSPORT, FK_CLIENT_BANK)
values ('Goncharov', 'Victor', 'Arturovih', 89425671536, 'gonchar@yandex.ru', 3687123543, 0);
insert into CLIENT (LAST_NAME, FIRST_NAME, MIDDLE_NAME, PHONE_NUMBER, EMAIL, PASSPORT, FK_CLIENT_BANK)
values ('Smith', 'Adam', 'Tod', 89425836536, 'smithat@gmail.com', 3287126743, 1);
insert into CLIENT (LAST_NAME, FIRST_NAME, MIDDLE_NAME, PHONE_NUMBER, EMAIL, PASSPORT, FK_CLIENT_BANK)
values ('Smith', 'Tom', 'Grand', 89425936536, 'smithtg@gmail.com', 4187126743, 1);
insert into CLIENT (LAST_NAME, FIRST_NAME, MIDDLE_NAME, PHONE_NUMBER, EMAIL, PASSPORT, FK_CLIENT_BANK)
values ('Tolstoy', 'Lev', 'Nicolaevich', 89425838765, 'realtolstoy@mail.ru', 3193126743, 1);
insert into CLIENT (LAST_NAME, FIRST_NAME, MIDDLE_NAME, PHONE_NUMBER, EMAIL, PASSPORT, FK_CLIENT_BANK)
values ('Guzenko', 'Artemiy', 'Andreevich', 89175461679, 'guzenko@mail.ru', 3236786743, 2);

insert into CREDIT (CREDIT_LIMIT, INTEREST_RATE, fk_credit_bank) values (100000.0, 30.0, 0);
insert into CREDIT (CREDIT_LIMIT, INTEREST_RATE, fk_credit_bank) values (500000.0, 25.0, 0);
insert into CREDIT (CREDIT_LIMIT, INTEREST_RATE, fk_credit_bank) values (1000000.0, 20.0, 0);
insert into CREDIT (CREDIT_LIMIT, INTEREST_RATE, FK_CREDIT_BANK) values (2500000.0, 5.0, 1);
insert into CREDIT (CREDIT_LIMIT, INTEREST_RATE, FK_CREDIT_BANK) values (1500000.0, 10.0, 1);
insert into CREDIT (CREDIT_LIMIT, INTEREST_RATE, FK_CREDIT_BANK) values (500000.0, 12.5, 1);
insert into CREDIT (CREDIT_LIMIT, INTEREST_RATE, FK_CREDIT_BANK) values (100000.0, 15.2, 1);
insert into CREDIT (CREDIT_LIMIT, INTEREST_RATE, FK_CREDIT_BANK) values (1250000.0, 8.7, 2);