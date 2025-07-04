package fr.mediaskol.projet.association;


import fr.mediaskol.projet.bo.adresse.Adresse;
import fr.mediaskol.projet.bo.formateur.Formateur;
import fr.mediaskol.projet.dal.FormateurRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Création de Test qui permet de valider l'association OneToOne
 * Entre Formateur et Adresse
 */
@Slf4j
// Permet de configurer un contexte Spring Boot limité à la couche JPA
@DataJpaTest
public class TestOneToOneUniFormateurAdresse {

    // Permet des opérations avancées sur l'EntityManager pour les tests
    @Autowired
    private TestEntityManager entityManager;

    // Repository Spring Data JPA pour Formateur
    @Autowired
    private FormateurRepository formateurRepository;





    // Sauvegarde d'un formateur et de son adresse
    @Test
    public void test_save_formateur_OneToOneUni() {

        // Création d'une nouvelle adresse avec le builder Lombok
        final Adresse adresseCoco = Adresse
                .builder()
                .rue("20 rue des Lilas")
                .codePostal("22100")
                .ville("Quévert")
                .region("Bretagne")
                .build();

        // Création d'un formateur avec le builder Lombok
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

        // Création d'une adresse avec le builder Lombok
        final Adresse adresseCoco = Adresse
                .builder()
                .rue("20 rue des Lilas")
                .codePostal("22100")
                .ville("Quévert")
                .region("Bretagne")
                .build();

        // Création d'un formateur avec le builder Lombok
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

        // Persistance du formateur dans la base de test
        entityManager.persist(coco);
        entityManager.flush();

        // Vérification s'il y a au moins un identifiant dans Adresse
        assertThat(adresseCoco.getIdAdresse()).isGreaterThan(0);

        // Log pour visualiser l'objet persisté
        //log.info(coco.toString());

        // Suppression du formateur via le repository
        formateurRepository.delete(coco);

        // Vérifie que le formateur n'est plus présent en base (doit être null)
        Formateur cocoDB = entityManager.find(Formateur.class, coco.getIdPersonne());
        assertNull(cocoDB);
        Adresse adresseCocoDB = entityManager.find(Adresse.class, adresseCoco.getIdAdresse());
        assertNull(adresseCocoDB);
    }

    // Test qui permet de s'assurer que si l'on supprime le formateur, l'adresse qui y était associée
    // soit également supprimée
    @Test
    public void test_orphanRemoval_formateur_OneToOneUni() {

        // Création d'une adresse avec le builder Lombok
        final Adresse adresseCoco = Adresse
                .builder()
                .rue("20 rue des Lilas")
                .codePostal("22100")
                .ville("Quévert")
                .region("Bretagne")
                .build();

        // Création d'un formateur avec le builder Lombok
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

        // Persistance du formateur dans la base de test
        entityManager.persist(coco);
        entityManager.flush();

        // Vérification s'il y a au moins un identifiant dans Adresse
        assertThat(adresseCoco.getIdAdresse()).isGreaterThan(0);

        // Suppression du lien entre l'entité Formateur et l'entité Adresse
        coco.setAdresse(null);

        // Suppression du formateur via le repository
       formateurRepository.delete(coco);

        // Vérifie que le formateur n'est plus présent en base (doit être null)
        Formateur cocoDB = entityManager.find(Formateur.class, coco.getIdPersonne());
        assertNull(cocoDB);
        Adresse adresseCocoDB = entityManager.find(Adresse.class, adresseCoco.getIdAdresse());
        assertNull(adresseCocoDB);

    }



}










