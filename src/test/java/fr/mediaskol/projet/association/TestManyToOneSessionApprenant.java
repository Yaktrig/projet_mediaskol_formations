package fr.mediaskol.projet.association;

import fr.mediaskol.projet.bo.SessionFormation.SessionFormation;
import fr.mediaskol.projet.bo.apprenant.Apprenant;
import fr.mediaskol.projet.bo.apprenant.SessionApprenant;
import fr.mediaskol.projet.bo.apprenant.StatutNumPasseport;
import fr.mediaskol.projet.dal.ApprenantRepository;
import fr.mediaskol.projet.dal.SessionApprenantRepository;
import fr.mediaskol.projet.dal.SessionFormationRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test unitaire qui permet de valider l'association ManyToOne
 * Entre SessionApprenant et Apprenant
 * Entre SessionApprenant et SessionFormation
 */

// Configure un contexte Spring Boot limité à la couche JPA
@Slf4j
@DataJpaTest
public class TestManyToOneSessionApprenant {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SessionApprenantRepository sessionApprenantRepository;

    @Autowired
    private SessionFormationRepository sessionFormationRepository;

    @Autowired
    private ApprenantRepository apprenantRepository;


    @Test
    public void test_save_session_apprenant(){

        // Création d'une nouvelle session de formation avec le builder Lombok
        final SessionFormation sessionMICE = SessionFormation
                .builder()
                .noYoda("123456")
                .dateDebutSession(LocalDate.parse("2026-01-07"))
                .nbHeureSession(14)
                .libelleSessionFormation("MICE24052025")
                .build();

        // Persitence de la session de formation dans la base
        entityManager.persist(sessionMICE);

        // Création d'un nouvel apprenant avec le builder Lombok
        final Apprenant tigrou = Apprenant
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

        // Persistence de l'apprenant dans la base de données
        entityManager.persist(tigrou);

        // Création d'une nouvelle apprenant de formation avec le builder Lombok
        final SessionApprenant sessionTigrou = SessionApprenant
                .builder()
                .modeReceptionInscription("mail")
                .build();

        // Associaton ManyToOne
        sessionTigrou.setApprenant(tigrou);
        sessionTigrou.setSessionFormation(sessionMICE);

        // Sauvegarde de la session apprenant en base via le repository
        final SessionApprenant sessionTigrouDB = sessionApprenantRepository.save(sessionTigrou);

        /// Vérification de la cascade de l'association
        // Vérifie que l'objet retourné n'est pas null
        assertThat(sessionTigrouDB.getIdSessionApprenant()).isGreaterThan(0);
        // Vérification si l'apprenant existe
        assertThat(sessionTigrouDB.getApprenant()).isNotNull();
        assertThat(sessionTigrouDB.getApprenant()).isEqualTo(tigrou);
        // Vérification si la session de formation existe
        assertThat(sessionTigrouDB.getSessionFormation()).isNotNull();
        assertThat(sessionTigrouDB.getSessionFormation()).isEqualTo(sessionMICE);

        // Log pour visualiser l'objet persisté
        log.info(sessionTigrouDB.toString());
    }
}
