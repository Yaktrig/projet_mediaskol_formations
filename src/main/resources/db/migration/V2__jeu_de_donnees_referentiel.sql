-- Fichier : V2__jeu_de_donnees_referentiel.sql


-- Dumping data for table `departement`
INSERT INTO `departement`
VALUES ('01', '#FFFFFF', 1, 'Ain', 'Auvergne-Rhône-Alpes'),
       ('02', '#FFFFFF', 2, 'Aisne', 'Hauts-de-France'),
       ('03', '#FFFFFF', 3, 'Allier', 'Auvergne-Rhône-Alpes'),
       ('04', '#FFFFFF', 4, 'Alpes-de-Haute-Provence', 'Provence-Alpes-Côte d\'Azur'),
       ('05', '#FFFFFF', 5, 'Hautes-Alpes', 'Provence-Alpes-Côte d\'Azur'),
       ('06', '#FFFFFF', 6, 'Alpes-Maritimes', 'Provence-Alpes-Côte d\'Azur'),
       ('07', '#FFFFFF', 7, 'Ardèche', 'Auvergne-Rhône-Alpes'),
       ('08', '#FFFFFF', 8, 'Ardennes', 'Grand Est'),
       ('09', '#FFFFFF', 9, 'Ariège', 'Occitanie'),
       ('10', '#FFFFFF', 10, 'Aube', 'Grand Est'),
       ('11', '#FFFFFF', 11, 'Aude', 'Occitanie'),
       ('12', '#FFFFFF', 12, 'Aveyron', 'Occitanie'),
       ('13', '#FFFFFF', 13, 'Bouches-du-Rhône', 'Provence-Alpes-Côte d\'Azur'),
       ('14', '#FFFFFF', 14, 'Calvados', 'Normandie'),
       ('15', '#FFFFFF', 15, 'Cantal', 'Auvergne-Rhône-Alpes'),
       ('16', '#FFFFFF', 16, 'Charente', 'Nouvelle-Aquitaine'),
       ('17', '#FFFFFF', 17, 'Charente-Maritime', 'Nouvelle-Aquitaine'),
       ('18', '#FFFFFF', 18, 'Cher', 'Centre-Val de Loire'),
       ('19', '#FFFFFF', 19, 'Corrèze', 'Nouvelle-Aquitaine'),
       ('21', '#FFFFFF', 20, 'Côte-d\'Or', 'Bourgogne-Franche-Comté'),
       ('22', '#A9438F', 21, 'Côtes-d\'Armor', 'Bretagne'),
       ('23', '#FFFFFF', 22, 'Creuse', 'Nouvelle-Aquitaine'),
       ('24', '#FFFFFF', 23, 'Dordogne', 'Nouvelle-Aquitaine'),
       ('25', '#FFFFFF', 24, 'Doubs', 'Bourgogne-Franche-Comté'),
       ('26', '#FFFFFF', 25, 'Drôme', 'Auvergne-Rhône-Alpes'),
       ('27', '#FFFFFF', 26, 'Eure', 'Normandie'),
       ('28', '#FFFFFF', 27, 'Eure-et-Loir', 'Centre-Val de Loire'),
       ('29', '#6FBBD5', 28, 'Finistère', 'Bretagne'),
       ('2A', '#FFFFFF', 29, 'Corse-du-Sud', 'Corse'),
       ('2B', '#FFFFFF', 30, 'Haute-Corse', 'Corse'),
       ('30', '#FFFFFF', 31, 'Gard', 'Occitanie'),
       ('31', '#FFFFFF', 32, 'Haute-Garonne', 'Occitanie'),
       ('32', '#FFFFFF', 33, 'Gers', 'Occitanie'),
       ('33', '#FFFFFF', 34, 'Gironde', 'Nouvelle-Aquitaine'),
       ('34', '#FFFFFF', 35, 'Hérault', 'Occitanie'),
       ('35', '#6FBBD5', 36, 'Ille-et-Vilaine', 'Bretagne'),
       ('36', '#FFFFFF', 37, 'Indre', 'Centre-Val de Loire'),
       ('37', '#FFFFFF', 38, 'Indre-et-Loire', 'Centre-Val de Loire'),
       ('38', '#FFFFFF', 39, 'Isère', 'Auvergne-Rhône-Alpes'),
       ('39', '#FFFFFF', 40, 'Jura', 'Bourgogne-Franche-Comté'),
       ('40', '#FFFFFF', 41, 'Landes', 'Nouvelle-Aquitaine'),
       ('41', '#FFFFFF', 42, 'Loir-et-Cher', 'Centre-Val de Loire'),
       ('42', '#FFFFFF', 43, 'Loire', 'Auvergne-Rhône-Alpes'),
       ('43', '#FFFFFF', 44, 'Haute-Loire', 'Auvergne-Rhône-Alpes'),
       ('44', '#FFFFFF', 45, 'Loire-Atlantique', 'Pays de la Loire'),
       ('45', '#FFFFFF', 46, 'Loiret', 'Centre-Val de Loire'),
       ('46', '#FFFFFF', 47, 'Lot', 'Occitanie'),
       ('47', '#FFFFFF', 48, 'Lot-et-Garonne', 'Nouvelle-Aquitaine'),
       ('48', '#FFFFFF', 49, 'Lozère', 'Occitanie'),
       ('49', '#FFFFFF', 50, 'Maine-et-Loire', 'Pays de la Loire'),
       ('50', '#FFFFFF', 51, 'Manche', 'Normandie'),
       ('51', '#FFFFFF', 52, 'Marne', 'Grand Est'),
       ('52', '#FFFFFF', 53, 'Haute-Marne', 'Grand Est'),
       ('53', '#FFFFFF', 54, 'Mayenne', 'Pays de la Loire'),
       ('54', '#FFFFFF', 55, 'Meurthe-et-Moselle', 'Grand Est'),
       ('55', '#FFFFFF', 56, 'Meuse', 'Grand Est'),
       ('56', '#F2DA42', 57, 'Morbihan', 'Bretagne'),
       ('57', '#FFFFFF', 58, 'Moselle', 'Grand Est'),
       ('58', '#FFFFFF', 59, 'Nièvre', 'Bourgogne-Franche-Comté'),
       ('59', '#FFFFFF', 60, 'Nord', 'Hauts-de-France'),
       ('60', '#FFFFFF', 61, 'Oise', 'Hauts-de-France'),
       ('61', '#FFFFFF', 62, 'Orne', 'Normandie'),
       ('62', '#FFFFFF', 63, 'Pas-de-Calais', 'Hauts-de-France'),
       ('63', '#FFFFFF', 64, 'Puy-de-Dôme', 'Auvergne-Rhône-Alpes'),
       ('64', '#FFFFFF', 65, 'Pyrénées-Atlantiques', 'Nouvelle-Aquitaine'),
       ('65', '#FFFFFF', 66, 'Hautes-Pyrénées', 'Occitanie'),
       ('66', '#FFFFFF', 67, 'Pyrénées-Orientales', 'Occitanie'),
       ('67', '#FFFFFF', 68, 'Bas-Rhin', 'Grand Est'),
       ('68', '#FFFFFF', 69, 'Haut-Rhin', 'Grand Est'),
       ('69', '#FFFFFF', 70, 'Rhône', 'Auvergne-Rhône-Alpes'),
       ('70', '#FFFFFF', 71, 'Haute-Saône', 'Bourgogne-Franche-Comté'),
       ('71', '#FFFFFF', 72, 'Saône-et-Loire', 'Bourgogne-Franche-Comté'),
       ('72', '#FFFFFF', 73, 'Sarthe', 'Pays de la Loire'),
       ('73', '#FFFFFF', 74, 'Savoie', 'Auvergne-Rhône-Alpes'),
       ('74', '#FFFFFF', 75, 'Haute-Savoie', 'Auvergne-Rhône-Alpes'),
       ('75', '#FFFFFF', 76, 'Paris', 'Île-de-France'),
       ('76', '#FFFFFF', 77, 'Seine-Maritime', 'Normandie'),
       ('77', '#FFFFFF', 78, 'Seine-et-Marne', 'Île-de-France'),
       ('78', '#FFFFFF', 79, 'Yvelines', 'Île-de-France'),
       ('79', '#FFFFFF', 80, 'Deux-Sèvres', 'Nouvelle-Aquitaine'),
       ('80', '#FFFFFF', 81, 'Somme', 'Hauts-de-France'),
       ('81', '#FFFFFF', 82, 'Tarn', 'Occitanie'),
       ('82', '#FFFFFF', 83, 'Tarn-et-Garonne', 'Occitanie'),
       ('83', '#FFFFFF', 84, 'Var', 'Provence-Alpes-Côte d\'Azur'),
       ('84', '#FFFFFF', 85, 'Vaucluse', 'Provence-Alpes-Côte d\'Azur'),
       ('85', '#FFFFFF', 86, 'Vendée', 'Pays de la Loire'),
       ('86', '#FFFFFF', 87, 'Vienne', 'Nouvelle-Aquitaine'),
       ('87', '#FFFFFF', 88, 'Haute-Vienne', 'Nouvelle-Aquitaine'),
       ('88', '#FFFFFF', 89, 'Vosges', 'Grand Est'),
       ('89', '#FFFFFF', 90, 'Yonne', 'Bourgogne-Franche-Comté'),
       ('90', '#FFFFFF', 91, 'Territoire de Belfort', 'Bourgogne-Franche-Comté'),
       ('91', '#FFFFFF', 92, 'Essonne', 'Île-de-France'),
       ('92', '#FFFFFF', 93, 'Hauts-de-Seine', 'Île-de-France'),
       ('93', '#FFFFFF', 94, 'Seine-Saint-Denis', 'Île-de-France'),
       ('94', '#FFFFFF', 95, 'Val-de-Marne', 'Île-de-France'),
       ('95', '#FFFFFF', 96, 'Val-d\'Oise', 'Île-de-France'),
       ('971', '#FFFFFF', 97, 'Guadeloupe', 'Guadeloupe'),
       ('972', '#FFFFFF', 98, 'Martinique', 'Martinique'),
       ('973', '#FFFFFF', 99, 'Guyane', 'Guyane'),
       ('974', '#FFFFFF', 100, 'La Réunion', 'La Réunion'),
       ('976', '#FFFFFF', 101, 'Mayotte', 'Mayotte');


