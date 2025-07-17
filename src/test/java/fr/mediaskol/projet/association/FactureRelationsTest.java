package fr.mediaskol.projet.association;

import fr.mediaskol.projet.bo.facture.Facture;
import fr.mediaskol.projet.bo.formateur.Formateur;
import fr.mediaskol.projet.bo.salle.Salle;
import fr.mediaskol.projet.dal.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Test unitaire qui permet de valider l'association ManyToOne
 * Entre les entités Facture et Formateur
 * Entre les entités Facture et Salle
 */

// Configure un contexte Spring Boot limité à la couche JPA
@Slf4j
@DataJpaTest
public class FactureRelationsTest {

    // Permet des opérations avancées sur l'EntityManager pour les tests - Injecte TestEntityManager
    @Autowired
    private TestEntityManager entityManager;

    // Repository pour effectuer des opérations CRUD sur l'entité Facture
    @Autowired
    private FactureRepository factureRepository;

    // Repository pour effectuer des opérations CRUD sur l'entité Formateur
    @Autowired
    private FormateurRepository formateurRepository;

    @Autowired
    private SalleRepository salleRepository;

    private Formateur coco;

    private Salle sallePlovan;


    /**
     * Méthode exécutée avant chaque test pour initialiser la base de données
     * avec plusieurs objets TypeFormation persistés.
     */
    @BeforeEach
    public void init() {

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

        // Sauvegarde dans la base de test
        formateurRepository.save(coco);


        // Création d'une salle avec le builder Lombok
        sallePlovan = Salle
                .builder()
                .nomSalle("Salle polyvalente")
                .cleSalle(true)
                .build();

        // Sauvegarde dans la base de test
        salleRepository.save(sallePlovan);

    }


    @Test
    public void test_save_facture() {

        // Création d'une nouvelle facture avec le builder Lombok
        final Facture factureFormateur = Facture
                .builder()
                .numFacture("AF123456")
                .typeFacture("Formateur")
                .libelleFacture("Facture SessionSTT du 05-01-2025")
                .montantFacture(50.00F)
                .build();

        // Association ManyToOne entre la facture et le formateur
        factureFormateur.setFormateur(coco);

        // Sauvegarde de la facture dans la base test via le repository
        final Facture factureFormateurDB = factureRepository.save(factureFormateur);

        // Log pour visualiser l'objet persisté
        log.info(factureFormateurDB.toString());

        // Vérification de la cascade de l'association
        // Vérifie que l'objet retourné n'est pas null
        assertThat(factureFormateurDB.getIdFacture()).isGreaterThan(0);
        assertThat(factureFormateurDB.getFormateur()).isNotNull();
        assertThat(factureFormateurDB.getFormateur()).isEqualTo(coco);


        // Création d'une seconde facture avec le builder Lombok
        final Facture factureSalle = Facture
                .builder()
                .numFacture("AF123456")
                .typeFacture("Salle")
                .libelleFacture("Facture SessionSTT du 05-01-2025")
                .montantFacture(20.00F)
                .build();

        // Association ManyToOne entre la facture et la salle
        factureSalle.setSalle(sallePlovan);

        // Sauvegarde de la facture dans la base test via le repository
        final Facture factureSalleDB = factureRepository.save(factureSalle);

        // Log pour visualiser l'objet persisté
        log.info(factureSalleDB.toString());

        // Vérification de la cascade de l'association
        // Vérifie que l'objet retourné n'est pas null
        assertThat(factureSalleDB.getIdFacture()).isGreaterThan(0);
        assertThat(factureSalleDB.getSalle()).isNotNull();
        assertThat(factureSalleDB.getSalle()).isEqualTo(sallePlovan);

    }


    @Test
    public void test_find_all() {

        // Récupération des données de la méthode jeuDeDonnees()
        List<Facture> factures = jeuDeDonnees();

        // Sauvegarde du jeu de données dans la base
        factures.forEach(fact ->
        {
            factureRepository.save(fact);
            assertThat(fact.getIdFacture()).isGreaterThan(0);
        });

        // Vérifie l'identifiant des factures
        final List<Facture> facturesDB = factureRepository.findAll();
        facturesDB.forEach(factDB -> {
            assertThat(factDB.getIdFacture()).isGreaterThan(0);

            // vérification du formateur
            assertThat(factDB.getFormateur()).isNotNull();

            // vérification de la salle
            assertThat(factDB.getSalle()).isNotNull();
        });
    }


