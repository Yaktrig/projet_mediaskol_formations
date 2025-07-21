-- Insertion des types de formation
-- INSERT INTO type_formation (libelle_type_formation)
-- VALUES ('Présentiel'),
--        ('Distanciel');

-- Insertion des adresses
INSERT INTO adresse (rue, code_postal, ville)
VALUES ('Lieu Dit Kerpont, Les Hauts De Kerousse - Lann Sevelin', '56600', 'Lanester'),
       ('4 Avenue Desambrois', '06000', 'Nice'),
       ('10 Rue de Gouesnou', '29283', 'Brest');

-- Insertion des apprenants
INSERT INTO apprenant (nom, prenom, email, num_portable, date_naissance, apprenant_actif, num_passeport,
                       commentaire_apprenant, statut_num_passeport, adresse_id)
VALUES ('Le tigre', 'Tigrou', 'tigrou.letigre@gmail.fr', '0600000000', '2000-12-12', true, 'A123456',
        'Fais des bonds partout', 'NUM_PASSEPORT_A_CREER', 1),
       ('Le lion', 'Simba', 'simba.lelion@gmail.fr', '0600000000', '1998-06-01', true, 'A123457', '',
        'NUM_PASSEPORT_CREE', 2),
       ('La petite sirène', 'Ariel', 'ariel.lapetitesirene@gmail.fr', '0600000000', '1989-11-15', true, 'A123458', '',
        'NUM_PASSEPORT_A_CREER', 3);

-- Liaisons entre apprenants et types de formation (table de jointure)
INSERT INTO apprenant_type_formation (personne_id, type_formation_id)
VALUES (1, 1), -- Tigrou : Présentiel
       (1, 2), -- Tigrou : Distanciel
       (2, 2), -- Simba : Distanciel
       (3, 1); -- Ariel : Présentiel
