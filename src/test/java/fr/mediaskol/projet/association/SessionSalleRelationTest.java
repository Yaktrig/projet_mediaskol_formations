package fr.mediaskol.projet.association;

import fr.mediaskol.projet.bo.sessionFormation.SessionFormation;
import fr.mediaskol.projet.bo.formation.Formation;
import fr.mediaskol.projet.bo.formation.TypeFormation;
import fr.mediaskol.projet.bo.salle.Salle;
import fr.mediaskol.projet.bo.salle.SessionSalle;
import fr.mediaskol.projet.bo.salle.StatutSessionSalle;
import fr.mediaskol.projet.dal.formation.FormationRepository;
import fr.mediaskol.projet.dal.formation.TypeFormationRepository;
import fr.mediaskol.projet.dal.sessionFormation.SessionFormationRepository;
import fr.mediaskol.projet.dal.salle.SalleRepository;
import fr.mediaskol.projet.dal.salle.SessionSalleRepository;
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
 * Entre SessionSalle et Salle
 * Entre SessionSalle et SessionFormation
 */


// Configure un contexte Spring Boot limité à la couche JPA
@Slf4j
@DataJpaTest
public class SessionSalleRelationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SessionFormationRepository sessionFormationRepository;

    @Autowired
    private SalleRepository salleRepository;

    @Autowired
    private SessionSalleRepository sessionSalleRepository;


    private SessionFormation sessionMISST;

    // Repository pour effectuer des opérations CRUD sur l'entité Formation
    @Autowired
    private FormationRepository formationRepository;

    private Formation formationMISST;

    private TypeFormation presentiel;

    @Autowired
    private TypeFormationRepository typeFormationRepository;

    private Salle sallePlovan1;

    private Salle sallePlovan2;

    @BeforeEach
    public void init() {

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

        // Création d'une nouvelle session de formation avec le builder Lombok
        sessionMISST = SessionFormation
                .builder()
                .noYoda("123456")
                .dateDebutSession(LocalDate.parse("2026-01-07"))
                .nbHeureSession(14)
                .libelleSessionFormation("MISST07012026")
                .formation(formationMISST)
                .build();

        // Persistence de la session de formation dans la base
        sessionFormationRepository.save(sessionMISST);

        // Création d'une salle avec le builder Lombok
        sallePlovan1 = Salle
                .builder()
                .nomSalle("Salle polyvalente")
                .cleSalle(true)
                .build();

        // Sauvegarde de la salle dans la base test
        salleRepository.save(sallePlovan1);

        // Création d'une 2ème salle avec le builder Lombok
        sallePlovan2 = Salle
                .builder()
                .nomSalle("Auberge Bigoudène")
                .cleSalle(false)
                .build();

        // Sauvegarde de la seconde salle dans la base test
        salleRepository.save(sallePlovan2);
    }


    @Test
    public void test_save_session_salle() {

        // Création d'une session salle avec le builder Lombock
        final SessionSalle sessionSallePlovan1 = SessionSalle
                .builder()
                .statutSessionSalle(StatutSessionSalle.SESSION_SALLE_VALIDEE)
                .build();

        // Associations ManyToOne
        sessionSallePlovan1.setSalle(sallePlovan1);
        sessionSallePlovan1.setSessionFormation(sessionMISST);

        // Sauvegarde de la session salle en base test via le repository
        final SessionSalle sessionSallePlovanDB = sessionSalleRepository.save(sessionSallePlovan1);

        // Vérifie que l'objet retourné n'est pas null
        assertThat(sessionSallePlovanDB.getIdSessionSalle()).isGreaterThan(0);
        // Vérification si la salle existe
        assertThat(sessionSallePlovanDB.getSalle()).isNotNull();
        assertThat(sessionSallePlovanDB.getSalle()).isEqualTo(sallePlovan1);
        // Vérification si la session de formation existe
        assertThat(sessionSallePlovanDB.getSessionFormation()).isNotNull();
        assertThat(sessionSallePlovanDB.getSessionFormation()).isEqualTo(sessionMISST);

        // Log pour visualiser l'objet persité
        log.info(sessionSallePlovanDB.toString());


        // Création d'une seconde session salle avec le builder Lombock
        final SessionSalle sessionSallePlovan2 = SessionSalle
                .builder()
                .statutSessionSalle(StatutSessionSalle.SESSION_SALLE_VALIDEE)
                .build();

        // Associations ManyToOne
        sessionSallePlovan2.setSalle(sallePlovan2);
        sessionSallePlovan2.setSessionFormation(sessionMISST);

        // Sauvegarde de la session salle en base test via le repository
        final SessionSalle sessionSallePlovanDB2 = sessionSalleRepository.save(sessionSallePlovan2);

        // Vérifie que l'objet retourné n'est pas null
        assertThat(sessionSallePlovanDB2.getIdSessionSalle()).isGreaterThan(0);
        // Vérification si la formatrice existe
        assertThat(sessionSallePlovanDB2.getSalle()).isNotNull();
        assertThat(sessionSallePlovanDB2.getSalle()).isEqualTo(sallePlovan2);
        // Vérification si la session de formation existe
        assertThat(sessionSallePlovanDB2.getSessionFormation()).isNotNull();
        assertThat(sessionSallePlovanDB2.getSessionFormation()).isEqualTo(sessionMISST);

        // Log pour visualiser l'objet persité
        log.info(sessionSallePlovanDB2.toString());
    }


    // Retrouver toutes les sessions salles d'une session de formation
    @Test
    public void test_find_all() {

        // Liste des sessions apprenants
        List<SessionSalle> listeSessionSalle = jeuDeDonnees();

        // Sauvegarder le jeu de données dans la base test
        listeSessionSalle.forEach(sessionSalle -> {
            sessionSalleRepository.save(sessionSalle);
            assertThat(sessionSalle.getIdSessionSalle()).isGreaterThan(0);
        });

        // Vérification de l'identifiant des sessions salles
        final List<SessionSalle> listeSessionSalleDB = sessionSalleRepository.findAll();
        listeSessionSalleDB.forEach(sessionSalle -> {
            assertThat(sessionSalle.getIdSessionSalle()).isGreaterThan(0);

            // vérification de la  salle
            assertThat(sessionSalle.getSalle()).isNotNull();

            // vérification de la session de formation
            assertThat(sessionSalle.getSessionFormation()).isNotNull();

        });


    }


    @Test
    public void test_delete_session_salle() {

        // Création d'une session salle avec le builder Lombock
        final SessionSalle sessionSallePlovan1 = SessionSalle
                .builder()
                .statutSessionSalle(StatutSessionSalle.SESSION_SALLE_VALIDEE)
                .build();

        // Associations ManyToOne
        sessionSallePlovan1.setSalle(sallePlovan1);
        sessionSallePlovan1.setSessionFormation(sessionMISST);

        // Sauvegarde de la session salle en base test via le repository
        final SessionSalle sessionSallePlovanDB = sessionSalleRepository.save(sessionSallePlovan1);


        // Vérification s'il y a au moins un identifiant dans SessionSalle, s'il n'est pas nul,
        // et si la salle est égal à sallePlovan1 et sa session de formation est égale à sessionMISST
        assertThat(sessionSallePlovanDB.getIdSessionSalle()).isGreaterThan(0);
        assertThat(sessionSallePlovanDB.getSalle()).isNotNull();
        assertThat(sessionSallePlovanDB.getSalle()).isEqualTo(sallePlovan1);
        assertThat(sessionSallePlovanDB.getSessionFormation()).isNotNull();
        assertThat(sessionSallePlovanDB.getSessionFormation()).isEqualTo(sessionMISST);

        // Suppression de la session salle
        sessionSalleRepository.delete(sessionSallePlovanDB);

        // Vérifie que l'entité SessionSalle n'est plus présente en base (doit être null)
        SessionSalle sessionSallePlovanDB2 = entityManager.find(SessionSalle.class, sessionSallePlovanDB.getIdSessionSalle());
        assertNull(sessionSallePlovanDB2);


        // Vérifie que les salles (dont celui qui y était associé à la sessionSalle) existent toujours en base (pas de suppression en cascade)
        List<Salle> listSalle = salleRepository.findAll();
        assertThat(listSalle).isNotNull();
        assertThat(listSalle).isNotEmpty();
        assertThat(listSalle.size()).isEqualTo(2);

        // Vérifie que la sessionFormation associée existent toujours en base (pas de suppression en cascade)
        List<SessionFormation> sessionFormation = sessionFormationRepository.findAll();
        assertThat(sessionFormation).isNotNull();
        assertThat(sessionFormation).isNotEmpty();
        assertThat(sessionFormation.size()).isEqualTo(1);

    }

    private List<SessionSalle> jeuDeDonnees() {

        List<SessionSalle> sessionsSalle = new ArrayList<>();

        sessionsSalle.add(SessionSalle
                .builder()
                .statutSessionSalle(StatutSessionSalle.SESSION_SALLE_VALIDEE)
                .coutSessionSalle(0.00F)
                .sessionFormation(sessionMISST)
                .salle(sallePlovan1)
                .build());

        sessionsSalle.add(SessionSalle
                .builder()
                .statutSessionSalle(StatutSessionSalle.SESSION_SALLE_VALIDEE)
                .coutSessionSalle(0.00F)
                .sessionFormation(sessionMISST)
                .salle(sallePlovan2)
                .build());

        return sessionsSalle;
    }

}
