-- ========================================
-- DONNÉES DE TEST POUR MEDIASKOL
-- ========================================

-- 1. TYPES DE FORMATION
INSERT INTO type_formation (libelle_type_formation)
VALUES ('Présentiel'),
       ('Distanciel');

-- 2. DÉPARTEMENTS (num_departement, couleur_departement, nom_departement, region)
INSERT INTO departement (num_departement, couleur_departement, nom_departement, region)
VALUES ('56', '#FF5733', 'Morbihan', 'Bretagne'),
       ('75', '#3357FF', 'Paris', 'Île-de-France'),
       ('69', '#33FF57', 'Rhône', 'Auvergne-Rhône-Alpes');

-- 3. ADRESSES (rue, code_postal, ville, num_departement)
SET @id_dept_56 = (SELECT departement_id FROM departement WHERE num_departement = '56');
SET @id_dept_75 = (SELECT departement_id FROM departement WHERE num_departement = '75');
SET @id_dept_69 = (SELECT departement_id FROM departement WHERE num_departement = '69');

INSERT INTO adresse (rue, code_postal, ville, num_departement)
VALUES ('Lieu Dit Kerpont, Les Hauts De Kerousse - Lann Sevelin', '56600', 'Lanester', @id_dept_56),
       ('4 Avenue Desambrois', '06000', 'Nice', NULL),
       ('10 Rue de Gouesnou', '29200', 'Brest', NULL),
       ('15 Rue de la République', '75001', 'Paris', @id_dept_75),
       ('20 Avenue des Champs', '69001', 'Lyon', @id_dept_69);

-- 4. PERSONNES (table mère - contient nom, prenom, email)
INSERT INTO personne (nom, prenom, email)
VALUES ('Dupont', 'Jean', 'jean.dupont@mediaskol.fr'),
       ('Martin', 'Sophie', 'sophie.martin@mediaskol.fr'),
       ('Le tigre', 'Tigrou', 'tigrou.letigre@gmail.fr'),
       ('Le lion', 'Simba', 'simba.lelion@gmail.fr'),
       ('La petite sirène', 'Ariel', 'ariel.lapetitesirene@gmail.fr');

-- 5. SALARIÉS (table fille - personne_id, couleur_salarie, statut_inscription, role_salarie, mot_de_passe)
SET @id_jean = (SELECT personne_id FROM personne WHERE email = 'jean.dupont@mediaskol.fr');
SET @id_sophie = (SELECT personne_id FROM personne WHERE email = 'sophie.martin@mediaskol.fr');
SET @id_tigrou = (SELECT personne_id FROM personne WHERE email = 'tigrou.letigre@gmail.fr');
SET @id_simba = (SELECT personne_id FROM personne WHERE email = 'simba.lelion@gmail.fr');
SET @id_ariel = (SELECT personne_id FROM personne WHERE email = 'ariel.lapetitesirene@gmail.fr');

INSERT INTO salarie (personne_id, couleur_salarie, statut_inscription, role_salarie, mot_de_passe)
VALUES (@id_jean, '#FF5733', true, 'ADMIN', NULL),
       (@id_sophie, '#3357FF', true, 'SALARIE', NULL);

-- 6. UTILISATEURS (pour l'authentification JWT)
-- Mot de passe: "password" encodé en BCrypt
INSERT INTO users (pseudo, password, role, authority, login, salarie_id)
VALUES ('admin', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'ADMIN', 'ROLE_ADMIN', 'admin', @id_jean),
       ('salarie', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'SALARIE', 'ROLE_SALARIE', 'salarie', @id_sophie);

-- 7. APPRENANTS (table fille - personne_id, date_naissance, apprenant_actif, num_portable_apprenant, num_passeport, commentaire_apprenant, statut_num_passeport, adresse_id)
SET @id_adresse_1 = (SELECT adresse_id FROM adresse WHERE ville = 'Lanester' LIMIT 1);
SET @id_adresse_2 = (SELECT adresse_id FROM adresse WHERE ville = 'Nice' LIMIT 1);
SET @id_adresse_3 = (SELECT adresse_id FROM adresse WHERE ville = 'Brest' LIMIT 1);

INSERT INTO apprenant (personne_id, date_naissance, apprenant_actif, num_portable_apprenant, num_passeport, commentaire_apprenant, statut_num_passeport, adresse_id)
VALUES (@id_tigrou, '2000-12-12', true, '0600000001', 'A123456', 'Fais des bonds partout', 'NUM_PASSEPORT_A_CREER', @id_adresse_1),
       (@id_simba, '1998-06-01', true, '0600000002', 'A123457', 'Roi de la savane', 'NUM_PASSEPORT_CREE', @id_adresse_2),
       (@id_ariel, '1989-11-15', true, '0600000003', 'A123458', 'Aime les trésors', 'NUM_PASSEPORT_A_CREER', @id_adresse_3);

-- 8. FORMATIONS (type_formation_id, theme_formation, libelle_formation)
SET @id_type_presentiel = (SELECT type_formation_id FROM type_formation WHERE libelle_type_formation = 'Présentiel');
SET @id_type_distanciel = (SELECT type_formation_id FROM type_formation WHERE libelle_type_formation = 'Distanciel');

INSERT INTO formation (type_formation_id, theme_formation, libelle_formation)
VALUES (@id_type_presentiel, 'Développement', 'Développement Web Full Stack'),
       (@id_type_presentiel, 'DevOps', 'DevOps et Cloud Computing'),
       (@id_type_distanciel, 'Data', 'Data Science avec Python'),
       (@id_type_presentiel, 'Sécurité', 'Cybersécurité');

-- ========================================
-- NOTES D'UTILISATION :
-- ========================================
-- Pour vous connecter :
--   Pseudo: admin
--   Password: password
--   OU
--   Pseudo: salarie
--   Password: password
--
-- Après le démarrage, allez sur :
--   http://localhost:8080/mediaskol/swagger-ui.html
-- ========================================
