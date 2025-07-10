package fr.mediaskol.projet.bo.apprenant;

import fr.mediaskol.projet.dal.ApprenantRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Création de deux tests pour l'entité Apprenant
 * - Sauvegarder un apprenant - Vérifie que l'entité est bien persistée et récupérable
 * - Supprimer un apprenant - Vérifie que l'entité n'est plus présente après suppression
 */
@Slf4j
// Permet de configurer un contexte Spring Boot limité à la couche JPA
@DataJpaTest
public class TestApprenant {

    // Permet des opérations avancées sur l'EntityManager pour les tests
    @Autowired
    private TestEntityManager entityManager;

    // Repository Spring Data JPA pour Apprenant
    @Autowired
    private ApprenantRepository apprenantRepository;

    @Test
    public void test_save_apprenant() {

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

        // Sauvegarde de l'apprenant en base via le repository
        final Apprenant tigrouDB = apprenantRepository.save(tigrou);

        // Log pour visualiser l'objet persisté
        log.info(tigrouDB.toString());

        // Vérifie que l'objet retourné n'est pas null
        assertThat(tigrouDB).isNotNull();

        // Vérifie que l'objet retourné correspond à l'objet initial (égalité sur les champs)
        assertThat(tigrouDB).isEqualTo(tigrou);
    }

    @Test
    public void test_delete_apprenant() {

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

        // Persistance de l'apprenant dans la base de test
        entityManager.persist(tigrou);
        entityManager.flush();

        // Log pour visualiser l'objet persisté
        log.info(tigrou.toString());

        // Suppression de l'apprenant via le repository
        apprenantRepository.delete(tigrou);

        // Vérifie que l'apprenant n'est plus présent en base (doit être null)
        Apprenant tigrouDB = entityManager.find(Apprenant.class, tigrou.getIdPersonne());
        assertNull(tigrouDB);
    }

}
