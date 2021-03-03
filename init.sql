DROP SCHEMA IF EXISTS projet CASCADE;
CREATE SCHEMA projet;

CREATE TABLE projet.adresses (
	id_adresse SERIAL PRIMARY KEY,
	rue VARCHAR(100) NOT NULL,
	numero VARCHAR(100) NOT NULL,
	boite VARCHAR(100) NULL,
	code_postal VARCHAR(100) NOT NULL,
	commune VARCHAR(100) NOT NULL,
	pays VARCHAR(100) NOT NULL
);

CREATE TABLE projet.utilisateurs (
	id_utilisateur SERIAL PRIMARY KEY,
	pseudo VARCHAR(100) NOT NULL,
	mot_de_passe CHARACTER(60) NOT NULL,
	nom VARCHAR(100) NOT NULL,
	prenom VARCHAR(100) NOT NULL,
	adresse INTEGER REFERENCES projet.adresses (id_adresse) NOT NULL,
	email VARCHAR(100) NOT NULL,
	date_inscription TIMESTAMP NOT NULL,
	inscription_valide BOOLEAN NOT NULL,
	type_utilisateur INTEGER NOT NULL
);

INSERT INTO projet.adresses VALUES (DEFAULT, 'rue', 'numero', 'boite', 'code_postal', 'commune', 'pays');

INSERT INTO projet.utilisateurs VALUES (DEFAULT, 'test', '$2a$10$qSBZrtuxTN3tM///i5rM8Opd3ioOk2vG.olTPH/UZqP8rjCtg19Zm', 'nom', 'prenom', 1, 'email', NOW(), 'true', '0');

SELECT * FROM projet.adresses;

/*
CREATE TABLE projet.demandes_de_visite (
	id_demande_de_visite SERIAL PRIMARY KEY,
	date_demande TIMESTAMP NOT NULL,
	plage_horaire TEXT NOT NULL,
	adresse INTEGER REFERENCES projet.adresses (id_adresse) NULL,
	etat INTEGER NOT NULL,
	date_et_heure_choisie TIMESTAMP NULL,
	raison_annulation TEXT NULL,
	client INTEGER REFERENCES projet.utilisateurs (id_utilisateur) NOT NULL
);

CREATE TABLE projet.type_de_meubles (
	id_type SERIAL PRIMARY KEY,
	nom VARCHAR(100) NOT NULL
);

CREATE TABLE projet.meubles (
	id_meuble SERIAL PRIMARY KEY,
	description TEXT NOT NULL,
	type INTEGER REFERENCES projet.type_de_meubles (id_type) NOT NULL,
	demande_de_visite INTEGER REFERENCES projet.demandes_de_visite (id_demande_de_visite) NOT NULL,
	prix_achat DECIMAL NULL,
	date_emportee TIMESTAMP NULL,
	prix_vente DECIMAL NULL,
	prix_special DECIMAL NULL,
	date_depot_en_magasin TIMESTAMP NULL,
	date_vente TIMESTAMP NULL,
	date_livraison TIMESTAMP NULL,
	date_retrait TIMESTAMP NULL,
	acheteur INTEGER REFERENCES projet.utilisateurs (id_utilisateur) NOT NULL,
	etat INTEGER NULL,
	email_acheteur_non_inscrit VARCHAR(100) NULL
	--photo_preferee INTEGER REFERENCES projet.photos (id_photo) NULL
);

CREATE TABLE projet.photos (
	id_photo SERIAL PRIMARY KEY,
	nom VARCHAR(100) NOT NULL,
	est_visible_pour_tous BOOLEAN NOT NULL,
	meuble INTEGER REFERENCES projet.meubles (id_meuble) NOT NULL,
	photo_defilante BOOLEAN NOT NULL
);
*/
--ALTER TABLE meubles ADD photo_preferee INTEGER FOREIGN KEY REFERENCES projet.photos(id_photo) NULL;
/*
CREATE TABLE projet.options (
	id_option SERIAL PRIMARY KEY,
	acheteur INTEGER REFERENCES projet.utilisateurs (id_utilisateur) NOT NULL,
	meuble INTEGER REFERENCES projet.meubles (id_meuble) NOT NULL,
	duree INTEGER NOT NULL,
	date TIMESTAMP NOT NULL,
	etat INTEGER NOT NULL
);*/
