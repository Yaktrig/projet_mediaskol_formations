-- Fichier : V1__creation_table_utilisateur.sql

-- Table structure for table `departement`
CREATE TABLE `departement`
(
    `num_departement`     varchar(3)   NOT NULL,
    `couleur_departement` varchar(7)   NOT NULL,
    `departement_id`      bigint       NOT NULL AUTO_INCREMENT,
    `nom_departement`     varchar(100) NOT NULL,
    `region`              varchar(100) NOT NULL,
    PRIMARY KEY (`departement_id`),
    UNIQUE KEY `UKldqddfoh1xdjv2bkw9q81p9g7` (`num_departement`),
    UNIQUE KEY `UK1ktn2mjhqmrkw5105nok3y39h` (`nom_departement`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 102
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- Table structure for table `adresse`
CREATE TABLE `adresse`
(
    `code_postal`     varchar(5)   DEFAULT NULL,
    `adresse_id`      bigint NOT NULL AUTO_INCREMENT,
    `num_departement` bigint       DEFAULT NULL,
    `ville`           varchar(200) DEFAULT NULL,
    `rue`             varchar(250) DEFAULT NULL,
    PRIMARY KEY (`adresse_id`),
    KEY `FK6y7oroon5dwr1l8jmbffr8mdb` (`num_departement`),
    CONSTRAINT `FK6y7oroon5dwr1l8jmbffr8mdb` FOREIGN KEY (`num_departement`) REFERENCES `departement` (`departement_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


-- Table structure for table `categorie_document`
CREATE TABLE `categorie_document`
(
    `categorie_document_id`      bigint       NOT NULL AUTO_INCREMENT,
    `libelle_categorie_document` varchar(120) NOT NULL,
    PRIMARY KEY (`categorie_document_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;



-- Table structure for table `fin_session_formation`
CREATE TABLE `fin_session_formation`
(
    `date_limite_yoda_fin_session_formation` date                                                                                                                                                           DEFAULT NULL,
    `statut_yoda_fin_session_formation`      varchar(5)                                                                                                                                                     DEFAULT NULL,
    `fin_session_formation_id`               bigint NOT NULL AUTO_INCREMENT,
    `commentaire_fin_session_formation`      varchar(2000)                                                                                                                                                  DEFAULT NULL,
    `statut_fin_session_formation`           enum ('SESSION_FIN_FORMATION_TERMINEE','SESSION_FIN_FORMATION_TERMINEE_NON_VALIDEE','SESSION_FIN_FORMATION_TERMINEE_VALIDEE','SESSION_FORMATION_NON_TERMINEE') DEFAULT NULL,
    PRIMARY KEY (`fin_session_formation_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


-- Table structure for table `type_formation`
CREATE TABLE `type_formation`
(
    `type_formation_id`      bigint NOT NULL AUTO_INCREMENT,
    `libelle_type_formation` varchar(100) DEFAULT NULL,
    PRIMARY KEY (`type_formation_id`),
    UNIQUE KEY `UKdotf8w63nfx8ao4tvfftofc5l` (`libelle_type_formation`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


-- Table structure for table `session_foad`
CREATE TABLE `session_foad`
(
    `date_debut_session_foad`   date   NOT NULL,
    `date_fin_session_foad`     date   NOT NULL,
    `date_relance_session_foad` date          DEFAULT NULL,
    `nb_bloc_session_foad`      int           DEFAULT NULL,
    `session_foad_id`           bigint NOT NULL AUTO_INCREMENT,
    `contrat_session_foad`      varchar(300)  DEFAULT NULL,
    `commentaire_session_foad`  varchar(2000) DEFAULT NULL,
    PRIMARY KEY (`session_foad_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;



-- Table structure for table `salle`
CREATE TABLE `salle`
(
    `cle_salle`         bit(1)       NOT NULL,
    `adresse_id`        bigint        DEFAULT NULL,
    `salle_id`          bigint       NOT NULL AUTO_INCREMENT,
    `digicode_salle`    varchar(10)   DEFAULT NULL,
    `portable_contact`  varchar(10)   DEFAULT NULL,
    `nom_contact`       varchar(200)  DEFAULT NULL,
    `commentaire_salle` varchar(2000) DEFAULT NULL,
    `mail_contact`      varchar(255)  DEFAULT NULL,
    `nom_salle`         varchar(255) NOT NULL,
    PRIMARY KEY (`salle_id`),
    UNIQUE KEY `UK97myy7m1d021tu6sawxg6mwlm` (`adresse_id`),
    CONSTRAINT `FK6yuh9esode365ua0uq8dggsg3` FOREIGN KEY (`adresse_id`) REFERENCES `adresse` (`adresse_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


-- Table structure for table `personne`
CREATE TABLE `personne`
(
    `personne_id` bigint       NOT NULL AUTO_INCREMENT,
    `nom`         varchar(90)  NOT NULL,
    `prenom`      varchar(150) NOT NULL,
    `email`       varchar(255) NOT NULL,
    PRIMARY KEY (`personne_id`),
    UNIQUE KEY `UKlif11ug7pqkdimuk0beonbfng` (`email`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


-- Table structure for table `formation`
CREATE TABLE `formation`
(
    `formation_id`      bigint       NOT NULL AUTO_INCREMENT,
    `type_formation_id` bigint       NOT NULL,
    `theme_formation`   varchar(20)  NOT NULL,
    `libelle_formation` varchar(300) NOT NULL,
    PRIMARY KEY (`formation_id`),
    KEY `FKqs830q8m6iu0revniohh64c5j` (`type_formation_id`),
    CONSTRAINT `FKqs830q8m6iu0revniohh64c5j` FOREIGN KEY (`type_formation_id`) REFERENCES `type_formation` (`type_formation_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;



-- Table structure for table `formateur`
CREATE TABLE `formateur`
(
    `adresse_id`             bigint        DEFAULT NULL,
    `personne_id`            bigint      NOT NULL,
    `num_portable_formateur` varchar(10)   DEFAULT NULL,
    `statut_formateur`       varchar(10) NOT NULL,
    `zone_intervention`      varchar(1000) DEFAULT NULL,
    `commentaire_formateur`  varchar(2000) DEFAULT NULL,
    PRIMARY KEY (`personne_id`),
    UNIQUE KEY `UK3jndbltha7bt0j98o2nxq7k57` (`adresse_id`),
    CONSTRAINT `FKdfe0geif73qeh0qot874ad0w0` FOREIGN KEY (`adresse_id`) REFERENCES `adresse` (`adresse_id`),
    CONSTRAINT `FKfd2r6thtgwamso4d5w8my4m3p` FOREIGN KEY (`personne_id`) REFERENCES `personne` (`personne_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


-- Table structure for table `note_de_frais`
CREATE TABLE `note_de_frais`
(
    `date_note_de_frais`           date         NOT NULL,
    `date_reglement_note_de_frais` date         NOT NULL,
    `date_session_formation`       date         NOT NULL,
    `montant_frais_divers`         float        DEFAULT NULL,
    `montant_nuit_hotel`           float        DEFAULT NULL,
    `montant_pause_cafe`           float        DEFAULT NULL,
    `montant_repas`                float        DEFAULT NULL,
    `montant_total_note_de_frais`  float        NOT NULL,
    `note_de_frais_id`             int          NOT NULL AUTO_INCREMENT,
    `formateur_id`                 bigint       NOT NULL,
    `libelle_session_formation`    varchar(120) NOT NULL,
    `libelle_frais_divers`         varchar(255) DEFAULT NULL,
    PRIMARY KEY (`note_de_frais_id`),
    KEY `FK4nx9t326su2hvdtxc4fljk92p` (`formateur_id`),
    CONSTRAINT `FK4nx9t326su2hvdtxc4fljk92p` FOREIGN KEY (`formateur_id`) REFERENCES `formateur` (`personne_id`),
    CONSTRAINT `note_de_frais_chk_1` CHECK ((`montant_pause_cafe` <= 7)),
    CONSTRAINT `note_de_frais_chk_2` CHECK ((`montant_repas` <= 15))
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;




-- Table structure for table `formations_dispensees`
CREATE TABLE `formations_dispensees`
(
    `formateur_id` bigint NOT NULL,
    `formation_id` bigint NOT NULL,
    KEY `FKg8usv2h8on586edkdcefm3578` (`formation_id`),
    KEY `FK11c68whncgn3ehlfhrkgulu1m` (`formateur_id`),
    CONSTRAINT `FK11c68whncgn3ehlfhrkgulu1m` FOREIGN KEY (`formateur_id`) REFERENCES `formateur` (`personne_id`),
    CONSTRAINT `FKg8usv2h8on586edkdcefm3578` FOREIGN KEY (`formation_id`) REFERENCES `formation` (`formation_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


-- Table structure for table `apprenant`
CREATE TABLE `apprenant`
(
    `apprenant_actif`        bit(1)                                                                        NOT NULL,
    `date_naissance`         date                                                                          NOT NULL,
    `adresse_id`             bigint        DEFAULT NULL,
    `personne_id`            bigint                                                                        NOT NULL,
    `num_portable_apprenant` varchar(10)   DEFAULT NULL,
    `num_passeport`          varchar(120)  DEFAULT NULL,
    `commentaire_apprenant`  varchar(2000) DEFAULT NULL,
    `statut_num_passeport`   enum ('NUM_PASSEPORT_A_CREER','NUM_PASSEPORT_A_ENVOYER','NUM_PASSEPORT_CREE') NOT NULL,
    PRIMARY KEY (`personne_id`),
    UNIQUE KEY `UKejlv3a2y6kth7r97lopev0wu6` (`adresse_id`),
    UNIQUE KEY `UKjepcuv7b1oehyq6af5cbr4u02` (`num_passeport`),
    CONSTRAINT `FK443u1qfuohgep7htm4vv54k16` FOREIGN KEY (`personne_id`) REFERENCES `personne` (`personne_id`),
    CONSTRAINT `FK8efirts8r8fw3o0yv7a3yq02x` FOREIGN KEY (`adresse_id`) REFERENCES `adresse` (`adresse_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


--
-- Table structure for table `session_formation`
CREATE TABLE `session_formation`
(
    `date_debut_session`        date                                                                                                                                   NOT NULL,
    `nb_heure_session`          int                                                                                                                                    NOT NULL,
    `statut_yoda`               varchar(5)                                                                                                                             NOT NULL,
    `fin_session_formation_id`  bigint      DEFAULT NULL,
    `formation_id`              bigint                                                                                                                                 NOT NULL,
    `num_departement`           bigint      DEFAULT NULL,
    `session_foad_id`           bigint      DEFAULT NULL,
    `session_formation_id`      bigint                                                                                                                                 NOT NULL AUTO_INCREMENT,
    `no_af_yoda`                varchar(30) DEFAULT NULL,
    `libelle_session_formation` varchar(50) DEFAULT NULL,
    `statut_session_formation`  enum ('DOSSIER_EN_COURS','DOSSIER_TERMINE','SESSION_FORMATION_ANNULEE','SESSION_FORMATION_NON_COMMENCEE','SESSION_FORMATION_TERMINEE') NOT NULL,
    PRIMARY KEY (`session_formation_id`),
    UNIQUE KEY `UK3n90j596bod42xbjyicc9327r` (`fin_session_formation_id`),
    UNIQUE KEY `UKqkpthrhuabjsm23rk9xn0h59t` (`session_foad_id`),
    UNIQUE KEY `UK66dds8qehurxr0jrlqbu5cpyi` (`no_af_yoda`),
    KEY `FK815bc9359y6wwvhs73yoeknsq` (`num_departement`),
    KEY `FKifvwsp4kg8086jk5vg43c1fuw` (`formation_id`),
    CONSTRAINT `FK815bc9359y6wwvhs73yoeknsq` FOREIGN KEY (`num_departement`) REFERENCES `departement` (`departement_id`),
    CONSTRAINT `FKif4n9p6ljhyxj3h049sgwfnbh` FOREIGN KEY (`fin_session_formation_id`) REFERENCES `fin_session_formation` (`fin_session_formation_id`),
    CONSTRAINT `FKifvwsp4kg8086jk5vg43c1fuw` FOREIGN KEY (`formation_id`) REFERENCES `formation` (`formation_id`),
    CONSTRAINT `FKspjhbfgygx3n17jx1v6icur90` FOREIGN KEY (`session_foad_id`) REFERENCES `session_foad` (`session_foad_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;



-- Table structure for table `session_formateur`
CREATE TABLE `session_formateur`
(
    `formateur_id`                  bigint NOT NULL,
    `session_formateur_id`          bigint NOT NULL AUTO_INCREMENT,
    `session_formation_id`          bigint NOT NULL,
    `commentaire_session_formateur` varchar(2000)                                                                                                                                                                                                                     DEFAULT NULL,
    `statut_session_formateur`      enum ('SESSION_FORMATEUR_AE_FACTURE_REGLEE','SESSION_FORMATEUR_ATTENTE_PRESENCE','SESSION_FORMATEUR_EN_ATTENTE_PAIEMENT','SESSION_FORMATEUR_PRESENCE_ANNULEE','SESSION_FORMATEUR_PRESENCE_CONFIRMEE','SESSION_FORMATEUR_SALARIE') DEFAULT NULL,
    PRIMARY KEY (`session_formateur_id`),
    KEY `FKc3lygovlllf6tywnon9hviwiy` (`formateur_id`),
    KEY `FKmo2nbirmokal0p8ymltc2kd2x` (`session_formation_id`),
    CONSTRAINT `FKc3lygovlllf6tywnon9hviwiy` FOREIGN KEY (`formateur_id`) REFERENCES `formateur` (`personne_id`),
    CONSTRAINT `FKmo2nbirmokal0p8ymltc2kd2x` FOREIGN KEY (`session_formation_id`) REFERENCES `session_formation` (`session_formation_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


-- Table structure for table `session_salle`
CREATE TABLE `session_salle`
(
    `cout_session_salle`        float                                                                                               DEFAULT NULL,
    `salle_id`                  bigint NOT NULL,
    `session_formation_id`      bigint NOT NULL,
    `session_salle_id`          bigint NOT NULL AUTO_INCREMENT,
    `commentaire_session_salle` varchar(2000)                                                                                       DEFAULT NULL,
    `statut_session_salle`      enum ('SESSION_SALLE_ATTENTE_DEVIS','SESSION_SALLE_VALIDEE','SESSION_SALLE_VALIDE_CLE_OU_DIGICODE') DEFAULT NULL,
    PRIMARY KEY (`session_salle_id`),
    KEY `FKhyc3osrnf0ke519ukqbhslnsa` (`salle_id`),
    KEY `FK9601sosi1aewjcyxgvkm4kp8v` (`session_formation_id`),
    CONSTRAINT `FK9601sosi1aewjcyxgvkm4kp8v` FOREIGN KEY (`session_formation_id`) REFERENCES `session_formation` (`session_formation_id`),
    CONSTRAINT `FKhyc3osrnf0ke519ukqbhslnsa` FOREIGN KEY (`salle_id`) REFERENCES `salle` (`salle_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;




-- Table structure for table `session_lieu_date`
CREATE TABLE `session_lieu_date`
(
    `date_session`             date   NOT NULL,
    `duree`                    int                                                                                                                DEFAULT NULL,
    `debut_heure_visio`        datetime(6)                                                                                                        DEFAULT NULL,
    `session_formateur_id`     bigint                                                                                                             DEFAULT NULL,
    `session_formation_id`     bigint NOT NULL,
    `session_lieu_date_id`     bigint NOT NULL AUTO_INCREMENT,
    `session_salle_id`         bigint                                                                                                             DEFAULT NULL,
    `lieu_session`             varchar(100)                                                                                                       DEFAULT NULL,
    `statut_session_lieu_date` enum ('SESSION_LIEU_DATE_FACTURE_NON_RECUE','SESSION_LIEU_DATE_FACTURE_REGLEE','SESSION_LIEU_DATE_SALLE_GRATUITE') DEFAULT NULL,
    PRIMARY KEY (`session_lieu_date_id`),
    KEY `FK7a828qy78bqy9jy15sberjst7` (`session_formateur_id`),
    KEY `FKn03bael40mntss0ajg28l3j5i` (`session_formation_id`),
    KEY `FKpy0ogqif9iq75mn5n6phn5yu2` (`session_salle_id`),
    CONSTRAINT `FK7a828qy78bqy9jy15sberjst7` FOREIGN KEY (`session_formateur_id`) REFERENCES `session_formateur` (`session_formateur_id`),
    CONSTRAINT `FKn03bael40mntss0ajg28l3j5i` FOREIGN KEY (`session_formation_id`) REFERENCES `session_formation` (`session_formation_id`),
    CONSTRAINT `FKpy0ogqif9iq75mn5n6phn5yu2` FOREIGN KEY (`session_salle_id`) REFERENCES `session_salle` (`session_salle_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;



-- Table structure for table `session_apprenant`
CREATE TABLE `session_apprenant`
(
    `apprenant_id`                  bigint      NOT NULL,
    `session_apprenant_id`          bigint      NOT NULL AUTO_INCREMENT,
    `session_formation_id`          bigint      NOT NULL,
    `mode_reception_inscription`    varchar(20) NOT NULL,
    `commentaire_session_apprenant` varchar(2000)                                                DEFAULT NULL,
    `statut_session_apprenant`      enum ('ABSENT_SESSION','INSCRIT_SUR_YODA','PRESENT_SESSION') DEFAULT NULL,
    PRIMARY KEY (`session_apprenant_id`),
    KEY `FKogn90bm5r23yehc52jrhiv1jb` (`apprenant_id`),
    KEY `FKx0kgm1iiuav361x5jr1xg4qk` (`session_formation_id`),
    CONSTRAINT `FKogn90bm5r23yehc52jrhiv1jb` FOREIGN KEY (`apprenant_id`) REFERENCES `apprenant` (`personne_id`),
    CONSTRAINT `FKx0kgm1iiuav361x5jr1xg4qk` FOREIGN KEY (`session_formation_id`) REFERENCES `session_formation` (`session_formation_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;



-- Table structure for table `session_apprenant_document`
CREATE TABLE `session_apprenant_document`
(
    `date_session_apprenant_document`    date                                                                   DEFAULT NULL,
    `categorie_document_id`              bigint       NOT NULL,
    `session_apprenant_document_id`      bigint       NOT NULL AUTO_INCREMENT,
    `session_apprenant_id`               bigint       NOT NULL,
    `nom_session_apprenant_document`     varchar(200) NOT NULL,
    `fichier_session_apprenant_document` varchar(2000)                                                          DEFAULT NULL,
    `statut_session_apprenant_document`  enum ('DOCUMENT_CONFORME','DOCUMENT_NON_CONFORME','DOCUMENT_NON_RECU') DEFAULT NULL,
    PRIMARY KEY (`session_apprenant_document_id`),
    KEY `FK1c191qeng0jytckbxowc8c4ad` (`categorie_document_id`),
    KEY `FKti42spbo8nmixnbsa2xwefmo3` (`session_apprenant_id`),
    CONSTRAINT `FK1c191qeng0jytckbxowc8c4ad` FOREIGN KEY (`categorie_document_id`) REFERENCES `categorie_document` (`categorie_document_id`),
    CONSTRAINT `FKti42spbo8nmixnbsa2xwefmo3` FOREIGN KEY (`session_apprenant_id`) REFERENCES `session_apprenant` (`session_apprenant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


-- Table structure for table `type_formation_dispensee`
CREATE TABLE `type_formation_dispensee`
(
    `formateur_id`      bigint NOT NULL,
    `type_formation_id` bigint NOT NULL,
    PRIMARY KEY (`formateur_id`, `type_formation_id`),
    KEY `FK4855k25pswbxdani1rvnb753l` (`type_formation_id`),
    CONSTRAINT `FK4855k25pswbxdani1rvnb753l` FOREIGN KEY (`type_formation_id`) REFERENCES `type_formation` (`type_formation_id`),
    CONSTRAINT `FKtq3qve0g3e2hbnm5xt59bccv7` FOREIGN KEY (`formateur_id`) REFERENCES `formateur` (`personne_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


-- Table structure for table `type_formation_suivie`
CREATE TABLE `type_formation_suivie`
(
    `apprenant_id`      bigint NOT NULL,
    `type_formation_id` bigint NOT NULL,
    PRIMARY KEY (`apprenant_id`, `type_formation_id`),
    KEY `FKf42cawlruv5bd2k06mom3e5ap` (`type_formation_id`),
    CONSTRAINT `FKf42cawlruv5bd2k06mom3e5ap` FOREIGN KEY (`type_formation_id`) REFERENCES `type_formation` (`type_formation_id`),
    CONSTRAINT `FKh6rl7v2d9dlx2k5fb8g0obvvt` FOREIGN KEY (`apprenant_id`) REFERENCES `apprenant` (`personne_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;



-- Table structure for table `salarie`
CREATE TABLE `salarie`
(
    `statut_inscription` bit(1)      NOT NULL,
    `couleur_salarie`    varchar(7)  NOT NULL,
    `personne_id`        bigint      NOT NULL,
    `role_salarie`       varchar(50) NOT NULL,
    `mot_de_passe`       varchar(68) DEFAULT NULL,
    PRIMARY KEY (`personne_id`),
    CONSTRAINT `FKgl3vdlnyaplcd0peltpxc6m3c` FOREIGN KEY (`personne_id`) REFERENCES `personne` (`personne_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


-- Table structure for table `facture`
CREATE TABLE `facture`
(
    `date_envoi_facture`     date   DEFAULT NULL,
    `date_reglement_facture` date   DEFAULT NULL,
    `fichier_facture`        date   DEFAULT NULL,
    `montant_facture`        float        NOT NULL,
    `facture_id`             bigint       NOT NULL AUTO_INCREMENT,
    `formateur_id`           bigint DEFAULT NULL,
    `salle_id`               bigint DEFAULT NULL,
    `num_facture`            varchar(50)  NOT NULL,
    `type_facture`           varchar(50)  NOT NULL,
    `libelle_facture`        varchar(120) NOT NULL,
    PRIMARY KEY (`facture_id`),
    KEY `FKdu6j5bvic3k3ps46241vjqo53` (`formateur_id`),
    KEY `FK1oqxy6ryhfs1uf4r1ua60bgrg` (`salle_id`),
    CONSTRAINT `FK1oqxy6ryhfs1uf4r1ua60bgrg` FOREIGN KEY (`salle_id`) REFERENCES `salle` (`salle_id`),
    CONSTRAINT `FKdu6j5bvic3k3ps46241vjqo53` FOREIGN KEY (`formateur_id`) REFERENCES `formateur` (`personne_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;