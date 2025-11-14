package fr.mediaskol.projet.association;


import fr.mediaskol.projet.bo.departement.Departement;
import fr.mediaskol.projet.bo.sessionFormation.SessionFormation;
import fr.mediaskol.projet.bo.formation.Formation;
import fr.mediaskol.projet.bo.formation.TypeFormation;
import fr.mediaskol.projet.bo.sessionFormation.SessionFormationPresentiel;
import fr.mediaskol.projet.dal.departement.DepartementRepository;
import fr.mediaskol.projet.dal.formation.FormationRepository;
import fr.mediaskol.projet.dal.sessionFormation.SessionFOPRepository;
import fr.mediaskol.projet.dal.sessionFormation.SessionFormationRepository;
import fr.mediaskol.projet.dal.formation.TypeFormationRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Test unitaire qui permet de valider l'association ManyToOne
 * Entre les entités SessionFormation et Departement
 * Ici, nous sommes dans le cadre d'une formation présentielle.
 * On n'oblige pas à associer une formation à la session.
 */

@Slf4j
@DataJpaTest
public class SessionFormationDepartementRelationTest {

    // Permet des opérations avancées sur l'EntityManager pour les tests
    @Autowired
    private TestEntityManager entityManager;

    // Comme il n'y a pas de cascade dans notre @ManyToOne de SessionFormation vers Departement
    // on doit appeler le repository pour créer ici nos départements
    @Autowired
    DepartementRepository departementRepository;

    @Autowired
    SessionFOPRepository sessionFopRepository;

    // Repository pour effectuer des opérations CRUD sur l'entité Formation
    @Autowired
    private FormationRepository formationRepository;

    private Formation formationMISST;

    private TypeFormation presentiel;

    @Autowired
    private TypeFormationRepository typeFormationRepository;

    private Departement cotesdarmor;
    private Departement finistere;
    private Departement illeetvilaine;
    private Departement morbihan;

    @BeforeEach
    public void initDepartementFormation() {


        // Création d'un type de formation avec le builder Lombok
        presentiel = TypeFormation
                .builder()
                .libelleTypeFormation("Présentiel")
                .build();

        // Persiste le type de formation dans la base de test
        typeFormationRepository.save(presentiel);

        // Création d'une formation avec le builder Lombok
        formationMISST = Formation
                .builder()
                .themeFormation("MISST")
                .libelleFormation("Sauveteur secouriste du travail (SST initial)")
                .typeFormation(presentiel)
                .build();

        // Persiste la formation dans la base de test
        formationRepository.save(formationMISST);


        // Création des 4 départements avec le builder Lombok
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

        // Persistence dans la base de test des départements
        departementRepository.save(cotesdarmor);
        departementRepository.save(finistere);
        departementRepository.save(illeetvilaine);
        departementRepository.save(morbihan);

    }

    /**
     * Sauvegarde d'une session de formation en présentiel et de son département
     */

    @Test
    public void test_save_session_formation() {

        // Création d'une nouvelle session de formation avec le builder Lombok
        final SessionFormationPresentiel sessionMICE = SessionFormationPresentiel
                .builder()
                .noYoda("123456")
                .libelleSessionFormation("MICE24052025")
                .dateDebutSession(LocalDate.parse("2025-01-01"))
                .nbHeureSession(21)
                .formation(formationMISST)
                .build();

        // Association ManyToOne entre la session et le département
        sessionMICE.setDepartement(illeetvilaine);

        // Sauvegarde de la session en base via le repository
        final SessionFormationPresentiel sessionFopMICEDB = sessionFopRepository.save(sessionMICE);

        // Log pour visualiser l'objet persisté
        log.info(sessionFopMICEDB.toString());

        // Vérification de la cascade de l'association
        // Vérifie que l'objet retourné n'est pas null
        assertThat(sessionFopMICEDB.getIdSessionFormation()).isGreaterThan(0);
        assertThat(sessionFopMICEDB.getDepartement()).isNotNull();
        assertThat(sessionFopMICEDB.getDepartement()).isEqualTo(illeetvilaine);
    }

