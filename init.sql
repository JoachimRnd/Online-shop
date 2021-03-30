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

SELECT * FROM project.addresses;

--DROP TABLE project.visit_requests;
--DROP TABLE project.furniture;
--DROP TABLE project.options;


CREATE TABLE project.visit_requests (
	visit_request_id SERIAL PRIMARY KEY,
	request_date TIMESTAMP NOT NULL,
	time_slot TEXT NOT NULL,
	address INTEGER REFERENCES project.addresses (address_id) NULL,
	status INTEGER NOT NULL,
	chosen_date_time TIMESTAMP NULL,
	cancellation_reason TEXT NULL,
	customer INTEGER REFERENCES project.users (user_id) NOT NULL
);

--INSERT INTO project.visit_requests VALUES (DEFAULT,'2020-03-30 14:07:22','time_slot ex',NULL,0,NULL, NULL,1);

CREATE TABLE project.furniture_types (
	type_id SERIAL PRIMARY KEY,
	name VARCHAR(100) NOT NULL
);

/*INSERT INTO project.furniture_types VALUES (DEFAULT,'Armoire');
INSERT INTO project.furniture_types VALUES (DEFAULT,'Bahut');
INSERT INTO project.furniture_types VALUES (DEFAULT,'Bibliothèque');
INSERT INTO project.furniture_types VALUES (DEFAULT,'Bonnetière');
INSERT INTO project.furniture_types VALUES (DEFAULT,'Buffet');
INSERT INTO project.furniture_types VALUES (DEFAULT,'Bureau');
INSERT INTO project.furniture_types VALUES (DEFAULT,'Chaise');
INSERT INTO project.furniture_types VALUES (DEFAULT,'Chiffonier');
INSERT INTO project.furniture_types VALUES (DEFAULT,'Coffre');
INSERT INTO project.furniture_types VALUES (DEFAULT,'Coiffeuse');
INSERT INTO project.furniture_types VALUES (DEFAULT,'Commode');
INSERT INTO project.furniture_types VALUES (DEFAULT,'Confident/Indiscret');
INSERT INTO project.furniture_types VALUES (DEFAULT,'Console');
INSERT INTO project.furniture_types VALUES (DEFAULT,'Dresse');
INSERT INTO project.furniture_types VALUES (DEFAULT,'Fauteuil');
INSERT INTO project.furniture_types VALUES (DEFAULT,'Guéridon');
INSERT INTO project.furniture_types VALUES (DEFAULT,'Lingère');
INSERT INTO project.furniture_types VALUES (DEFAULT,'Lit');
INSERT INTO project.furniture_types VALUES (DEFAULT,'Penderie');
INSERT INTO project.furniture_types VALUES (DEFAULT,'Secrétaire');
INSERT INTO project.furniture_types VALUES (DEFAULT,'Table');
INSERT INTO project.furniture_types VALUES (DEFAULT,'Tabouret');
INSERT INTO project.furniture_types VALUES (DEFAULT,'Vaisselier');
INSERT INTO project.furniture_types VALUES (DEFAULT,'Valet muet');*/


CREATE TABLE project.furniture (
	furniture_id SERIAL PRIMARY KEY,
	description TEXT NOT NULL,
	type INTEGER REFERENCES project.furniture_types (type_id) NOT NULL,
	visit_request INTEGER REFERENCES project.visit_requests (visit_request_id) NOT NULL,
	purchase_price DECIMAL NULL,
	withdrawal_date_from_customer TIMESTAMP NULL,
	selling_price DECIMAL NULL,
	special_sale_price DECIMAL NULL,
	deposit_date TIMESTAMP NULL,
	selling_date TIMESTAMP NULL,
	delivery_date TIMESTAMP NULL,
	withdrawal_date_to_customer TIMESTAMP NULL,
	buyer INTEGER REFERENCES project.users (user_id) NOT NULL,
	condition INTEGER NULL,
	unregistered_buyer_email VARCHAR(100) NULL
	--favourite_picture INTEGER REFERENCES project.pictures (picture_id) NULL
);


--INSERT INTO project.furniture VALUES(DEFAULT,'description test',24,1,20.5,NULL,45.5,40.9,NULL,NULL,NULL,NULL,1,NULL,NULL);

CREATE TABLE project.pictures (
	picture_id SERIAL PRIMARY KEY,
	name VARCHAR(100) NOT NULL,
	visible_for_everyone BOOLEAN NOT NULL,
	furniture INTEGER REFERENCES project.furniture (furniture_id) NOT NULL,
	scrolling_picture BOOLEAN NOT NULL
);

--ALTER TABLE project.furniture ADD favourite_picture INTEGER NULL;
--ALTER TABLE project.furniture ADD FOREIGN KEY (favourite_picture) REFERENCES project.pictures(picture_id);

CREATE TABLE project.options (
	option_id SERIAL PRIMARY KEY,
	buyer INTEGER REFERENCES project.users (user_id) NOT NULL,
	furniture INTEGER REFERENCES project.furniture (furniture_id) NOT NULL,
	duration INTEGER NOT NULL,
	date TIMESTAMP NOT NULL,
	status INTEGER NOT NULL
);
