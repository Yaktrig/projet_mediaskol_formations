package fr.mediaskol.projet.association;

import fr.mediaskol.projet.bo.SessionFormation.SessionFormation;
import fr.mediaskol.projet.bo.SessionLieuDate.SessionLieuDate;
import fr.mediaskol.projet.bo.SessionLieuDate.StatutSessionLieuDate;
import fr.mediaskol.projet.bo.formation.Formation;
import fr.mediaskol.projet.bo.formation.TypeFormation;
import fr.mediaskol.projet.bo.salle.Salle;
import fr.mediaskol.projet.bo.salle.SessionSalle;
import fr.mediaskol.projet.bo.salle.StatutSessionSalle;
import fr.mediaskol.projet.dal.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
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
 * Entre SessionLieuDate et SessionFormation (relation obligatoire)
 */
//Entre SessionLieuDate et SessionSalle
//Entre SessionLieuDate et SessionFormateur


// Configure un contexte Spring Boot limité à la couche JPA
@Slf4j
@DataJpaTest
public class SessionLieuDateRelationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SessionLieuDateRepository sessionLieuDateRepository;

    @Autowired
    private SessionFormationRepository sessionFormationRepository;
    private SessionFormation sessionMISST;

    // Repository pour effectuer des opérations CRUD sur l'entité Formation
    @Autowired
    private FormationRepository formationRepository;

    private Formation formationMISST;

    private TypeFormation presentiel;

    @Autowired
    private TypeFormationRepository typeFormationRepository;


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


    }


    @Test
    public void test_save_session_lieu_date() {

        // Création d'une session lieu date avec le builder Lombock
        final SessionLieuDate sessionLieuDate070126 = SessionLieuDate
                .builder()
                .dateSession(LocalDate.parse("2026-01-07"))
                .statutSessionLieuDate(StatutSessionLieuDate.SESSION_LIEU_DATE_SALLE_GRATUITE)
                .lieuSession("Plovan")
                .duree(7)
                .build();

        // Associations ManyToOne
        sessionLieuDate070126.setSessionFormation(sessionMISST);

        // Sauvegarde de la session lieu date en base test via le repository
        final SessionLieuDate sessionLieuDate070126DB = sessionLieuDateRepository.save(sessionLieuDate070126);

        // Vérifie que l'objet retourné n'est pas null
        assertThat(sessionLieuDate070126DB.getIdSessionLieuDate()).isGreaterThan(0);

        // Vérification si la session formation associée existe
        assertThat(sessionLieuDate070126DB.getIdSessionLieuDate()).isNotNull();
        assertThat(sessionLieuDate070126DB.getSessionFormation()).isEqualTo(sessionMISST);

        // Log pour visualiser l'objet persité
        log.info(sessionLieuDate070126DB.toString());


    }


    // Retrouver toutes les sessions lieu date d'une session de formation
    @Test
    public void test_find_all() {

        // Liste des sessions lieu date
        List<SessionLieuDate> listeSessionLieuDate = jeuDeDonnees();

        // Sauvegarder le jeu de données dans la base test
        listeSessionLieuDate.forEach(sessionLD -> {
            sessionLieuDateRepository.save(sessionLD);
            assertThat(sessionLD.getIdSessionLieuDate()).isGreaterThan(0);
        });

        // Vérification de l'identifiant des sessions lieu date
        final List<SessionLieuDate> listeSessionLieuDateDB = sessionLieuDateRepository.findAll();
        listeSessionLieuDateDB.forEach(sessionLD -> {
            assertThat(sessionLD.getIdSessionLieuDate()).isGreaterThan(0);

            // vérification de la session de formation
            assertThat(sessionLD.getSessionFormation()).isNotNull();


        });


    }


    @Test
    public void test_delete_session_salle() {

        // Création d'une session lieu date avec le builder Lombock
        final SessionLieuDate sessionLieuDate070126 = SessionLieuDate
                .builder()
                .dateSession(LocalDate.parse("2026-01-07"))
                .statutSessionLieuDate(StatutSessionLieuDate.SESSION_LIEU_DATE_SALLE_GRATUITE)
                .lieuSession("Plovan")
                .duree(7)
                .build();

        // Associations ManyToOne
        sessionLieuDate070126.setSessionFormation(sessionMISST);

        // Sauvegarde de la session lieu date en base test via le repository
        final SessionLieuDate sessionLieuDateDB = sessionLieuDateRepository.save(sessionLieuDate070126);


        // Vérification s'il y a au moins un identifiant dans SessionLieuDate, s'il n'est pas nul,
        // et si la session de formation est égale à sessionMISST
        assertThat(sessionLieuDateDB.getIdSessionLieuDate()).isGreaterThan(0);
        assertThat(sessionLieuDateDB.getSessionFormation()).isNotNull();
        assertThat(sessionLieuDateDB.getSessionFormation()).isEqualTo(sessionMISST);

        // Suppression de la session lieu date
        sessionLieuDateRepository.delete(sessionLieuDateDB);

        // Vérifie que l'entité SessionLieuDate n'est plus présente en base (doit être null)
        SessionLieuDate sessionLieuDateDB2 = entityManager.find(SessionLieuDate.class, sessionLieuDateDB.getIdSessionLieuDate());
        assertNull(sessionLieuDateDB2);


        // Vérifie que la sessionFormation associée existe toujours en base (pas de suppression en cascade)
        List<SessionFormation> sessionFormation = sessionFormationRepository.findAll();
        assertThat(sessionFormation).isNotNull();
        assertThat(sessionFormation).isNotEmpty();
        assertThat(sessionFormation.size()).isEqualTo(1);

    }

    private List<SessionLieuDate> jeuDeDonnees() {

        List<SessionLieuDate> sessionLieuDates = new ArrayList<>();

        sessionLieuDates.add(SessionLieuDate
                .builder()
                .dateSession(LocalDate.parse("2026-01-07"))
                .statutSessionLieuDate(StatutSessionLieuDate.SESSION_LIEU_DATE_SALLE_GRATUITE)
                .lieuSession("Plovan")
                .duree(7)
                .sessionFormation(sessionMISST)
                .build());

        sessionLieuDates.add(SessionLieuDate
                .builder()
                .dateSession(LocalDate.parse("2026-01-14"))
                .statutSessionLieuDate(StatutSessionLieuDate.SESSION_LIEU_DATE_SALLE_GRATUITE)
                .lieuSession("Plovan")
                .duree(7)
                .sessionFormation(sessionMISST)
                .build());

        return sessionLieuDates;
    }

}
