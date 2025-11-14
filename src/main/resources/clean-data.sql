-- Script de nettoyage pour les tables existantes uniquement
SET FOREIGN_KEY_CHECKS=0;

-- Suppression des données (ordre inverse des dépendances)
DELETE FROM apprenant WHERE 1=1;
DELETE FROM users WHERE 1=1;
DELETE FROM salarie WHERE 1=1;
DELETE FROM personne WHERE 1=1;
DELETE FROM formation WHERE 1=1;
DELETE FROM type_formation WHERE 1=1;
DELETE FROM adresse WHERE 1=1;
DELETE FROM departement WHERE 1=1;

SET FOREIGN_KEY_CHECKS=1;
