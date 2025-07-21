package fr.mediaskol.projet.association;

import fr.mediaskol.projet.bo.sessionFormation.SessionFormation;
import fr.mediaskol.projet.bo.apprenant.Apprenant;
import fr.mediaskol.projet.bo.apprenant.SessionApprenant;
import fr.mediaskol.projet.bo.apprenant.StatutNumPasseport;
import fr.mediaskol.projet.bo.formation.Formation;
import fr.mediaskol.projet.bo.formation.TypeFormation;
import fr.mediaskol.projet.dal.apprenant.ApprenantRepository;
import fr.mediaskol.projet.dal.apprenant.SessionApprenantRepository;
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
 * Entre SessionApprenant et Apprenant
 * Entre SessionApprenant et SessionFormation
 */

// Configure un contexte Spring Boot limité à la couche JPA
@Slf4j
@DataJpaTest
public class SessionApprenantRelationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SessionApprenantRepository sessionApprenantRepository;

    @Autowired
    private ApprenantRepository apprenantRepository;

    @Autowired
    private SessionFormationRepository sessionFormationRepository;


    private Apprenant tigrou;

    private Apprenant tigrou2;

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
                .dateDebutSession(LocalDate.parse("2025-01-07"))
                .nbHeureSession(14)
                .libelleSessionFormation("MISST07012025")
                .formation(formationMISST)
                .build();

        // Persitence de la session de formation dans la base
        sessionFormationRepository.save(sessionMISST);


        // Création d'un nouvel apprenant avec le builder Lombok
        tigrou = Apprenant
                .builder()
                .nom("Le tigre")
                .prenom("Tigrou")
                .email("tigrou.letigre@gmail.fr")
                .numPortable("0600000000")
                .dateNaissance(LocalDate.parse("2000-12-12"))
                .apprenantActif(true)
                .numPasseport("A123456")
                .commentaireApprenant("Fais des bonds partout")
                .statutNumPasseport(StatutNumPasseport.NUM_PASSEPORT_A_CREER)
                .build();

        // Sauvegarde dans la base de l'apprenant tigrou
        apprenantRepository.save(tigrou);

        // Création d'un nouvel apprenant avec le builder Lombok
        tigrou2 = Apprenant
                .builder()
                .nom("Le tigre2")
                .prenom("Tigrou2")
                .email("tigrou2.letigre@gmail.fr")
                .numPortable("0600000000")
                .dateNaissance(LocalDate.parse("2000-10-12"))
                .apprenantActif(true)
                .numPasseport("A123457")
                .commentaireApprenant("")
                .statutNumPasseport(StatutNumPasseport.NUM_PASSEPORT_CREE)
                .build();

        // Sauvegarde dans la base de l'apprenant tigrou2
        apprenantRepository.save(tigrou2);


    }

    @Test
    public void test_save_session_apprenant() {

        // Création d'une 1ère session apprenant de formation avec le builder Lombok
        final SessionApprenant sessionTigrou = SessionApprenant
                .builder()
                .modeReceptionInscription("mail")
                .build();

        // Associaton ManyToOne
        sessionTigrou.setApprenant(tigrou);
        sessionTigrou.setSessionFormation(sessionMISST);

        // Sauvegarde de la session apprenant en base test via le repository
        final SessionApprenant sessionTigrouDB = sessionApprenantRepository.save(sessionTigrou);

        // Vérifie que l'objet retourné n'est pas null
        assertThat(sessionTigrouDB.getIdSessionApprenant()).isGreaterThan(0);
        // Vérification si l'apprenant existe
        assertThat(sessionTigrouDB.getApprenant()).isNotNull();
        assertThat(sessionTigrouDB.getApprenant()).isEqualTo(tigrou);
        // Vérification si la session de formation existe
        assertThat(sessionTigrouDB.getSessionFormation()).isNotNull();
        assertThat(sessionTigrouDB.getSessionFormation()).isEqualTo(sessionMISST);

        // Log pour visualiser l'objet persisté
        log.info(sessionTigrouDB.toString());

        // Création d'une 2nde session apprenant de formation avec le builder Lombok
        final SessionApprenant sessionTigrou2 = SessionApprenant
                .builder()
                .modeReceptionInscription("courrier")
                .build();

        // Associaton ManyToOne
        sessionTigrou2.setApprenant(tigrou2);
        sessionTigrou2.setSessionFormation(sessionMISST);

        // Sauvegarde de la session apprenant en base test via le repository
        final SessionApprenant sessionTigrouDB2 = sessionApprenantRepository.save(sessionTigrou2);

        // Vérifie que l'objet retourné n'est pas null
        assertThat(sessionTigrouDB2.getIdSessionApprenant()).isGreaterThan(0);
        // Vérification si l'apprenant existe
        assertThat(sessionTigrouDB2.getApprenant()).isNotNull();
        assertThat(sessionTigrouDB2.getApprenant()).isEqualTo(tigrou2);
        // Vérification si la session de formation existe
        assertThat(sessionTigrouDB2.getSessionFormation()).isNotNull();
        assertThat(sessionTigrouDB2.getSessionFormation()).isEqualTo(sessionMISST);

        // Log pour visualiser l'objet persisté
        log.info(sessionTigrouDB2.toString());

    }

    // Retrouver toutes les sessions apprenant d'une session de formation
    @Test
    public void test_find_all() {

        // Liste des sessions apprenants
        List<SessionApprenant> listeSessionApprenant = jeuDeDonnees();

        // Sauvegarder le jeu de données dans la base test
        listeSessionApprenant.forEach(sessionApp -> {
            sessionApprenantRepository.save(sessionApp);
            assertThat(sessionApp.getIdSessionApprenant()).isGreaterThan(0);
        });

        // Vérification de l'identifiant des sessions apprenant
        final List<SessionApprenant> listeSessionApprenantDB = sessionApprenantRepository.findAll();
        listeSessionApprenantDB.forEach(sessionApp -> {
            assertThat(sessionApp.getIdSessionApprenant()).isGreaterThan(0);

            // vérification de l'apprenant
            assertThat(sessionApp.getApprenant()).isNotNull();

            // vérification de la session de formation
            assertThat(sessionApp.getSessionFormation()).isNotNull();

        });



    }

    @Test
    public void test_delete_session_apprenant() {

        // Création d'une 1ère session apprenant de formation avec le builder Lombok
        final SessionApprenant sessionTigrou = SessionApprenant
                .builder()
                .modeReceptionInscription("mail")
                .build();

        // Associaton ManyToOne
        sessionTigrou.setApprenant(tigrou);
        sessionTigrou.setSessionFormation(sessionMISST);

        // Persistance de la session apprenant dans la base de test
        final SessionApprenant sessionTigrouDB = sessionApprenantRepository.save(sessionTigrou);


        // Vérification s'il y a au moins un identifiant dans SessionApprenant, s'il n'est pas null,
        // et si son apprenant est égal à tigrou et sa session de formation est égale à sessionMISST
        assertThat(sessionTigrouDB.getIdSessionApprenant()).isGreaterThan(0);
        assertThat(sessionTigrouDB.getApprenant()).isNotNull();
        assertThat(sessionTigrouDB.getApprenant()).isEqualTo(tigrou);
        assertThat(sessionTigrouDB.getSessionFormation()).isNotNull();
        assertThat(sessionTigrouDB.getSessionFormation()).isEqualTo(sessionMISST);

        // Suppression de la session apprenant
        sessionApprenantRepository.delete(sessionTigrouDB);

        // Vérifie que l'entité SessionApprenant n'est plus présente en base (doit être null)
        SessionApprenant sessionTigrouDB2 = entityManager.find(SessionApprenant.class, sessionTigrouDB.getIdSessionApprenant());
        assertNull(sessionTigrouDB2);


        // Vérifie que les apprenants (dont celui qui y était associé à la sessionApprenant) existent toujours en base (pas de suppression en cascade)
        List<Apprenant> listApprenant = apprenantRepository.findAll();
        assertThat(listApprenant).isNotNull();
        assertThat(listApprenant).isNotEmpty();
        assertThat(listApprenant.size()).isEqualTo(2);

        // Vérifie que la sessionFormation associée existent toujours en base (pas de suppression en cascade)
        List<SessionFormation> sessionFormation = sessionFormationRepository.findAll();
        assertThat(sessionFormation).isNotNull();
        assertThat(sessionFormation).isNotEmpty();
        assertThat(sessionFormation.size()).isEqualTo(1);

    }


    private List<SessionApprenant> jeuDeDonnees() {

        List<SessionApprenant> sessionsApprenant = new ArrayList<>();

        sessionsApprenant.add(SessionApprenant
                .builder()
                .modeReceptionInscription("mail")
                .apprenant(tigrou)
                .sessionFormation(sessionMISST)
                .build());

        sessionsApprenant.add(SessionApprenant
                .builder()
                .modeReceptionInscription("courrier")
                .apprenant(tigrou2)
                .sessionFormation(sessionMISST)
                .build());



        return sessionsApprenant;
    }
}
