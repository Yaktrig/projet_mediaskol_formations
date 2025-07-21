package fr.mediaskol.projet.association;


import fr.mediaskol.projet.bo.sessionFormation.FinSessionFormation;
import fr.mediaskol.projet.bo.formation.Formation;
import fr.mediaskol.projet.bo.sessionFormation.SessionFormation;
import fr.mediaskol.projet.bo.formation.TypeFormation;
import fr.mediaskol.projet.dal.formation.FormationRepository;
import fr.mediaskol.projet.dal.sessionFormation.FinSessionFormationRepository;
import fr.mediaskol.projet.dal.sessionFormation.SessionFormationRepository;
import fr.mediaskol.projet.dal.formation.TypeFormationRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Test unitaire qui permet de valider l'association OneToOne
 * Entre les entités SessionFormation et FinSessionFormation
 * Dans le cadre d'une formation en distanciel, le département n'est pas nécessaire.
 */

// Configure un contexte Spring Boot limité à la couche JPA
@Slf4j
@DataJpaTest
public class SessionFormationFinFormationRelationTest {

    // Permet des opérations avancées sur l'EntityManager pour les tests
    @Autowired
    private TestEntityManager entityManager;

    // Comme il n'y a pas de cascade dans notre @ManyToOne de SessionFormation vers Formation
    // on doit appeler le repository pour créer ici nos départements
    @Autowired
    FormationRepository formationRepository;

    @Autowired
    SessionFormationRepository sessionFormationRepository;

    private Formation comprendreLesEmotions;

    private TypeFormation distanciel;



    @Autowired
    private TypeFormationRepository typeFormationRepository;

    private FinSessionFormation finSessionFormation;

    @Autowired
    private FinSessionFormationRepository finSessionFormationRepository;

    @BeforeEach
    public void initFormations() {

        distanciel = TypeFormation
                .builder()
                .libelleTypeFormation("Distanciel")
                .build();

        typeFormationRepository.save(distanciel);

        comprendreLesEmotions = Formation
                .builder()
                .themeFormation("MICE")
                .libelleFormation("Comprendre les émotions de l'enfant pour mieux l'accompagner au quotidien")
                .typeFormation(distanciel)
                .build();

        formationRepository.save(comprendreLesEmotions);


        // Création d'une fin de formation avec le builder Lombok
        finSessionFormation = FinSessionFormation
                .builder()
                .statutYodaFinSessionFormation("FF")
                .dateLimiteYodaFinSessionFormation(LocalDate.parse("2025-10-01"))
                .build();

        // Persistence de la session de fin de formation
        finSessionFormationRepository.save(finSessionFormation);

    }

    // Sauvegarde d'une session de formation et de sa formation
    @Test
    public void test_save_session() {

        // Création d'une nouvelle session de formation avec le builder Lombok
        final SessionFormation sessionMICE = SessionFormation
                .builder()
                .noYoda("123456")
                .libelleSessionFormation("MICE24052025")
                .dateDebutSession(LocalDate.parse("2025-01-01"))
                .nbHeureSession(21)
                .formation(comprendreLesEmotions)
                .build();

        // Association OneToOne entre la session de formation et la fin de session de formation
        sessionMICE.setFinSessionFormation(finSessionFormation);

        // Sauvegarde de la session en base via le repository
        final SessionFormation sessionMICEDB = sessionFormationRepository.save(sessionMICE);

        // Log pour visualiser l'objet persisté
        log.info(sessionMICEDB.toString());

        // Vérification de la cascade de l'association
        // Vérifie que l'objet retourné n'est pas nul
        assertThat(sessionMICE.getIdSessionFormation()).isGreaterThan(0);
        assertThat(finSessionFormation.getIdFinSessionFormation()).isGreaterThan(0);

    }


    @Test
    public void test_delete_session() {

        // Création d'une nouvelle session avec le builder Lombok
        final SessionFormation sessionMICE = SessionFormation
                .builder()
                .noYoda("123456")
                .libelleSessionFormation("MICE24052025")
                .dateDebutSession(LocalDate.parse("2025-01-01"))
                .nbHeureSession(21)
                .formation(comprendreLesEmotions)
                .build();

        // Association ManyToOne entre la session de formation et la fin de session de formation
        sessionMICE.setFinSessionFormation(finSessionFormation);

        // Persistance de la session dans la base de test
        final SessionFormation sessionMICEDB = sessionFormationRepository.save(sessionMICE);

        // Vérification s'il y a au moins un identifiant dans SessionFormation, s'il n'est pas nul,
        assertThat(sessionMICE.getIdSessionFormation()).isGreaterThan(0);
        assertThat(finSessionFormation.getIdFinSessionFormation()).isGreaterThan(0);

        // Suppression de la session de formation MICE
        sessionFormationRepository.delete(sessionMICEDB);

        // Vérifie que l'entité SessionFormation n'est plus présente en base (doit être nul)
        SessionFormation sessionMICEDB2 = entityManager.find(SessionFormation.class, sessionMICEDB.getIdSessionFormation());
        assertNull(sessionMICEDB2);


        // Vérifie que la fin de session de formation n'existe plus également en base
        FinSessionFormation finSessionFormationDB = entityManager.find(FinSessionFormation.class, finSessionFormation.getIdFinSessionFormation());
        assertNull(finSessionFormationDB);


    }


    // Test du paramètre orphanRemoval
    // On constatera un échec si on n'utilise pas le paramètre orphan dans les paramètres de l'entité SessionFormation
    @Test
    public void test_orphanRemoval(){

        // Création d'une nouvelle session avec le builder Lombok
        final SessionFormation sessionMICE = SessionFormation
                .builder()
                .noYoda("123456")
                .libelleSessionFormation("MICE24052025")
                .dateDebutSession(LocalDate.parse("2025-01-01"))
                .nbHeureSession(21)
                .formation(comprendreLesEmotions)
                .build();

        // Association ManyToOne entre la session de formation et la fin de session de formation
        sessionMICE.setFinSessionFormation(finSessionFormation);

        // Persistance de la session dans la base de test
        final SessionFormation sessionMICEDB = sessionFormationRepository.save(sessionMICE);

        // Vérification s'il y a au moins un identifiant dans SessionFormation, s'il n'est pas nul,
        assertThat(sessionMICEDB.getIdSessionFormation()).isGreaterThan(0);
        assertThat(finSessionFormation.getIdFinSessionFormation()).isGreaterThan(0);


        // Supprimer le lien entre l'entité SessionFormation et l'entité FinSessionFormation
        sessionMICE.setFinSessionFormation(null);

        // Suppression de la session de formation MICE
        sessionFormationRepository.delete(sessionMICE);

        // Vérification que l'entité a été supprimée
        SessionFormation sessionMICEDB2 = entityManager.find(SessionFormation.class, sessionMICE.getIdSessionFormation());
        assertNull(sessionMICEDB2);

        FinSessionFormation finSessionFormationDB2 = entityManager.find(FinSessionFormation.class, finSessionFormation.getIdFinSessionFormation());
        assertNull(finSessionFormationDB2);

    }

}


