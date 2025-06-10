

CREATE DATABASE gestion_vente_informatique;
CREATE TABLE produits (
id SERIAL PRIMARY KEY,
nom VARCHAR(100) NOT NULL,
description TEXT,
prix DECIMAL(10, 2) NOT NULL,
stock INTEGER NOT NULL CHECK (stock >= 0)
);

CREATE TABLE clients (
id SERIAL PRIMARY KEY,
nom VARCHAR(100) NOT NULL,
email VARCHAR(100) UNIQUE NOT NULL,
telephone VARCHAR(20)
);


CREATE TABLE ventes (
id SERIAL PRIMARY KEY,
client_id INTEGER NOT NULL,
produit_id INTEGER NOT NULL,
quantite INTEGER NOT NULL CHECK (quantite > 0),
montant_total DECIMAL(10, 2) NOT NULL,
date_vente TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
FOREIGN KEY (client_id) REFERENCES clients(id) ON DELETE RESTRICT,
FOREIGN KEY (produit_id) REFERENCES produits(id) ON DELETE RESTRICT
);


















-- Créer la base de données
CREATE DATABASE gestion_vente_informatique;


\c gestion_vente_informatique;
-- Se connecter à la base de données (à exécuter manuellement dans psql après création)
-- \c gestion_vente_informatique

-- Créer la table produits
CREATE TABLE produits (
                          id SERIAL PRIMARY KEY,
                          nom VARCHAR(100) NOT NULL,
                          description TEXT,
                          prix DECIMAL(10, 2) NOT NULL,
                          stock INTEGER NOT NULL CHECK (stock >= 0)
);

-- Créer la table clients
CREATE TABLE clients (
                         id SERIAL PRIMARY KEY,
                         nom VARCHAR(100) NOT NULL,
                         email VARCHAR(100) UNIQUE NOT NULL,
                         telephone VARCHAR(20)
);

-- Créer la table ventes
CREATE TABLE ventes (
                        id SERIAL PRIMARY KEY,
                        client_id INTEGER NOT NULL,
                        produit_id INTEGER NOT NULL,
                        quantite INTEGER NOT NULL CHECK (quantite > 0),
                        montant_total DECIMAL(10, 2) NOT NULL,
                        date_vente TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        FOREIGN KEY (client_id) REFERENCES clients(id) ON DELETE RESTRICT,
                        FOREIGN KEY (produit_id) REFERENCES produits(id) ON DELETE RESTRICT
);

-- Insérer des données dans la table produits
INSERT INTO produits (nom, description, prix, stock) VALUES
                                                         ('Ordinateur Portable', 'PC portable 16Go RAM', 999.99, 10),
                                                         ('Souris Optique', 'Souris sans fil', 19.99, 50),
                                                         ('Clavier Mécanique', 'Clavier RGB', 79.99, 20);

-- Insérer des données dans la table clients
INSERT INTO clients (nom, email, telephone) VALUES
                                                ('Jean Dupont', 'jean.dupont@example.com', '0123456789'),
                                                ('Marie Curie', 'marie.curie@example.com', '0987654321');

-- Insérer des données dans la table ventes
INSERT INTO ventes (client_id, produit_id, quantite, montant_total, date_vente) VALUES
                                                                                    (1, 1, 2, 1999.98, '2025-03-03 10:00:00'),
                                                                                    (2, 2, 3, 59.97, '2025-03-03 12:00:00');


INSERT INTO produits (nom, description, prix, stock) VALUES
('Ordinateur Portable', 'PC portable 16Go RAM', 999.99, 10),
('Souris Optique', 'Souris sans fil', 19.99, 50),
('Clavier Mécanique', 'Clavier RGB', 79.99, 20);


INSERT INTO clients (nom, email, telephone) VALUES
('Jean Dupont', 'jean.dupont@example.com', '0123456789'),
('Marie Curie', 'marie.curie@example.com', '0987654321');

INSERT INTO ventes (client_id, produit_id, quantite, montant_total, date_vente) VALUES
(1, 1, 2, 1999.98, '2025-03-03 10:00:00'),
(2, 2, 3, 59.97, '2025-03-03 12:00:00');
