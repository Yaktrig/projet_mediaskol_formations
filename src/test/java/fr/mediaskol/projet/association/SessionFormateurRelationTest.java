package fr.mediaskol.projet.association;

import fr.mediaskol.projet.bo.sessionFormation.SessionFormation;
import fr.mediaskol.projet.bo.formateur.Formateur;
import fr.mediaskol.projet.bo.formateur.SessionFormateur;
import fr.mediaskol.projet.bo.formateur.StatutSessionFormateur;
import fr.mediaskol.projet.bo.formation.Formation;
import fr.mediaskol.projet.bo.formation.TypeFormation;
import fr.mediaskol.projet.dal.formateur.FormateurRepository;
import fr.mediaskol.projet.dal.formateur.SessionFormateurRepository;
import fr.mediaskol.projet.dal.formation.FormationRepository;
import fr.mediaskol.projet.dal.formation.TypeFormationRepository;
import fr.mediaskol.projet.dal.sessionFormation.SessionFormationRepository;
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
 * Entre SessionFormateur et Formateur
 * Entre SessionFormateur et SessionFormation
 */


// Configure un contexte Spring Boot limité à la couche JPA
@Slf4j
@DataJpaTest
public class SessionFormateurRelationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SessionFormateurRepository sessionFormateurRepository;

    @Autowired
    private FormateurRepository formateurRepository;

    @Autowired
    private SessionFormationRepository sessionFormationRepository;

    private Formateur coco;

    private Formateur mamanGourou;

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

        // Persitence de la session de formation dans la base
        sessionFormationRepository.save(sessionMISST);

        // Création d'un formateur avec le builder Lombok
        coco = Formateur
                .builder()
                .nom("Lapin")
                .prenom("Coco")
                .email("coco.lapin@gmail.fr")
                .numPortable("0600000000")
                .statutFormateur("AE")
                .zoneIntervention("Intervient sur la commune de Quévert dans un rayon de 30km")
                .commentaireFormateur("")
                .build();


        // Sauvegarde dans la base de test du formateur
        formateurRepository.save(coco);

        // Création d'un second formateur avec le builder Lombok
        mamanGourou = Formateur
                .builder()
                .nom("Gourou")
                .prenom("Maman")
                .email("Mamam.gourou@gmail.fr")
                .numPortable("0600000000")
                .statutFormateur("S")
                .zoneIntervention("Intervient sur la commune de Quévert dans un rayon de 30km")
                .commentaireFormateur("")
                .build();


        // Sauvegarde dans la base de test du formateur
        formateurRepository.save(mamanGourou);
    }


    @Test
    public void test_save_session_formateur() {

        // Création d'une session formateur avec le builder Lombock
        final SessionFormateur sessionCoco = SessionFormateur
                .builder()
                .statutSessionFormateur(StatutSessionFormateur.SESSION_FORMATEUR_PRESENCE_CONFIRMEE)
                .build();

        // Associations ManyToOne
        sessionCoco.setFormateur(coco);
        sessionCoco.setSessionFormation(sessionMISST);

        // Sauvegarde de la session formateur en base test via le repository
        final SessionFormateur sessionCocoDB = sessionFormateurRepository.save(sessionCoco);

        // Vérifie que l'objet retourné n'est pas null
        assertThat(sessionCocoDB.getIdSessionFormateur()).isGreaterThan(0);
        // Vérification si le formateur existe
        assertThat(sessionCocoDB.getFormateur()).isNotNull();
        assertThat(sessionCocoDB.getFormateur()).isEqualTo(coco);
        // Vérification si la session de formation existe
        assertThat(sessionCocoDB.getSessionFormation()).isNotNull();
        assertThat(sessionCocoDB.getSessionFormation()).isEqualTo(sessionMISST);

        // Log pour visualiser l'objet persité
        log.info(sessionCocoDB.toString());


        // Création d'une seconde session formateur avec le builder Lombock
        final SessionFormateur sessionMamanGourou = SessionFormateur
                .builder()
                .statutSessionFormateur(StatutSessionFormateur.SESSION_FORMATEUR_ATTENTE_PRESENCE)
                .build();

        // Associations ManyToOne
        sessionMamanGourou.setFormateur(mamanGourou);
        sessionMamanGourou.setSessionFormation(sessionMISST);

        // Sauvegarde de la session formateur en base test via le repository
        final SessionFormateur sessionMamanGourouDB = sessionFormateurRepository.save(sessionMamanGourou);

        // Vérifie que l'objet retourné n'est pas null
        assertThat(sessionMamanGourouDB.getIdSessionFormateur()).isGreaterThan(0);
        // Vérification si la formatrice existe
        assertThat(sessionMamanGourouDB.getFormateur()).isNotNull();
        assertThat(sessionMamanGourouDB.getFormateur()).isEqualTo(mamanGourou);
        // Vérification si la session de formation existe
        assertThat(sessionMamanGourouDB.getSessionFormation()).isNotNull();
        assertThat(sessionMamanGourouDB.getSessionFormation()).isEqualTo(sessionMISST);

        // Log pour visualiser l'objet persité
        log.info(sessionMamanGourouDB.toString());
    }


    // Retrouver toutes les sessions formateurs d'une session de formation
    @Test
    public void test_find_all() {

        // Liste des sessions apprenants
        List<SessionFormateur> listeSessionFormateur = jeuDeDonnees();

        // Sauvegarder le jeu de données dans la base test
        listeSessionFormateur.forEach(sessionFormateur -> {
            sessionFormateurRepository.save(sessionFormateur);
            assertThat(sessionFormateur.getIdSessionFormateur()).isGreaterThan(0);
        });

        // Vérification de l'identifiant des sessions formateurs
        final List<SessionFormateur> listeSessionFormateurDB = sessionFormateurRepository.findAll();
        listeSessionFormateurDB.forEach(sessionFormateur -> {
            assertThat(sessionFormateur.getIdSessionFormateur()).isGreaterThan(0);

            // vérification du formateur
            assertThat(sessionFormateur.getFormateur()).isNotNull();

            // vérification de la session de formation
            assertThat(sessionFormateur.getSessionFormation()).isNotNull();

        });


    }


    @Test
    public void test_delete_session_formateur() {

        // Création d'une 1ère session formateur de formation avec le builder Lombok
        final SessionFormateur sessionCoco = SessionFormateur
                .builder()
                .statutSessionFormateur(StatutSessionFormateur.SESSION_FORMATEUR_PRESENCE_CONFIRMEE)
                .build();

        // Associaton ManyToOne
        sessionCoco.setFormateur(coco);
        sessionCoco.setSessionFormation(sessionMISST);

        // Persistance de la session formateur dans la base de test
        final SessionFormateur sessionCocoDB = sessionFormateurRepository.save(sessionCoco);


        // Vérification s'il y a au moins un identifiant dans SessionFormateur, s'il n'est pas null,
        // et si son formateur est égal à coco et sa session de formation est égale à sessionMISST
        assertThat(sessionCocoDB.getIdSessionFormateur()).isGreaterThan(0);
        assertThat(sessionCocoDB.getFormateur()).isNotNull();
        assertThat(sessionCocoDB.getFormateur()).isEqualTo(coco);
        assertThat(sessionCocoDB.getSessionFormation()).isNotNull();
        assertThat(sessionCocoDB.getSessionFormation()).isEqualTo(sessionMISST);

        // Suppression de la session formateur
        sessionFormateurRepository.delete(sessionCocoDB);

        // Vérifie que l'entité SessionFormateur n'est plus présente en base (doit être null)
        SessionFormateur sessionCocoDB2 = entityManager.find(SessionFormateur.class, sessionCocoDB.getIdSessionFormateur());
        assertNull(sessionCocoDB2);


        // Vérifie que les formateurs (dont celui qui y était associé à la sessionFormateur) existent toujours en base (pas de suppression en cascade)
        List<Formateur> listFormateur = formateurRepository.findAll();
        assertThat(listFormateur).isNotNull();
        assertThat(listFormateur).isNotEmpty();
        assertThat(listFormateur.size()).isEqualTo(2);

        // Vérifie que la sessionFormation associée existent toujours en base (pas de suppression en cascade)
        List<SessionFormation> sessionFormation = sessionFormationRepository.findAll();
        assertThat(sessionFormation).isNotNull();
        assertThat(sessionFormation).isNotEmpty();
        assertThat(sessionFormation.size()).isEqualTo(1);

    }

    private List<SessionFormateur> jeuDeDonnees() {

        List<SessionFormateur> sessionsFormateur = new ArrayList<>();

        sessionsFormateur.add(SessionFormateur
                .builder()
                .statutSessionFormateur(StatutSessionFormateur.SESSION_FORMATEUR_PRESENCE_CONFIRMEE)
                .formateur(coco)
                .sessionFormation(sessionMISST)
                .build());

        sessionsFormateur.add(SessionFormateur
                .builder()
                .statutSessionFormateur(StatutSessionFormateur.SESSION_FORMATEUR_ATTENTE_PRESENCE)
                .formateur(mamanGourou)
                .sessionFormation(sessionMISST)
                .build());


        return sessionsFormateur;
    }

}
