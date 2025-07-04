package fr.mediaskol.projet.association;


import fr.mediaskol.projet.bo.adresse.Adresse;
import fr.mediaskol.projet.bo.apprenant.Apprenant;
import fr.mediaskol.projet.bo.formateur.Formateur;
import fr.mediaskol.projet.bo.salle.Salle;
import fr.mediaskol.projet.dal.ApprenantRepository;
import fr.mediaskol.projet.dal.FormateurRepository;
import fr.mediaskol.projet.dal.SalleRepository;
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
 * Entre Salle et Adresse
 */
@Slf4j
// Permet de configurer un contexte Spring Boot limité à la couche JPA
@DataJpaTest
public class TestOneToOneUniSalleAdresse {

    // Permet des opérations avancées sur l'EntityManager pour les tests
    @Autowired
    private TestEntityManager entityManager;


    // Repository Spring Data JPA pour Salle
    @Autowired
    private SalleRepository salleRepository;



    // Sauvegarde d'une salle et de son adresse
    @Test
    public void test_save_salle_OneToOneUni() {

        // Création d'une adresse avec le builder Lombok
        final Adresse adressePlovan = Adresse
                .builder()
                .rue("30 rue des Tulipes")
                .codePostal("29720")
                .ville("Plovan")
                .region("Bretagne")
                .build();

        // Création d'une salle avec le builder Lombok
        final Salle sallePlovan = Salle
                .builder()
                .nomSalle("Salle polyvalente")
                .cleSalle(true)
                .build();

        // Association entre l'adresse et la salle
        sallePlovan.setAdresse(adressePlovan);

        // Sauvegarde de la salle en base via le repository
        final Salle sallePlovanDB = salleRepository.save(sallePlovan);

        // Log pour visualiser l'objet persisté
        log.info(sallePlovanDB.toString());

        // Vérification de la cascade de l'association
        // Vérifie que l'objet retourné n'est pas null
        assertThat(sallePlovanDB.getAdresse()).isNotNull();

        // Vérification s'il y a au moins un identifiant dans Adresse
        assertThat(adressePlovan.getIdAdresse()).isGreaterThan(0);
    }

    // Suppression d'une salle et de son adresse
    @Test
    public void test_delete_salle_OneToOneUni() {

        // Création d'une adresse avec le builder Lombok
        final Adresse adressePlovan = Adresse
                .builder()
                .rue("30 rue des Tulipes")
                .codePostal("29720")
                .ville("Plovan")
                .region("Bretagne")
                .build();

        // Création d'une salle avec le builder Lombok
        final Salle sallePlovan = Salle
                .builder()
                .nomSalle("Salle polyvalente")
                .cleSalle(true)
                .build();

        // Association entre l'adresse et la salle
        sallePlovan.setAdresse(adressePlovan);

        // Persistance de la salle dans la base de test
        entityManager.persist(sallePlovan);
        entityManager.flush();

        // Vérification s'il y a au moins un identifiant dans Adresse
        assertThat(adressePlovan.getIdAdresse()).isGreaterThan(0);

        // Log pour visualiser l'objet persisté
        log.info(sallePlovan.toString());

        // Suppression de la salle via le repository
        salleRepository.delete(sallePlovan);

        // Vérifie que la salle n'est plus présente en base (doit être null)
        Salle sallePlovanDB = entityManager.find(Salle.class, sallePlovan.getIdSalle());
        assertNull(sallePlovanDB);
        Adresse adressePlovanDB = entityManager.find(Adresse.class, adressePlovan.getIdAdresse());
        assertNull(adressePlovanDB);
    }

    // Test qui permet de s'assurer que si l'on supprime la salle, l'adresse qui y était associée
    // soit également supprimée
    @Test
    public void test_orphanRemoval_salle_OneToOneUni() {

        // Création d'une adresse avec le builder Lombok
        final Adresse adressePlovan = Adresse
                .builder()
                .rue("30 rue des Tulipes")
                .codePostal("29720")
                .ville("Plovan")
                .region("Bretagne")
                .build();

        // Création d'une salle avec le builder Lombok
        final Salle sallePlovan = Salle
                .builder()
                .nomSalle("Salle polyvalente")
                .cleSalle(true)
                .build();

        // Association entre l'adresse et la salle
        sallePlovan.setAdresse(adressePlovan);

        // Persistance de la salle dans la base de test
        entityManager.persist(sallePlovan);
        entityManager.flush();

        // Vérification s'il y a au moins un identifiant dans Adresse
        assertThat(adressePlovan.getIdAdresse()).isGreaterThan(0);

        // Suppression du lien entre l'entité salle et l'entité Adresse
        sallePlovan.setAdresse(null);

        // Suppression de la salle via le repository
       salleRepository.delete(sallePlovan);

        // Vérifie que la salle n'est plus présente en base (doit être null)
        Salle sallePlovanDB = entityManager.find(Salle.class, sallePlovan.getIdSalle());
        assertNull(sallePlovanDB);
        Adresse adressePlovanDB = entityManager.find(Adresse.class, adressePlovan.getIdAdresse());
        assertNull(adressePlovanDB);

    }
}










