package fr.mediaskol.projet.association;


import fr.mediaskol.projet.bo.adresse.Adresse;
import fr.mediaskol.projet.bo.apprenant.Apprenant;
import fr.mediaskol.projet.dal.ApprenantRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Création de Test qui permet de valider l'association OneToOne
 * Entre Apprenant et Adresse
 */
@Slf4j
// Permet de configurer un contexte Spring Boot limité à la couche JPA
@DataJpaTest
public class TestOneToOneUniApprenantAdresse {

    // Permet des opérations avancées sur l'EntityManager pour les tests
    @Autowired
    private TestEntityManager entityManager;

    // Repository Spring Data JPA pour Apprenant
    @Autowired
    private ApprenantRepository apprenantRepository;



    // Sauvegarde d'un apprenant et de son adresse
    @Test
    public void test_save_apprenant_OneToOneUni() {

        // Création d'une nouvelle adresse avec le builder Lombok
        final Adresse adresseTigrou = Adresse
                .builder()
                .rue("10 rue des Acacias")
                .codePostal("56140")
                .ville("Saint-Laurent-sur-Oust")
                .region("Bretagne")
                .build();

        // Création d'un nouvel apprenant tigrou avec le builder Lombok
        final Apprenant tigrou = Apprenant
                .builder()
                .nom("Le tigre")
                .prenom("Tigrou")
                .email("tigrou.letigre@gmail.fr")
                .numPortable("0600000000")
                .dateNaissance(LocalDate.parse("2000-12-12"))
                .statutApprenant(true)
                .noPasseport("A123456")
                .commentaireApprenant("Fais des bonds partout")
                .build();

        // Association entre l'adresse et l'apprenant
        tigrou.setAdresse(adresseTigrou);

        // Sauvegarde de l'apprenant en base via le repository
        final Apprenant tigrouDB = apprenantRepository.save(tigrou);

        // Log pour visualiser l'objet persisté
        log.info(tigrouDB.toString());

        // Vérification de la cascade de l'association
        // Vérifie que l'objet retourné n'est pas null
        assertThat(tigrouDB.getAdresse()).isNotNull();

        // Vérification s'il y a au moins un identifiant dans Adresse
        assertThat(adresseTigrou.getIdAdresse()).isGreaterThan(0);
    }

    // Suppression d'un apprenant et de son adresse
    @Test
    public void test_delete_apprenant_OneToOneUni() {

        // Création d'une adresse avec le builder Lombok
        final Adresse adresseTigrou = Adresse
                .builder()
                .rue("10 rue des Acacias")
                .codePostal("56140")
                .ville("Saint-Laurent-sur-Oust")
                .region("Bretagne")
                .build();

        // Création de l'apprenant tigrou avec le builder Lombok
        final Apprenant tigrou = Apprenant
                .builder()
                .nom("Le tigre")
                .prenom("Tigrou")
                .email("tigrou.letigre@gmail.fr")
                .numPortable("0600000000")
                .dateNaissance(LocalDate.parse("2000-12-12"))
                .statutApprenant(true)
                .noPasseport("A123456")
                .commentaireApprenant("Fais des bonds partout")
                .build();

        // Association entre l'adresse et l'apprenant
        tigrou.setAdresse(adresseTigrou);

        // Persistance de l'apprenant dans la base de test
        entityManager.persist(tigrou);
        entityManager.flush();

        // Vérification s'il y a au moins un identifiant dans Adresse
        assertThat(adresseTigrou.getIdAdresse()).isGreaterThan(0);

        // Log pour visualiser l'objet persisté
        //log.info(tigrou.toString());

        // Suppression de l'apprenant via le repository
        apprenantRepository.delete(tigrou);

        // Vérifie que l'apprenant n'est plus présent en base (doit être null)
        Apprenant tigrouDB = entityManager.find(Apprenant.class, tigrou.getIdPersonne());
        assertNull(tigrouDB);
        Adresse adresseTigrouDB = entityManager.find(Adresse.class, adresseTigrou.getIdAdresse());
        assertNull(adresseTigrouDB);
    }

    // Test qui permet de s'assurer que si l'on supprime l'apprenant, l'adresse qui y était associée
    // soit également supprimée
    @Test
    public void test_orphanRemoval_apprenant_OneToOneUni() {

        // Création d'une adresse avec le builder Lombok
        final Adresse adresseTigrou = Adresse
                .builder()
                .rue("10 rue des Acacias")
                .codePostal("56140")
                .ville("Saint-Laurent-sur-Oust")
                .region("Bretagne")
                .build();

        // Création d'un apprenant tigrou avec le builder Lombok
        final Apprenant tigrou = Apprenant
                .builder()
                .nom("Le tigre")
                .prenom("Tigrou")
                .email("tigrou.letigre@gmail.fr")
                .numPortable("0600000000")
                .dateNaissance(LocalDate.parse("2000-12-12"))
                .statutApprenant(true)
                .noPasseport("A123456")
                .commentaireApprenant("Fais des bonds partout")
                .build();

        // Association entre l'adresse et l'apprenant
        tigrou.setAdresse(adresseTigrou);

        // Persistance de l'apprenant dans la base de test
        entityManager.persist(tigrou);
        entityManager.flush();

        // Vérification s'il y a au moins un identifiant dans Adresse
        assertThat(adresseTigrou.getIdAdresse()).isGreaterThan(0);

        // Suppression du lien entre l'entité Apprenant et l'entité Adresse
        tigrou.setAdresse(null);

        // Suppression de l'apprenant via le repository
        apprenantRepository.delete(tigrou);

        // Vérifie que l'apprenant n'est plus présent en base (doit être null)
        Apprenant tigrouDB = entityManager.find(Apprenant.class, tigrou.getIdPersonne());
        assertNull(tigrouDB);
        Adresse adresseTigrouDB = entityManager.find(Adresse.class, adresseTigrou.getIdAdresse());
        assertNull(adresseTigrouDB);

    }


}










