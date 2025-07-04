package fr.mediaskol.projet.association;


import fr.mediaskol.projet.bo.adresse.Adresse;
import fr.mediaskol.projet.bo.apprenant.Apprenant;
import fr.mediaskol.projet.bo.formateur.Formateur;
import fr.mediaskol.projet.dal.ApprenantRepository;
import fr.mediaskol.projet.dal.FormateurRepository;
import fr.mediaskol.projet.dal.SalarieRepository;
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
 * Entre Formateur et Adresse
 */
@Slf4j
// Permet de configurer un contexte Spring Boot limité à la couche JPA
@DataJpaTest
public class TestOneToOneUni {

    // Permet des opérations avancées sur l'EntityManager pour les tests
    @Autowired
    private TestEntityManager entityManager;

    // Repository Spring Data JPA pour Apprenant
    @Autowired
    private ApprenantRepository apprenantRepository;

    // Repository Spring Data JPA pour Formateur
    @Autowired
    private FormateurRepository formateurRepository;


    // Sauvegarde d'un apprenant et de son adresse
    @Test
    public void test_save_apprenant_OneToOneUni() {

        final Adresse adresseTigrou = Adresse
                .builder()
                .rue("10 rue des Acacias")
                .codePostal("56140")
                .ville("Saint-Laurent-sur-Oust")
                .region("Bretagne")
                .build();

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

        final Adresse adresseTigrou = Adresse
                .builder()
                .rue("10 rue des Acacias")
                .codePostal("56140")
                .ville("Saint-Laurent-sur-Oust")
                .region("Bretagne")
                .build();

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

    // Test qui permet de s'assurer que si l'on supprime l'apprenant, l'adresse qui y était associé
    // soit également supprimée
    @Test
    public void test_orphanRemoval_apprenant_OneToOneUni() {

        final Adresse adresseTigrou = Adresse
                .builder()
                .rue("10 rue des Acacias")
                .codePostal("56140")
                .ville("Saint-Laurent-sur-Oust")
                .region("Bretagne")
                .build();

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


    // Sauvegarde d'un formateur et de son adresse
    @Test
    public void test_save_formateur_OneToOneUni() {

        final Adresse adresseCoco = Adresse
                .builder()
                .rue("20 rue des Lilas")
                .codePostal("22100")
                .ville("Quévert")
                .region("Bretagne")
                .build();

        final Formateur coco = Formateur
                .builder()
                .nom("Lapin")
                .prenom("Coco")
                .email("coco.lapin@gmail.fr")
                .numPortable("0600000000")
                .statutFormateur("AE")
                .zoneIntervention("Intervient sur la commune de Quévert dans un rayon de 30km")
                .commentaireFormateur("")
                .build();

        // Association entre l'adresse et le formateur
        coco.setAdresse(adresseCoco);

        // Sauvegarde du formateur en base via le repository
        final Formateur cocoDB = formateurRepository.save(coco);

        // Log pour visualiser l'objet persisté
        log.info(cocoDB.toString());

        // Vérification de la cascade de l'association
        // Vérifie que l'objet retourné n'est pas null
        assertThat(cocoDB.getAdresse()).isNotNull();

        // Vérification s'il y a au moins un identifiant dans Adresse
        assertThat(adresseCoco.getIdAdresse()).isGreaterThan(0);
    }

    // Suppression d'un formateur et de son adresse
    @Test
    public void test_delete_formateur_OneToOneUni() {

        final Adresse adresseCoco = Adresse
                .builder()
                .rue("20 rue des Lilas")
                .codePostal("22100")
                .ville("Quévert")
                .region("Bretagne")
                .build();

        final Formateur coco = Formateur
                .builder()
                .nom("Lapin")
                .prenom("Coco")
                .email("coco.lapin@gmail.fr")
                .numPortable("0600000000")
                .statutFormateur("AE")
                .zoneIntervention("Intervient sur la commune de Quévert dans un rayon de 30km")
                .commentaireFormateur("")
                .build();

        // Association entre l'adresse et le formateur
        coco.setAdresse(adresseCoco);

        // Persistance de le formateur dans la base de test
        entityManager.persist(coco);
        entityManager.flush();

        // Vérification s'il y a au moins un identifiant dans Adresse
        assertThat(adresseCoco.getIdAdresse()).isGreaterThan(0);

        // Log pour visualiser l'objet persisté
        //log.info(coco.toString());

        // Suppression de le formateur via le repository
        formateurRepository.delete(coco);

        // Vérifie que le formateur n'est plus présent en base (doit être null)
        Formateur cocoDB = entityManager.find(Formateur.class, coco.getIdPersonne());
        assertNull(cocoDB);
        Adresse adresseCocoDB = entityManager.find(Adresse.class, adresseCoco.getIdAdresse());
        assertNull(adresseCocoDB);
    }

    // Test qui permet de s'assurer que si l'on supprime le formateur, l'adresse qui y était associé
    // soit également supprimée
    @Test
    public void test_orphanRemoval_formateur_OneToOneUni() {

        final Adresse adresseCoco = Adresse
                .builder()
                .rue("20 rue des Lilas")
                .codePostal("22100")
                .ville("Quévert")
                .region("Bretagne")
                .build();

        final Formateur coco = Formateur
                .builder()
                .nom("Lapin")
                .prenom("Coco")
                .email("coco.lapin@gmail.fr")
                .numPortable("0600000000")
                .statutFormateur("AE")
                .zoneIntervention("Intervient sur la commune de Quévert dans un rayon de 30km")
                .commentaireFormateur("")
                .build();

        // Association entre l'adresse et le formateur
        coco.setAdresse(adresseCoco);

        // Persistance de le formateur dans la base de test
        entityManager.persist(coco);
        entityManager.flush();

        // Vérification s'il y a au moins un identifiant dans Adresse
        assertThat(adresseCoco.getIdAdresse()).isGreaterThan(0);

        // Suppression du lien entre l'entité Formateur et l'entité Adresse
        coco.setAdresse(null);

        // Suppression de le formateur via le repository
       formateurRepository.delete(coco);

        // Vérifie que le formateur n'est plus présent en base (doit être null)
        Formateur cocoDB = entityManager.find(Formateur.class, coco.getIdPersonne());
        assertNull(cocoDB);
        Adresse adresseCocoDB = entityManager.find(Adresse.class, adresseCoco.getIdAdresse());
        assertNull(adresseCocoDB);

    }
}