    // Test sur la suppression des factures sans supprimer le formateur ou la salle
    @Test
    public void test_delete_factures() {


        // Création d'une nouvelle facture avec le builder Lombok
        final Facture factureFormateur = Facture
                .builder()
                .numFacture("AF123456")
                .typeFacture("Formateur")
                .libelleFacture("Facture SessionSTT du 05-01-2025")
                .montantFacture(50.00F)
                .build();

        // Association ManyToOne entre la facture et le formateur
        factureFormateur.setFormateur(coco);

        // Sauvegarde de la facture dans la base test via le repository
        final Facture factureFormateurDB = factureRepository.save(factureFormateur);

        // Vérification de la cascade de l'association
        // Vérifie que l'objet retourné n'est pas null
        assertThat(factureFormateurDB.getIdFacture()).isGreaterThan(0);
        assertThat(factureFormateurDB.getFormateur()).isNotNull();
        assertThat(factureFormateurDB.getFormateur()).isEqualTo(coco);

        // Suppression de la facture
        factureRepository.delete(factureFormateurDB);

        // Vérifie que l'entité Facture n'est plus présente en base (doit être null)
        Facture factureFormateurDB2 = entityManager.find(Facture.class, factureFormateurDB.getIdFacture());
        assertNull(factureFormateurDB2);


        // Vérifie que le formateur existe toujours en base (pas de suppression en cascade)
        List<Formateur> formateurs = formateurRepository.findAll();
        assertThat(formateurs).isNotNull();
        assertThat(formateurs).isNotEmpty();
        assertThat(formateurs.size()).isEqualTo(1);


        // Création d'une nouvelle facture avec le builder Lombok
        final Facture factureSalle = Facture
                .builder()
                .numFacture("AF123456")
                .typeFacture("Salle")
                .libelleFacture("Facture SessionSTT du 05-01-2025")
                .montantFacture(20.00F)
                .build();

        // Association ManyToOne entre la facture et la salle
        factureSalle.setSalle(sallePlovan);

        // Sauvegarde de la facture dans la base test via le repository
        final Facture factureSalleDB = factureRepository.save(factureSalle);

        // Vérification de la cascade de l'association
        // Vérifie que l'objet retourné n'est pas null
        assertThat(factureSalleDB.getIdFacture()).isGreaterThan(0);
        assertThat(factureSalleDB.getSalle()).isNotNull();
        assertThat(factureSalleDB.getSalle()).isEqualTo(sallePlovan);

        // Suppression de la facture
        factureRepository.delete(factureSalleDB);

        // Vérifie que l'entité Facture n'est plus présente en base (doit être null)
        Facture factureSalleDB2 = entityManager.find(Facture.class, factureSalleDB.getIdFacture());
        assertNull(factureSalleDB2);


        // Vérifie que la salle existe toujours en base (pas de suppression en cascade)
        List<Salle> salles = salleRepository.findAll();
        assertThat(salles).isNotNull();
        assertThat(salles).isNotEmpty();
        assertThat(salles.size()).isEqualTo(1);

    }


    // Création d'un jeu de données de factures
    private List<Facture> jeuDeDonnees() {

        List<Facture> factures = new ArrayList<>();

        // Création d'une nouvelle facture avec le builder Lombok
        final Facture factureFormateur = Facture
                .builder()
                .numFacture("AF123456")
                .typeFacture("Formateur")
                .libelleFacture("Facture SessionSTT du 05-01-2025")
                .montantFacture(50.00F)
                .formateur(coco)
                .build();


        // Création d'une seconde facture avec le builder Lombok
        final Facture factureSalle = Facture
                .builder()
                .numFacture("AF123456")
                .typeFacture("Salle")
                .libelleFacture("Facture SessionSTT du 05-01-2025")
                .montantFacture(20.00F)
                .salle(sallePlovan)
                .build();

        return factures;
    }

}
