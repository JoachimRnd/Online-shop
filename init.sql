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

CREATE TABLE project.visit_requests
(
    visit_request_id    SERIAL PRIMARY KEY,
    request_date        TIMESTAMP                                  NOT NULL,
    time_slot           TEXT                                       NOT NULL,
    address             INTEGER REFERENCES project.addresses (address_id) NULL,
    status              INTEGER                                    NOT NULL,
    chosen_date_time    TIMESTAMP NULL,
    cancellation_reason TEXT NULL,
    customer            INTEGER REFERENCES project.users (user_id) NOT NULL
);

CREATE TABLE project.furniture_types
(
    type_id SERIAL PRIMARY KEY,
    name    VARCHAR(100) NOT NULL
);

CREATE TABLE project.furniture
(
    furniture_id                  SERIAL PRIMARY KEY,
    description                   TEXT                                                         NOT NULL,
    type                          INTEGER REFERENCES project.furniture_types (type_id)         NOT NULL,
    visit_request                 INTEGER REFERENCES project.visit_requests (visit_request_id) NOT NULL,
    purchase_price                DECIMAL NULL,
    withdrawal_date_from_customer TIMESTAMP NULL,
    selling_price                 DECIMAL NULL,
    special_selling_price            DECIMAL NULL,
    deposit_date                  TIMESTAMP NULL,
    selling_date                  TIMESTAMP NULL,
    delivery_date                 TIMESTAMP NULL,
    withdrawal_date_to_customer   TIMESTAMP NULL,
    buyer                         INTEGER REFERENCES project.users (user_id) NULL,
    condition                     INTEGER NULL,
    unregistered_buyer_email      VARCHAR(100) NULL
    --favourite_picture INTEGER REFERENCES project.pictures (picture_id) NULL
);


CREATE TABLE project.pictures
(
    picture_id           SERIAL PRIMARY KEY,
    name                 VARCHAR(100)                                        NOT NULL,
    visible_for_everyone BOOLEAN                                             NOT NULL,
    furniture            INTEGER REFERENCES project.furniture (furniture_id) NOT NULL,
    scrolling_picture    BOOLEAN                                             NOT NULL
);

ALTER TABLE project.furniture
    ADD favourite_picture INTEGER NULL;
ALTER TABLE project.furniture
    ADD FOREIGN KEY (favourite_picture) REFERENCES project.pictures (picture_id);

CREATE TABLE project.options
(
    option_id SERIAL PRIMARY KEY,
    buyer     INTEGER REFERENCES project.users (user_id)          NOT NULL,
    furniture INTEGER REFERENCES project.furniture (furniture_id) NOT NULL,
    duration  INTEGER                                             NOT NULL,
    date      TIMESTAMP                                           NOT NULL,
    status    INTEGER                                             NOT NULL
);

INSERT INTO project.furniture_types
VALUES (DEFAULT, 'Armoire');

INSERT INTO project.furniture_types
VALUES (DEFAULT, 'Bahut');

INSERT INTO project.furniture_types
VALUES (DEFAULT, 'Bibliothèque');

INSERT INTO project.furniture_types
VALUES (DEFAULT, 'Bonnetière');

INSERT INTO project.furniture_types
VALUES (DEFAULT, 'Buffet');

INSERT INTO project.furniture_types
VALUES (DEFAULT, 'Bureau');

INSERT INTO project.furniture_types
VALUES (DEFAULT, 'Chaise');

INSERT INTO project.furniture_types
VALUES (DEFAULT, 'Chiffonier');

INSERT INTO project.furniture_types
VALUES (DEFAULT, 'Coffre');

INSERT INTO project.furniture_types
VALUES (DEFAULT, 'Coiffeuse');

INSERT INTO project.furniture_types
VALUES (DEFAULT, 'Commode');

INSERT INTO project.furniture_types
VALUES (DEFAULT, 'Confident/Indiscret');

INSERT INTO project.furniture_types
VALUES (DEFAULT, 'Console');

INSERT INTO project.furniture_types
VALUES (DEFAULT, 'Dresse');

INSERT INTO project.furniture_types
VALUES (DEFAULT, 'Fauteuil');

INSERT INTO project.furniture_types
VALUES (DEFAULT, 'Guéridon');

INSERT INTO project.furniture_types
VALUES (DEFAULT, 'Lingère');

INSERT INTO project.furniture_types
VALUES (DEFAULT, 'Lit');

INSERT INTO project.furniture_types
VALUES (DEFAULT, 'Penderie');

INSERT INTO project.furniture_types
VALUES (DEFAULT, 'Secrétaire');

INSERT INTO project.furniture_types
VALUES (DEFAULT, 'Table');

INSERT INTO project.furniture_types
VALUES (DEFAULT, 'Tabouret');

INSERT INTO project.furniture_types
VALUES (DEFAULT, 'Vaisselier');

INSERT INTO project.furniture_types
VALUES (DEFAULT, 'Valet muet');

--Scénario test livrable 4
--Inserts admins
INSERT INTO project.addresses
VALUES (DEFAULT, 'sente des artistes', '1bis', DEFAULT, '4800', 'Verviers', 'Belgique');

INSERT INTO project.users
VALUES (DEFAULT, 'bert', '$2a$10$YnWAR46MkSLTOBR.ptW63uNwsFYSO5aGQ8I/N..Z4mZokFwNShGs.', 'Satcho',
        'Albert', 1, 'bert.satcho@gmail.be', '2021-03-22', true, 2);

