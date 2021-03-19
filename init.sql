DROP SCHEMA IF EXISTS project CASCADE;
CREATE SCHEMA project;

CREATE TABLE project.addresses
(
    address_id      SERIAL PRIMARY KEY,
    street          VARCHAR(100) NOT NULL,
    building_number VARCHAR(100) NOT NULL,
    unit_number     VARCHAR(100) NULL,
    postcode        VARCHAR(100) NOT NULL,
    commune         VARCHAR(100) NOT NULL,
    country         VARCHAR(100) NOT NULL
);

CREATE TABLE project.users
(
    user_id            SERIAL PRIMARY KEY,
    username           VARCHAR(100)                                      NOT NULL,
    password           CHARACTER(60)                                     NOT NULL,
    last_name          VARCHAR(100)                                      NOT NULL,
    first_name         VARCHAR(100)                                      NOT NULL,
    address            INTEGER REFERENCES project.addresses (address_id) NOT NULL,
    email              VARCHAR(100)                                      NOT NULL,
    registration_date  TIMESTAMP                                         NOT NULL,
    valid_registration BOOLEAN                                           NOT NULL,
    user_type          INTEGER
);

INSERT INTO project.addresses
VALUES (DEFAULT, 'street', 'building_number', 'unit_number', 'postcode', 'commune', 'country');

INSERT INTO project.users
VALUES (DEFAULT, 'test', '$2a$10$qSBZrtuxTN3tM///i5rM8Opd3ioOk2vG.olTPH/UZqP8rjCtg19Zm',
        'last_name', 'first_name', 1, 'email', NOW(), 'true', '0');
/*
SELECT * FROM project.addresses;

CREATE TABLE project.visit_requests (
	visit_request_id SERIAL PRIMARY KEY,
	request_date TIMESTAMP NOT NULL,
	time_slot TEXT NOT NULL,
	adress INTEGER REFERENCES project.addresses (address_id) NULL,
	status INTEGER NOT NULL,
	chosen_date_time TIMESTAMP NULL,
	cancellation_reason TEXT NULL,
	customer INTEGER REFERENCES project.users (user_id) NOT NULL
);

CREATE TABLE project.furniture_types (
	type_id SERIAL PRIMARY KEY,
	name VARCHAR(100) NOT NULL
);

CREATE TABLE project.furniture (
	furniture_id SERIAL PRIMARY KEY,
	description TEXT NOT NULL,
	type INTEGER REFERENCES project.furniture_types (type_id) NOT NULL,
	visit_request INTEGER REFERENCES project.visit_requests (visit_request_id) NOT NULL,
	purchase_price DECIMAL NULL,
	withdrawal_date_from_costumer TIMESTAMP NULL,
	selling_price DECIMAL NULL,
	special_sale_price DECIMAL NULL,
	deposit_date TIMESTAMP NULL,
	selling_date TIMESTAMP NULL,
	delivery_date TIMESTAMP NULL,
	withdrawal_date_to_customer TIMESTAMP NULL,
	buyer INTEGER REFERENCES project.users (user_id) NOT NULL,
	condition INTEGER NULL,
	unregistered_buyer_email VARCHAR(100) NULL,
	--favourite_picture INTEGER REFERENCES project.pictures (picture_id) NULL
);

CREATE TABLE project.pictures (
	picture_id SERIAL PRIMARY KEY,
	name VARCHAR(100) NOT NULL,
	visible_for_everyone BOOLEAN NOT NULL,
	furniture INTEGER REFERENCES project.furniture (furniture_id) NOT NULL,
	scrolling_picture BOOLEAN NOT NULL
);

--ALTER TABLE furniture ADD favorite_picture INTEGER FOREIGN KEY REFERENCES project.pictures(picture_id) NULL;

CREATE TABLE project.options (
	option_id SERIAL PRIMARY KEY,
	buyer INTEGER REFERENCES project.users (user_id) NOT NULL,
	furniture INTEGER REFERENCES project.furniture (furniture_id) NOT NULL,
	duration INTEGER NOT NULL,
	date TIMESTAMP NOT NULL,
	status INTEGER NOT NULL
);
*/