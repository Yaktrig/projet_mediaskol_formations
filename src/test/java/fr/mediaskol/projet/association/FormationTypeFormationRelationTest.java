package fr.mediaskol.projet.association;

import fr.mediaskol.projet.bo.formation.Formation;
import fr.mediaskol.projet.bo.formation.TypeFormation;
import fr.mediaskol.projet.dal.formation.FormationRepository;
import fr.mediaskol.projet.dal.formation.TypeFormationRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Test unitaire qui permet de valider l'association ManyToOne
 * Entre les entités Formation et TypeFormation
 */

// Configure un contexte Spring Boot limité à la couche JPA
@Slf4j
@DataJpaTest
public class FormationTypeFormationRelationTest {

    // Permet des opérations avancées sur l'EntityManager pour les tests - Injecte TestEntityManager
    @Autowired
    private TestEntityManager entityManager;

    // Repository pour effectuer des opérations CRUD sur l'entité Formation
    @Autowired
    private FormationRepository formationRepository;

    // Repository pour effectuer des opérations CRUD sur l'entité TypeFormation
    @Autowired
    private TypeFormationRepository typeFormationRepository;


    // Liste utilisée pour stocker les objets Formation pré-chargés en base pour les tests
    private Set<TypeFormation> listeTypeFormationDB = new HashSet<>();

    private TypeFormation distanciel;

    private TypeFormation presentiel;


    /**
     * Méthode exécutée avant chaque test pour initialiser la base de données
     * avec plusieurs objets TypeFormation persistés.
     */
    @BeforeEach
    public void initTypeFormation() {

        distanciel = TypeFormation
                .builder()
                .libelleTypeFormation("Distanciel")
                .build();


        presentiel = TypeFormation
                .builder()
                .libelleTypeFormation("Présentiel")
                .build();

        typeFormationRepository.save(distanciel);
        typeFormationRepository.save(presentiel);


    }

    // Sauvegarde d'une formation et de son type de formation
    @Test
    public void test_save_formation() {

        // Création d'une nouvelle formation avec le builder Lombok
        final Formation formationSST = Formation
                .builder()
                .themeFormation("MISST")
                .libelleFormation("Sauveteur secouriste du travail (SST initial)")
                .build();

        // Association ManyToOne entre la formation et son type
        formationSST.setTypeFormation(presentiel);

        // Sauvegarde de la formation dans la base test via le repository
        final Formation formationSSTDB = formationRepository.save(formationSST);

        // Log pour visualiser l'objet persisté
        log.info(formationSSTDB.toString());

        // Vérification de la cascade de l'association
        // Vérifie que l'objet retourné n'est pas null
        assertThat(formationSSTDB.getIdFormation()).isGreaterThan(0);
        assertThat(formationSSTDB.getTypeFormation()).isNotNull();
        assertThat(formationSSTDB.getTypeFormation()).isEqualTo(presentiel);

    }


    @Test
    public void test_find_all() {

        // Récupération des données de la méthode jeuDeDonnees()
        List<Formation> formations = jeuDeDonnees();

        // Sauvegarde du jeu de données dans la base
        formations.forEach(formation ->
        {
            formationRepository.save(formation);
            assertThat(formation.getIdFormation()).isGreaterThan(0);
        });

        // Vérifie l'identifiant des formations
        final List<Formation> formationsDB = formationRepository.findAll();
        formationsDB.forEach(formation -> {
            assertThat(formation.getIdFormation()).isGreaterThan(0);

            // vérification du type de formation
            assertThat(formation.getTypeFormation()).isNotNull();
        });
    }


    // Test sur la suppression des formations sans supprimer le type de formation
    @Test
    public void test_delete_formation() {


        // Création d'une nouvelle formation avec le builder Lombok
        final Formation formationSST = Formation
                .builder()
                .themeFormation("MISST")
                .libelleFormation("Sauveteur secouriste du travail (SST initial)")
                .build();

        // Association ManyToOne entre la formation et son type
        formationSST.setTypeFormation(presentiel);

        // Sauvegarde de la formation dans la base test via le repository
        final Formation formationSSTDB = formationRepository.save(formationSST);

        // Vérification de la cascade de l'association
        // Vérifie que l'objet retourné n'est pas null
        assertThat(formationSSTDB.getIdFormation()).isGreaterThan(0);
        assertThat(formationSSTDB.getTypeFormation()).isNotNull();
        assertThat(formationSSTDB.getTypeFormation()).isEqualTo(presentiel);

        // Suppression de la formation
        formationRepository.delete(formationSSTDB);

        // Vérifie que l'entité Formation n'est plus présente en base (doit être null)
        Formation formationSSTDB2 = entityManager.find(Formation.class, formationSSTDB.getIdFormation());
        assertNull(formationSSTDB2);


        // Vérifie que les types de formations existent toujours en base (pas de suppression en cascade)
        List<TypeFormation> typeFormations = typeFormationRepository.findAll();
        assertThat(typeFormations).isNotNull();
        assertThat(typeFormations).isNotEmpty();
        assertThat(typeFormations.size()).isEqualTo(2);


    }


    // Création d'un jeu de données de formations
    private List<Formation> jeuDeDonnees() {

        List<Formation> formations = new ArrayList<>();

        formations.add(Formation.builder()
                .themeFormation("MISST")
                .libelleFormation("Sauveteur secouriste du travail (SST initial)")
                .typeFormation(presentiel)
                .build());

        formations.add(Formation.builder()
                .themeFormation("MICE")
                .libelleFormation("Comprendre les émotions de l'enfant pour mieux l'accompagner au quotidien")
                .typeFormation(distanciel)
                .build());

        formations.add(Formation.builder()
                .themeFormation("MIMACSST")
                .libelleFormation("Recyclage sauveteur secouriste du travail")
                .typeFormation(presentiel)
                .build());

        return formations;
    }

}