-- Dumping data for table `adresse`
INSERT INTO `adresse`
VALUES ('56600', 1, 57, 'Lanester', 'Lieu Dit Kerpont, Les Hauts De Kerousse - Lann Sevelin'),
       ('06000', 2, 6, 'Nice', '4 Avenue Desambrois'),
       ('29283', 3, 28, 'Brest', '10 Rue de Gouesnou');


-- Dumping data for table `personne`
INSERT INTO `personne`
VALUES (1, 'Le tigre', 'Tigrou', 'tigrou.letigre@gmail.fr'),
       (2, 'Le lion', 'Simba', 'simba.lelion@gmail.fr'),
       (3, 'La petite sirène', 'Ariel', 'ariel.lapetitesirene@gmail.fr');

-- Dumping data for table `apprenant`
INSERT INTO `apprenant`
VALUES (b'0', '2000-12-12', 1, 1, '0600000000', 'A123456', 'Fais des bonds partout', 'NUM_PASSEPORT_A_CREER'),
       (b'0', '1998-06-01', 2, 2, '0600000000', 'A123457', '', 'NUM_PASSEPORT_CREE'),
       (b'0', '1989-11-15', 3, 3, '0600000000', 'A123458', '', 'NUM_PASSEPORT_A_CREER');


-- Dumping data for table `type_formation`
INSERT INTO `type_formation`
VALUES (2, 'Distanciel'),
       (1, 'Présentiel');


-- Dumping data for table `type_formation_suivie`
INSERT INTO `type_formation_suivie`
VALUES (1, 1),
       (2, 1),
       (1, 2),
       (3, 2);

