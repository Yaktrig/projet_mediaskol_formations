package fr.mediaskol.projet.association;


import fr.mediaskol.projet.bo.formateur.Formateur;
import fr.mediaskol.projet.bo.formation.Formation;
import fr.mediaskol.projet.bo.formation.TypeFormation;
import fr.mediaskol.projet.dal.FormateurRepository;
import fr.mediaskol.projet.dal.FormationRepository;
import fr.mediaskol.projet.dal.TypeFormationRepository;
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
 * Test unitaire qui permet de valider l'association ManyToMany
 * Entre les entités Formateur et Formation
 */
@Slf4j
// Configure un contexte Spring Boot limité à la couche JPA
@DataJpaTest
public class TestManyToManyFormateurFormation {

    // Permet des opérations avancées sur l'EntityManager pour les tests - Injecte TestEntityManager
    @Autowired
    private TestEntityManager entityManager;

    // Repository pour effectuer des opérations CRUD sur l'entité Formateur
    @Autowired
    private FormateurRepository formateurRepository;

    // Repository pour effectuer des opérations CRUD sur l'entité Formation
    @Autowired
    private FormationRepository formationRepository;

    // Repository pour effectuer des opération CRUD sur l'entité TypeFormation

    // Liste utilisée pour stocker les objets Formation pré-chargés en base pour les tests
    private List<Formation> listeFormationDB = new ArrayList<>();

    private TypeFormation distanciel;

    private TypeFormation presentiel;
    @Autowired
    private TypeFormationRepository typeFormationRepository;

    /**
     * Méthode exécutée avant chaque test pour initialiser la base de données
     * avec plusieurs objets Formation persistés.
     */
    @BeforeEach
    public void initFormations() {

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


        listeFormationDB.add(Formation
                .builder()
                .themeFormation("MISST")
                .libelleFormation("Sauveteur secouriste du travail (SST initial)")
                .typeFormation(presentiel)
                .build());

        listeFormationDB.add(Formation
                .builder()
                .themeFormation("MICE")
                .libelleFormation("Comprendre les émotions de l'enfant pour mieux l'accompagner au quotidien")
                .typeFormation(distanciel)
                .build());

        listeFormationDB.add(Formation
                .builder()
                .themeFormation("MIMACSST")
                .libelleFormation("Recyclage sauveteur secouriste du travail")
                .typeFormation(presentiel)
                .build());

        // Persiste chaque formation et vérifie que l'identifiant a bien été généré
        listeFormationDB.forEach(f -> {
            formationRepository.save(f);
            assertThat(f.getIdFormation()).isGreaterThan(0);
        });
    }


    /**
     * Teste la sauvegarde d'un formateur et l'association avec une formation (ManyToMany).
     * Vérifie que la relation est bien persistée et que les données sont cohérentes.
     */
    @Test
    public void test_save_formateur_formations() {

        // Création d'un formateur avec le builder Lombok
        final Formateur formateurCoco = Formateur
                .builder()
                .nom("Lapin")
                .prenom("Coco")
                .email("coco.lapin@gmail.fr")
                .numPortable("0600000000")
                .statutFormateur("AE")
                .zoneIntervention("Intervient sur la commune de Quévert dans un rayon de 30km")
                .commentaireFormateur("")
                .build();

        // Sélectionne la formation dont le thème est "MICE"
        final List<Formation> formations = listeFormationDB
                .stream()
                .filter(item -> item.getThemeFormation().equals("MICE"))
                .toList();

        // Vérifie que la liste retournée n'est pas nulle, ni vide et contient bien une formation
        assertThat(formations).isNotNull();
        assertThat(formations).isNotEmpty();
        assertThat(formations.size()).isEqualTo(1);

        // Association entre le formateur et la liste des formations
        formateurCoco.setFormationsDispensees(formations);

        // Sauvegarde du formateur en base via le repository
        final Formateur formateurCocoDB = formateurRepository.save(formateurCoco);

        // Vérification de l'identifiant du formateur
        assertThat(formateurCocoDB.getIdPersonne()).isGreaterThan(0);

        // Vérifie que l'association ManyToMany est bien persistée
        assertThat(formateurCocoDB.getFormationsDispensees()).isNotNull();
        assertThat(formateurCocoDB.getFormationsDispensees()).isNotEmpty();
        assertThat(formateurCocoDB.getFormationsDispensees().size()).isEqualTo(1);
        log.info(formateurCocoDB.toString());

    }

    /**
     * Teste la suppression d'un formateur et vérifie que les formations associées
     * ne sont pas supprimées (pas de suppression en cascade).
     */
    @Test
    public void test_delete_formateur() {

        // Création d'un formateur avec le builder Lombok
        final Formateur formateurCoco = Formateur
                .builder()
                .nom("Lapin")
                .prenom("Coco")
                .email("coco.lapin@gmail.fr")
                .numPortable("0600000000")
                .statutFormateur("AE")
                .zoneIntervention("Intervient sur la commune de Quévert dans un rayon de 30km")
                .commentaireFormateur("")
                .build();

        // Sélectionne les formations dont le thème est "MISST" ou "MIMACSST"
        final List<Formation> formations = listeFormationDB
                .stream()
                .filter(item -> item.getThemeFormation().equals("MISST") || item.getThemeFormation().equals("MIMACSST"))
                .toList();

        // Vérifie que la liste n'est ni nulle ni vide et contient deux formations
        assertThat(formations).isNotNull();
        assertThat(formations).isNotEmpty();
        assertThat(formations.size()).isEqualTo(2);

        // Association entre le formateur et la liste des formations
        formateurCoco.setFormationsDispensees(formations);


        // Persistance du formateur dans la base de test
        final Formateur formateurCocoDB = formateurRepository.save(formateurCoco);

        // Vérifie que le formateur a bien un identifiant et que les formations sont associées
        assertThat(formateurCocoDB.getIdPersonne()).isGreaterThan(0);
        assertThat(formateurCocoDB.getFormationsDispensees()).isNotNull();
        assertThat(formateurCocoDB.getFormationsDispensees()).isNotEmpty();

        // Récupère la liste des formations associées depuis la base
        List<Formation> formationsDispensesDB = formateurCocoDB.getFormationsDispensees();
        assertThat(formationsDispensesDB).isNotNull();
        assertThat(formationsDispensesDB).isNotEmpty();
        assertThat(formationsDispensesDB.size()).isEqualTo(2);

        // Suppression du formateur via le repository
        formateurRepository.delete(formateurCocoDB);

        // Vérification que l'entité a été supprimée
        final Formateur formateurCocoDB2 = entityManager.find(Formateur.class, formateurCoco.getIdPersonne());
        assertNull(formateurCocoDB2);

        // Vérifie que les formations associées existent toujours en base (pas de suppression en cascade)
        List<Formation> formationsDispensesDB2 = formationRepository.findAll();
        assertThat(formationsDispensesDB2).isNotNull();
        assertThat(formationsDispensesDB2).isNotEmpty();
        assertThat(formationsDispensesDB2.size()).isEqualTo(3);

    }
}