    //
    @Test
    public void test_find_all() {

        // Récupération des données de la méthode jeuDeDonnees()
        List<SessionFormationPresentiel> sessionFop = jeuDeDonnees();

        // Sauvegarde du jeu de données dans la base
        sessionFop.forEach(session -> {
            sessionFopRepository.save(session);
            assertThat(session.getIdSessionFormation()).isGreaterThan(0);
        });

        // Vérifie l'identifiant des sessions
        final List<SessionFormationPresentiel> sessionFopDB = sessionFopRepository.findAll();
        sessionFopDB.forEach(session -> {
            assertThat(session.getIdSessionFormation()).isGreaterThan(0);

            // Vérification du département
            assertThat(session.getDepartement()).isNotNull();
        });
    }

    @Test
    public void test_delete_session_formation() {

        // Création d'une nouvelle session avec le builder Lombok
        final SessionFormationPresentiel sessionMICE = SessionFormationPresentiel
                .builder()
                .noYoda("123456")
                .libelleSessionFormation("MICE24052025")
                .dateDebutSession(LocalDate.parse("2025-01-01"))
                .nbHeureSession(21)
                .formation(formationMISST)
                .build();

        // Association ManyToOne entre la session et le département
        sessionMICE.setDepartement(illeetvilaine);

        // Persistance de la session dans la base de test
        final SessionFormationPresentiel sessionMICEDB = sessionFopRepository.save(sessionMICE);


        // Vérification s'il y a au moins un identifiant dans SessionFormation, s'il n'est pas null,
        // et si son département est égal au département illeetvilaine
        assertThat(sessionMICEDB.getIdSessionFormation()).isGreaterThan(0);
        assertThat(sessionMICEDB.getDepartement()).isNotNull();
        assertThat(sessionMICEDB.getDepartement()).isEqualTo(illeetvilaine);

        // Suppression de la session de formation MICE
        sessionFopRepository.delete(sessionMICEDB);

        // Vérifie que l'entité SessionFormation n'est plus présente en base (doit être null)
        SessionFormation sessionMICEDB2 = entityManager.find(SessionFormation.class, sessionMICEDB.getIdSessionFormation());
        assertNull(sessionMICEDB2);


        // Vérifie que les départements associés existent toujours en base (pas de suppression en cascade)
        List<Departement> departements = departementRepository.findAll();
        assertThat(departements).isNotNull();
        assertThat(departements).isNotEmpty();
        assertThat(departements.size()).isEqualTo(4);


    }


    // Création d'un jeu de données de sessions de formations
    private List<SessionFormationPresentiel> jeuDeDonnees() {

        List<SessionFormationPresentiel> sessionsFop = new ArrayList<>();

        sessionsFop.add(SessionFormationPresentiel
                .builder()
                .departement(illeetvilaine)
                .noYoda("123456")
                .libelleSessionFormation("MICE24052025")
                .dateDebutSession(LocalDate.parse("2025-01-01"))
                .nbHeureSession(14)
                .formation(formationMISST)
                .build());

        sessionsFop.add(SessionFormationPresentiel
                .builder()
                .departement(morbihan)
                .noYoda("234567")
                .libelleSessionFormation("MICE20092025")
                .dateDebutSession(LocalDate.parse("2025-02-01"))
                .nbHeureSession(14)
                .formation(formationMISST)
                .build());

        sessionsFop.add(SessionFormationPresentiel
                .builder()
                .departement(cotesdarmor)
                .noYoda("345678")
                .libelleSessionFormation("MISST24052025")
                .dateDebutSession(LocalDate.parse("2025-03-01"))
                .nbHeureSession(14)
                .formation(formationMISST)
                .build());

        return sessionsFop;
    }

}
