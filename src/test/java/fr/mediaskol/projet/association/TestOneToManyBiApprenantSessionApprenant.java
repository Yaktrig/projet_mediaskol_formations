package fr.mediaskol.projet.association;

import fr.mediaskol.projet.bo.SessionFormation.SessionFormation;
import fr.mediaskol.projet.bo.apprenant.Apprenant;
import fr.mediaskol.projet.bo.apprenant.SessionApprenant;
import fr.mediaskol.projet.dal.ApprenantRepository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static fr.mediaskol.projet.bo.apprenant.StatutNumPasseport.NUM_PASSEPORT_CREE;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test unitaire qui permet de valider l'association bidirectionnelle OneToMany
 * Entre les entités Apprenant et SessionApprenant
 */
@Slf4j
// Configure un contexte Spring Boot limité à la couche JPA
@DataJpaTest
public class TestOneToManyBiApprenantSessionApprenant {

    // Permet des opérations avancées sur l'EntityManager pour les tests - Injecte TestEntityManager
    @Autowired
    private TestEntityManager entityManager;

    // Repository pour effectuer des opérations CRUD sur l'entité SessionApprenant
    @Autowired
    private ApprenantRepository apprenantRepository;

    // Initialisation d'une liste de sessions apprenant liée à un apprenant
    private List<SessionApprenant> datasSessionApprenant;

    private Apprenant apprenant;

    @BeforeEach
    void jeuDeDonnees() {

        datasSessionApprenant = new ArrayList<>();

        final Apprenant apprenant = Apprenant
                .builder()
                .nom("Le tigre")
                .prenom("Tigrou")
                .email("tigrou.letigre@gmail.fr")
                .numPortable("0600000000")
                .dateNaissance(LocalDate.parse("2000-12-12"))
                .apprenantActif(true)
                .numPasseport("A123456")
                .statutNumPasseport(NUM_PASSEPORT_CREE)
                .commentaireApprenant("Fais des bonds partout")
                .build();


        for (int i = 0; i < 5; i++) {

            final SessionFormation sessionsFormation = SessionFormation
                    .builder()
                    .dateDebutSession(LocalDate.parse("2026-0" + (i + 7) + "-10"))
                    .nbHeureSession(14)
                    .build();

            final SessionApprenant sessionApprenant = SessionApprenant
                    .builder()
                    .modeReceptionInscription("mail")
                    .sessionFormation(sessionsFormation)
                    .build();

            // Association bidirectionnelle
            sessionApprenant.setApprenant(apprenant);
            apprenant.addSession(sessionApprenant);

            datasSessionApprenant.add(sessionApprenant);

        }

    }

    @Test
    public void test_save() {

        // Persistance de l'apprenant (cascade sur les sessions)
        final Apprenant apprenantDB = apprenantRepository.save(apprenant);

        // Vérification de l'identifiant
        assertThat(apprenantDB.getIdPersonne()).isGreaterThan(0);


        // Vérification de la cascade de l'association
        assertThat(apprenantDB.getSessionsApprenant()).isNotNull();
        assertThat(apprenantDB.getSessionsApprenant()).isNotEmpty();
        assertThat(apprenantDB.getSessionsApprenant().size()).isEqualTo(5);

        // Vérification de la cohérence bidirectionnelle
        apprenantDB.getSessionsApprenant().forEach(session ->
                assertThat(session.getApprenant()).isEqualTo(apprenantDB)
        );


    }
}



