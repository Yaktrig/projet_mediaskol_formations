package fr.mediaskol.projet.association;


import fr.mediaskol.projet.bo.formation.Formation;
import fr.mediaskol.projet.bo.SessionFormation.SessionFormation;
import fr.mediaskol.projet.dal.FormationRepository;
import fr.mediaskol.projet.dal.SessionFormationRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Test unitaire qui permet de valider l'association ManyToOne
 * Entre les entités SessionFormation et Formation
 * Ici, nous sommes dans le cadre d'une formation en distanciel, département non nécessaire
 */

// Configure un contexte Spring Boot limité à la couche JPA
@Slf4j
@DataJpaTest
public class TestManyToOneSessionFDFormation {

    // Permet des opérations avancées sur l'EntityManager pour les tests
    @Autowired
    private TestEntityManager entityManager;

    // Comme il n'y a pas de cascade dans notre @ManyToOne de SessionFormation vers Formation
    // on doit appeler le repository pour créer ici nos départements
    @Autowired
    FormationRepository formationRepository;

    @Autowired
    SessionFormationRepository sessionFormationRepository;

    private Formation sauveteurSecouristeInitial;
    private Formation sauveteurSecouristeRecyclage;
    private Formation comprendreLesEmotions;

    @BeforeEach
    public void initFormations() {
        sauveteurSecouristeInitial = Formation
                .builder()
                .themeFormation("MISST")
                .libelleFormation("Sauveteur secouriste du travail (SST initial)")
                .build();

        sauveteurSecouristeRecyclage = Formation
                .builder()
                .themeFormation("MIMACSST")
                .libelleFormation("Recyclage sauveteur secouriste du travail")
                .build();

        comprendreLesEmotions = Formation
                .builder()
                .themeFormation("MICE")
                .libelleFormation("Comprendre les émotions de l'enfant pour mieux l'accompagner au quotidien")
                .build();



        formationRepository.save(sauveteurSecouristeInitial);
        formationRepository.save(sauveteurSecouristeRecyclage);
        formationRepository.save(comprendreLesEmotions);
    }

    // Sauvegarde d'une session de formation et de sa formation
    @Test
    public void test_save_session_distanciel(){

        // Création d'une nouvelle session de formation avec le builder Lombok
        final SessionFormation sessionMICE = SessionFormation
                .builder()
                .noYoda("123456")
                .libelleSessionFormation("MICE24052025")
                .build();

        // Association ManyToOne entre la session et la formation
        sessionMICE.setFormation(comprendreLesEmotions);

        // Sauvegarde de la session en base via le repository
        final SessionFormation sessionMICEDB = sessionFormationRepository.save(sessionMICE);

        // Log pour visualiser l'objet persisté
        log.info(sessionMICEDB.toString());

        // Vérification de la cascade de l'association
        // Vérifie que l'objet retourné n'est pas null
        assertThat(sessionMICEDB.getIdSessionFormation()).isGreaterThan(0);
        assertThat(sessionMICEDB.getFormation()).isNotNull();
        assertThat(sessionMICEDB.getFormation()).isEqualTo(comprendreLesEmotions);

    }

    //
    @Test
    public void test_find_all(){

        // Récupération des données de la méthode jeuDeDonnees()
        List<SessionFormation> sessionFormations = jeuDeDonnees();

        // Sauvegarde du jeu de données dans la base
        sessionFormations.forEach(session -> {
            entityManager.persist(session);
            assertThat(session.getIdSessionFormation()).isGreaterThan(0);
        });

        // Vérifie l'identifiant des sessions
        final List<SessionFormation> sessionFormationDB = sessionFormationRepository.findAll();
        sessionFormationDB.forEach(session -> {
            assertThat(session.getIdSessionFormation()).isGreaterThan(0);

            // Vérification de la formation
            assertThat(session.getFormation()).isNotNull();
        });
    }


    @Test
    public void test_delete_session_distanciel(){

        // Création d'une nouvelle session avec le builder Lombok
        final SessionFormation sessionMICE = SessionFormation
                .builder()
                .noYoda("123456")
                .libelleSessionFormation("MICE24052025")
                .build();

        // Association ManyToOne entre la session et la formation
        sessionMICE.setFormation(sauveteurSecouristeRecyclage);

        // Persistance de la session dans la base de test
        final SessionFormation sessionMICEDB = sessionFormationRepository.save(sessionMICE);
        entityManager.flush();

        // Vérification s'il y a au moins un identifiant dans SessionFormation, s'il n'est pas null,
        // et si sa formation est égale à la formation sauveteurSecouristeRecyclage
        assertThat(sessionMICEDB.getIdSessionFormation()).isGreaterThan(0);
        assertThat(sessionMICEDB.getFormation()).isNotNull();
        assertThat(sessionMICEDB.getFormation()).isEqualTo(sauveteurSecouristeRecyclage);

        // Suppression de la session de formation MICE
        sessionFormationRepository.delete(sessionMICEDB);

        // Vérifie que l'entité SessionFormation n'est plus présente en base (doit être null)
        SessionFormation sessionMICEDB2 = entityManager.find(SessionFormation.class, sessionMICEDB.getIdSessionFormation());
        assertNull(sessionMICEDB2);


        // Vérifie que les départements associés existent toujours en base (pas de suppression en cascade)
        List<Formation> formations = formationRepository.findAll();
        assertThat(formations).isNotNull();
        assertThat(formations).isNotEmpty();
        assertThat(formations.size()).isEqualTo(3);


    }


    // Création d'un jeu de données de sessions de formations
    private List<SessionFormation> jeuDeDonnees() {
        List<SessionFormation> sessionsFormation = new ArrayList<>();
        sessionsFormation.add(SessionFormation.builder()
                .formation(sauveteurSecouristeInitial)
                .noYoda("123456")
                .libelleSessionFormation("MICE24052025")
                .build());
        sessionsFormation.add(SessionFormation.builder()
                .formation(sauveteurSecouristeRecyclage)
                .noYoda("234567")
                .libelleSessionFormation("MICE20092025")
                .build());
        sessionsFormation.add(SessionFormation.builder()
                .formation(comprendreLesEmotions)
                .noYoda("345678")
                .libelleSessionFormation("MISST24052025")
                .build());

        return sessionsFormation;
    }

}