INSERT INTO project.addresses
VALUES (DEFAULT, 'sente des artistes', '18', DEFAULT, '4800', 'Verviers', 'Belgique');

INSERT INTO project.users
VALUES (DEFAULT, 'lau', '$2a$10$BSS3FijyW9M2TZ1brbdSP.nB8Mo6Y/Dx28oHGEdP4MB2SjakYTZvW', 'Satcho',
        'Laurent', 2, 'laurent.satcho@gmail.be', '2021-03-22', true, 2);

UPDATE project.users
SET registration_date = '2021-03-22'
WHERE user_id = 2;

UPDATE project.users
SET valid_registration = true
WHERE user_id = 1;

UPDATE project.users
SET user_type = 2
WHERE user_id = 2;

--Inserts clients
INSERT INTO project.addresses
VALUES (DEFAULT, 'Rue de l Eglise', '11', 'B1', '4987', 'Stoumont', 'Belgique');

INSERT INTO project.users
VALUES (DEFAULT, 'Caro', '$2a$10$Olb5dluFvgTVFW7MhGe6jODO8C6edw9qhkow0X0pYleOYB7HsYQR.', 'Line',
        'Caroline', 3, 'caro.line@hotmail.com', '2021-03-23', true, 1);

INSERT INTO project.addresses
VALUES (DEFAULT, 'Rue de Renkin', '7', DEFAULT, '4800', 'Verviers', 'Belgique');

INSERT INTO project.users
VALUES (DEFAULT, 'achil', '$2a$10$UKx1LZF5Np1JjgM5mDfsQeth.sNVcKAFHIJtaQA0DvISmNeQz7qeW', 'Ile',
        'Achille', 4, 'ach.ile@gmail.com', '2021-03-23', true, 0);

INSERT INTO project.addresses
VALUES (DEFAULT, 'Lammerskreuzstrasse', '6', DEFAULT, '52159', 'Roetgen', 'Allemagne');

INSERT INTO project.users
VALUES (DEFAULT, 'bazz', '$2a$10$dwQcHOd5M8OBj6ckqlsfHu6KlKrLVEQzxhyF99iNo9G7F6NSA9C2G', 'Ile',
        'Basile', 5, 'bas.ile@gmail.be', '2021-03-23', true, 0);

UPDATE project.users
SET registration_date = '2021-03-23'
WHERE user_id = 5;

--Insert demandes de visite
INSERT INTO project.visit_requests
VALUES (DEFAULT, '2021-03-24', 'lundi de 18h à 22h', 4, 1, '2021-03-29 20:00:00', DEFAULT, 4);

INSERT INTO project.visit_requests
VALUES (DEFAULT, '2021-03-25', 'lundi de 18h à 22h', 4, 2, DEFAULT, 'Meuble trop récent', 4);

INSERT INTO project.visit_requests
VALUES (DEFAULT, '2021-03-25', 'tous les jours de 15h à 18h', 5, 1, '2021-03-29 15:00:00', DEFAULT,
        5);

--Inserts meubles
INSERT INTO project.furniture
VALUES (DEFAULT, 'Bahut profond d’une largeur de 112 cm et d’une hauteur de 147cm.', 2, 1, 200,
        '2021-03-30', DEFAULT, DEFAULT, DEFAULT, DEFAULT, DEFAULT, DEFAULT, 1, 4, DEFAULT, DEFAULT);

INSERT INTO project.furniture
VALUES (DEFAULT, 'Large bureau 1m87 cm, deux colonnes de tiroirs', 6, 1, 159, '2021-03-30', DEFAULT,
        DEFAULT, DEFAULT, DEFAULT, DEFAULT, DEFAULT, 1, 4, DEFAULT, DEFAULT);

INSERT INTO project.furniture
VALUES (DEFAULT, 'Table jardin en bois brut', 21, 2, DEFAULT, DEFAULT, DEFAULT, DEFAULT, DEFAULT,
        DEFAULT, DEFAULT, DEFAULT, 1, 5, DEFAULT, DEFAULT);

INSERT INTO project.furniture
VALUES (DEFAULT, 'Table en chêne, pieds en fer forgé', 21, 3, 140, '2021-03-29', DEFAULT, DEFAULT,
        DEFAULT, DEFAULT, DEFAULT, DEFAULT, 1, 4, DEFAULT, DEFAULT);

INSERT INTO project.furniture
VALUES (DEFAULT, 'Secrétaire en acajou, marqueterie', 20, 3, 90, '2021-03-29', DEFAULT, DEFAULT,
        DEFAULT, DEFAULT, DEFAULT, DEFAULT, 1, 4, DEFAULT, DEFAULT);

--Inserts pictures
INSERT INTO project.pictures
VALUES (DEFAULT, 'Bahut_2.png', false, 1, false);

INSERT INTO project.pictures
VALUES (DEFAULT, 'Bureau_1.png', false, 2, false);

INSERT INTO project.pictures
VALUES (DEFAULT, 'table-jardin-recente.jpg', false, 3, false);

INSERT INTO project.pictures
VALUES (DEFAULT, 'Table.jpg', false, 4, false);

INSERT INTO project.pictures
VALUES (DEFAULT, 'Secretaire.png', false, 5, false);

