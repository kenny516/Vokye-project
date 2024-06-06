--Creation de la database 
CREATE DATABASE voke;

-- Makany .\c voke;
\c voke;

--Creation table :

-- Employee --
CREATE TABLE type_emp(
    id_type_emp SERIAL PRIMARY KEY,
    Designation VARCHAR(20),
    salaire_base NUMERIC(10,2),
    Pourcentage INT
);

CREATE TABLE employee(
    id_employee SERIAL PRIMARY KEY ,
    id_type_emp int,
    nom VARCHAR(50),
    prenom VARCHAR(50),
    Date_entrer DATE,
    Date_fin DATE,
    FOREIGN KEY (id_type_emp) REFERENCES type_emp(id_type_emp)
);

-- Stock -- 
---- All that will be used ----
CREATE TABLE Unite(
    id_unite SERIAL PRIMARY KEY,
    nom_unite VARCHAR(50)
);

CREATE TABLE Utilitaire(
    id_utilitaire SERIAL PRIMARY KEY,
    nom VARCHAR(50),
    id_unite INT,
    FOREIGN KEY (id_unite) REFERENCES Unite(id_unite)
);

CREATE TABLE Ingredient(
    id_ingredient SERIAL PRIMARY KEY, 
    nom VARCHAR(50),
    id_unite INT,
    FOREIGN KEY (id_unite) REFERENCES Unite(id_unite)
);

---- Stock en générale ----
CREATE TABLE Stock_utilitaire(
    id_stock_utilitaire SERIAL PRIMARY KEY,
    id_utilitaire INT ,
    quantite INT,
    FOREIGN KEY (id_utilitaire) REFERENCES Utilitaire(id_utilitaire)
);

CREATE TABLE Stock_ingredient(
    id_stock_Ingredient SERIAL PRIMARY KEY ,
    id_ingredient INT,
    quantite INT ,
    FOREIGN KEY (id_ingredient) REFERENCES Ingredient(id_ingredient)
);

-- Achat --
CREATE TABLE achat_ingredient(
    id_achat_Ingredient SERIAL PRIMARY KEY,
    id_ingredient INT , 
    prix NUMERIC(10,2),
    quantite INTEGER,
    data_achat DATE,
    FOREIGN KEY (id_ingredient) REFERENCES Ingredient(id_ingredient)
);

CREATE TABLE achat_utilitaire(
    id_achat_utilitaire SERIAL PRIMARY KEY,
    id_utilitaire INT,
    prix NUMERIC(10,2),
    quantite INTEGER,
    data_achat DATE,
    FOREIGN KEY (id_utilitaire) REFERENCES Utilitaire(id_utilitaire)
);

-- Depense
CREATE TABLE depense(
    id_depense SERIAL PRIMARY KEY,
    id_type_depense INT,
    prix NUMERIC(10,2),
    date_depense DATE,
    FOREIGN KEY (id_type_depense) REFERENCES type_depense(id_type_depense)
);

CREATE TABLE type_depense(
    id_type_depense SERIAL PRIMARY KEY,
    Designation VARCHAR(50) 
);

-- Produit == Recette
CREATE TABLE Ingredient_produit(
    id_ingredient_produit SERIAL PRIMARY KEY,
    id_produit INT,
    id_ingredient INT,
    id_unite INT,
    quantite INT,
    FOREIGN KEY (id_produit) REFERENCES Produit(id_produit)
);

CREATE TABLE Produit(
    id_produit SERIAL PRIMARY KEY,
    nom VARCHAR(50),
    prix NUMERIC(10,2)
);

--Vente 
CREATE TABLE chariot(
    id_chariot SERIAL PRIMARY KEY,
    id_employee INT
);

CREATE TABLE vente (
    id_vente SERIAL PRIMARY KEY,
    id_chariot INT,
    id_produit INT,
    id_point_vente INT,
    quantite INT,
    date_vente DATE,
    FOREIGN KEY (id_produit) REFERENCES Produit(id_produit),
    FOREIGN KEY (id_chariot) REFERENCES chariot(id_chariot),
    FOREIGN KEY (id_point_vente) REFERENCES point_vente(id_point_vente)
);

CREATE TABLE point_vente(
    id_point_vente SERIAL PRIMARY KEY,
    localisation VARCHAR(200),
    FOREIGN KEY (id_chariot) REFERENCES chariot(id_chariot)
);

--For the equipment and the state
CREATE TABLE Etat(
    id_etat SERIAL PRIMARY KEY,
    nom VARCHAR(50)
);

CREATE TABLE Etat_utilitaire(
    id_etat_utilitaire SERIAL PRIMARY KEY,
    id_utilitaire INTEGER,
    id_etat INTEGER,
    date_etat_utilitaire DATE
);