package fr.mediaskol.projet.association;



import fr.mediaskol.projet.bo.departement.Departement;
import fr.mediaskol.projet.bo.sessionFormation.SessionFormationPresentiel;
import fr.mediaskol.projet.dal.DepartementRepository;
import fr.mediaskol.projet.dal.SessionFormationPresentielRepository;
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
 * Entre les entités SessionFormationPresentiel et Departement
 */

// Configure un contexte Spring Boot limité à la couche JPA
@Slf4j
@DataJpaTest
public class TestManyToOneSessionFPDepartement {

    // Permet des opérations avancées sur l'EntityManager pour les tests
    @Autowired
    private TestEntityManager entityManager;

    // Comme il n'y a pas de cascade dans notre @ManyToOne de SessionFormationPresentiel vers Departement
    // on doit appeler le repository pour créer ici nos départements
    @Autowired
    DepartementRepository departementRepository;

    @Autowired
    SessionFormationPresentielRepository sessionFormationPresentielRepository;

    private Departement cotesdarmor;
    private Departement finistere;
    private Departement illeetvilaine;
    private Departement morbihan;

    @BeforeEach
    public void initDepartement() {
        cotesdarmor = Departement
                .builder()
                .idDepartement(22L)
                .nomDepartement("Côtes d'Armor")
                .couleurDepartement("#9AC8EB")
                .region("Bretagne")
                .build();

        finistere = Departement
                .builder()
                .idDepartement(29L)
                .nomDepartement("Finistère")
                .couleurDepartement("#CE6A6B")
                .region("Bretagne")
                .build();

        illeetvilaine = Departement
                .builder()
                .idDepartement(35L)
                .nomDepartement("Ille et Vilaine")
                .couleurDepartement("#F7F6CF")
                .region("Bretagne")
                .build();

        morbihan = Departement
                .builder()
                .idDepartement(56L)
                .nomDepartement("Morbihan")
                .couleurDepartement("#D5CD90")
                .region("Bretagne")
                .build();

        departementRepository.save(cotesdarmor);
        departementRepository.save(finistere);
        departementRepository.save(illeetvilaine);
        departementRepository.save(morbihan);

    }

    // Sauvegarde d'une session de formation et de son département
    @Test
    public void test_save_session_presentiel(){

        // Création d'une nouvelle session de formation avec le builder Lombok
        final SessionFormationPresentiel sessionMICE = SessionFormationPresentiel
                .builder()
                .noYoda(123456L)
                .libelleSessionFormation("MICE24052025")
                .build();

        // Association ManyToOne entre la session et le département
        sessionMICE.setDepartement(illeetvilaine);

        // Sauvegarde de la session en base via le repository
        final SessionFormationPresentiel sessionMICEDB = sessionFormationPresentielRepository.save(sessionMICE);

        // Log pour visualiser l'objet persisté
        log.info(sessionMICEDB.toString());

        // Vérification de la cascade de l'association
        // Vérifie que l'objet retourné n'est pas null
        assertThat(sessionMICEDB.getIdSessionFormation()).isGreaterThan(0);
        assertThat(sessionMICEDB.getDepartement()).isNotNull();
        assertThat(sessionMICEDB.getDepartement()).isEqualTo(illeetvilaine);

    }

    //
    @Test
    public void test_find_all(){

        // Récupération des données de la méthode jeuDeDonnees()
        List<SessionFormationPresentiel> sessionFormationPresentiels = jeuDeDonnees();

        // Sauvegarde du jeu de données dans la base
        sessionFormationPresentiels.forEach(session -> {
            entityManager.persist(session);
            assertThat(session.getIdSessionFormation()).isGreaterThan(0);
        });

        // Vérifie l'identifiant des sessions
        final List<SessionFormationPresentiel> sessionFormationPresentielsDB = sessionFormationPresentielRepository.findAll();
        sessionFormationPresentielsDB.forEach(session -> {
            assertThat(session.getIdSessionFormation()).isGreaterThan(0);

            // Vérification du département
            assertThat(session.getDepartement()).isNotNull();
        });
    }

    @Test
    public void test_delete_session_presentiel(){

        // Création d'une nouvelle session avec le builder Lombok
        final SessionFormationPresentiel sessionMICE = SessionFormationPresentiel
                .builder()
                .noYoda(123456L)
                .libelleSessionFormation("MICE24052025")
                .build();

        // Association ManyToOne entre la session et le département
        sessionMICE.setDepartement(illeetvilaine);

        // Persistance de la session dans la base de test
        final SessionFormationPresentiel sessionMICEDB = sessionFormationPresentielRepository.save(sessionMICE);
        entityManager.flush();

        // Vérification s'il y a au moins un identifiant dans SessionFormationPresentiel, s'il n'est pas null,
        // et si son département est égal au département illeetvilaine
        assertThat(sessionMICEDB.getIdSessionFormation()).isGreaterThan(0);
        assertThat(sessionMICEDB.getDepartement()).isNotNull();
        assertThat(sessionMICEDB.getDepartement()).isEqualTo(illeetvilaine);

        // Suppression de la session de formation MICE
        sessionFormationPresentielRepository.delete(sessionMICEDB);

        // Vérifie que l'entité SessionFormationPresentiel n'est plus présente en base (doit être null)
        SessionFormationPresentiel sessionMICEDB2 = entityManager.find(SessionFormationPresentiel.class, sessionMICEDB.getIdSessionFormation());
        assertNull(sessionMICEDB2);


        // Vérifie que les départements associés existent toujours en base (pas de suppression en cascade)
        List<Departement> departements = departementRepository.findAll();
        assertThat(departements).isNotNull();
        assertThat(departements).isNotEmpty();
        assertThat(departements.size()).isEqualTo(4);


    }


    // Création d'un jeu de données de sessions de formations en présentiel
    private List<SessionFormationPresentiel> jeuDeDonnees() {
        List<SessionFormationPresentiel> sessionsFormationPresentiel = new ArrayList<>();
        sessionsFormationPresentiel.add(SessionFormationPresentiel.builder()
                .departement(illeetvilaine)
                .noYoda(123456L)
                .libelleSessionFormation("MICE24052025")
                .build());
        sessionsFormationPresentiel.add(SessionFormationPresentiel.builder()
                .departement(morbihan)
                .noYoda(234567L)
                .libelleSessionFormation("MICE20092025")
                .build());
        sessionsFormationPresentiel.add(SessionFormationPresentiel.builder()
                .departement(cotesdarmor)
                .noYoda(345678L)
                .libelleSessionFormation("MISST24052025")
                .build());
        return sessionsFormationPresentiel;
    }

}
