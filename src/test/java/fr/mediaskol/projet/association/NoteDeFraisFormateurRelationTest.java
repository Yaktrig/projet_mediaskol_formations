package fr.mediaskol.projet.association;

import fr.mediaskol.projet.bo.NoteDeFrais.NoteDeFrais;
import fr.mediaskol.projet.bo.formateur.Formateur;
import fr.mediaskol.projet.dal.FormateurRepository;
import fr.mediaskol.projet.dal.NoteDeFraisRepository;
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
import static org.assertj.core.api.InstanceOfAssertFactories.list;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Test unitaire qui permet de valider l'association ManyToOne
 * Entre les entités NoteDeFrais et Formateur
 */

// Configure un contexte Spring Boot limité à la couche JPA
@Slf4j
@DataJpaTest
public class NoteDeFraisFormateurRelationTest {

    // Permet des opérations avancées sur l'EntityManager pour les tests - Injecte TestEntityManager
    @Autowired
    private TestEntityManager entityManager;

    // Repository pour effectuer des opérations CRUD sur l'entité NoteDeFrais
    @Autowired
    private NoteDeFraisRepository noteDeFraisRepository;

    // Repository pour effectuer des opérations CRUD sur l'entité Formateur
    @Autowired
    private FormateurRepository formateurRepository;

    private Formateur coco;


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

    }


    @Test
    public void test_save_note_de_frais() {

        // Création d'une nouvelle note de frais avec le builder Lombok
        final NoteDeFrais noteDeFrais01 = NoteDeFrais
                .builder()
                .libelleSessionFormation("MISST24012025")
                .dateSessionFormation(LocalDate.parse("2025-01-24"))
                .dateNoteDeFrais(LocalDate.parse("2025-02-24"))
                .dateReglementNoteDeFrais(LocalDate.parse("2025-02-25"))
                .montantRepas(4.00F)
                .montantFraisDivers(10.00F)
                .montantTotalNoteDeFrais(24.00F)
                .build();

        // Association ManyToOne entre la note de frais et le formateur
        noteDeFrais01.setFormateur(coco);

        // Sauvegarde de la note de frais dans la base test via le repository
        final NoteDeFrais noteDeFrais01DB = noteDeFraisRepository.save(noteDeFrais01);

        // Log pour visualiser l'objet persisté
        log.info(noteDeFrais01DB.toString());

        // Vérification de la cascade de l'association
        // Vérifie que l'objet retourné n'est pas null
        assertThat(noteDeFrais01DB.getIdNoteDeFrais()).isGreaterThan(0);
        assertThat(noteDeFrais01DB.getFormateur()).isNotNull();
        assertThat(noteDeFrais01DB.getFormateur()).isEqualTo(coco);


    }


    @Test
    public void test_find_all() {

        // Récupération des données de la méthode jeuDeDonnees()
        List<NoteDeFrais> listeNdf = jeuDeDonnees();

        // Sauvegarde du jeu de données dans la base
        listeNdf.forEach(noteDeFrais ->
        {
            noteDeFraisRepository.save(noteDeFrais);
            assertThat(noteDeFrais.getIdNoteDeFrais()).isGreaterThan(0);
        });

        // Vérifie l'identifiant des notes de frais
        final List<NoteDeFrais> listeNdfDB = noteDeFraisRepository.findAll();
        listeNdfDB.forEach(ndfDB -> {
            assertThat(ndfDB.getIdNoteDeFrais()).isGreaterThan(0);

            // vérification du formateur
            assertThat(ndfDB.getFormateur()).isNotNull();

        });
    }


    // Test sur la suppression des notes de frais sans supprimer le formateur
    @Test
    public void test_delete_note_de_frais() {


        // Création d'une nouvelle note de frais avec le builder Lombok
        final NoteDeFrais noteDeFrais01 = NoteDeFrais
                .builder()
                .libelleSessionFormation("MISST24012025")
                .dateSessionFormation(LocalDate.parse("2025-01-24"))
                .dateNoteDeFrais(LocalDate.parse("2025-02-24"))
                .dateReglementNoteDeFrais(LocalDate.parse("2025-02-25"))
                .montantRepas(4.00F)
                .montantFraisDivers(10.00F)
                .montantTotalNoteDeFrais(24.00F)
                .build();

        // Association ManyToOne entre la note de frais et le formateur
        noteDeFrais01.setFormateur(coco);

        // Sauvegarde de la note de frais dans la base test via le repository
        final NoteDeFrais noteDeFrais01DB = noteDeFraisRepository.save(noteDeFrais01);


        // Vérification de la cascade de l'association
        // Vérifie que l'objet retourné n'est pas null
        assertThat(noteDeFrais01DB.getIdNoteDeFrais()).isGreaterThan(0);
        assertThat(noteDeFrais01DB.getFormateur()).isNotNull();
        assertThat(noteDeFrais01DB.getFormateur()).isEqualTo(coco);

        // Suppression de la note de frais
        noteDeFraisRepository.delete(noteDeFrais01DB);

        // Vérifie que l'entité NoteDeFrais n'est plus présente en base (doit être null)
        NoteDeFrais noteDeFrais01DB2 = entityManager.find(NoteDeFrais.class, noteDeFrais01DB.getIdNoteDeFrais());
        assertNull(noteDeFrais01DB2);


        // Vérifie que le formateur existe toujours en base (pas de suppression en cascade)
        List<Formateur> formateurs = formateurRepository.findAll();
        assertThat(formateurs).isNotNull();
        assertThat(formateurs).isNotEmpty();
        assertThat(formateurs.size()).isEqualTo(1);


    }


    // Création d'un jeu de données de notes de frais
    private List<NoteDeFrais> jeuDeDonnees() {

        List<NoteDeFrais> listeNDF = new ArrayList<>();

        // Création d'une nouvelle note de frais avec le builder Lombok
        listeNDF.add(NoteDeFrais
                .builder()
                .libelleSessionFormation("MISST24012025")
                .dateSessionFormation(LocalDate.parse("2025-01-24"))
                .dateNoteDeFrais(LocalDate.parse("2025-02-24"))
                .dateReglementNoteDeFrais(LocalDate.parse("2025-02-25"))
                .montantRepas(4.00F)
                .montantFraisDivers(10.00F)
                .montantTotalNoteDeFrais(24.00F)
                .formateur(coco)
                .build());


        // Création d'une seconde note de frais avec le builder Lombok
        listeNDF.add(NoteDeFrais
                .builder()
                .libelleSessionFormation("MISST01062025")
                .dateSessionFormation(LocalDate.parse("2025-07-01"))
                .dateNoteDeFrais(LocalDate.parse("2025-07-01"))
                .dateReglementNoteDeFrais(LocalDate.parse("2025-02-25"))
                .montantRepas(5.00F)
                .montantPauseCafe(4.00F)
                .montantTotalNoteDeFrais(9.00F)
                .formateur(coco)
                .build());

        return listeNDF;
    }

}
