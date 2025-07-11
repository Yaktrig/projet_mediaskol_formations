package fr.mediaskol.projet.association;

import fr.mediaskol.projet.bo.formateur.Formateur;
import fr.mediaskol.projet.bo.formation.TypeFormation;
import fr.mediaskol.projet.dal.FormateurRepository;
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
 * Entre les entités Formateur et TypeFormation
 */
@Slf4j
// Configure un contexte Spring Boot limité à la couche JPA
@DataJpaTest
public class TestManyToManyFormateurTypeformation {

    // Permet des opérations avancées sur l'EntityManager pour les tests - Injecte TestEntityManager
    @Autowired
    private TestEntityManager entityManager;

    // Repository pour effectuer des opérations CRUD sur l'entité Formateur
    @Autowired
    private FormateurRepository formateurRepository;

    // Repository pour effectuer des opérations CRUD sur l'entité TypeFormation
    @Autowired
    private TypeFormationRepository typeFormationRepository;


    // Liste utilisée pour stocker les objets Formation pré-chargés en base pour les tests
    private List<TypeFormation> listeTypeFormationDB = new ArrayList<>();


    /**
     * Méthode exécutée avant chaque test pour initialiser la base de données
     * avec plusieurs objets TypeFormation persistés.
     */
    @BeforeEach
    public void initTypeFormation() {

        listeTypeFormationDB.add(TypeFormation
                .builder()
                .libelleTypeFormation("Présentiel")
                .build());

        listeTypeFormationDB.add(TypeFormation
                .builder()
                .libelleTypeFormation("Distanciel")
                .build()
        );


        // Persiste chaque type de formation et vérifie que l'identifiant a bien été généré
        listeTypeFormationDB.forEach(tf -> {
            typeFormationRepository.save(tf);
            assertThat(tf.getIdTypeFormation()).isGreaterThan(0);
        });
    }

    /**
     * Teste la sauvegarde d'un formateur et l'association avec un type de formation (ManyToMany).
     * Vérifie que la relation est bien persistée et que les données sont cohérentes.
     */
    @Test
    public void test_save_formateur_typeformation() {

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


        // Sélectionne le type de formation dont le libellé est distanciel
        final List<TypeFormation> typesFormations = listeTypeFormationDB
                .stream()
                .filter(item -> item.getLibelleTypeFormation().equals("Distanciel"))
                .toList();

        // Vérifie que la liste retournée n'est pas nulle, ni vide et contient bien un type de formation
        assertThat(typesFormations).isNotNull();
        assertThat(typesFormations).isNotEmpty();
        assertThat(typesFormations.size()).isEqualTo(1);

        // Association entre le formateur et la liste des types de formations
        formateurCoco.setTypeFormationDispensee(typesFormations);
        log.info(formateurCoco.getTypeFormationDispensee().toString());

        // Sauvegarde du formateur en base via le repository
        final Formateur formateurCocoDB = formateurRepository.save(formateurCoco);

        // Vérification de l'identifiant du formateur
        assertThat(formateurCocoDB.getIdPersonne()).isGreaterThan(0);

        // Vérifie que l'association ManyToMany est bien persistée
        assertThat(formateurCocoDB.getTypeFormationDispensee()).isNotNull();
        assertThat(formateurCocoDB.getTypeFormationDispensee()).isNotEmpty();
        assertThat(formateurCocoDB.getTypeFormationDispensee().size()).isEqualTo(1);
        log.info(formateurCocoDB.toString());

    }

    /**
     * Teste la suppression d'un formateur et vérifie que les formations associées
     * ne sont pas supprimées (pas de suppression en cascade).
     */
    @Test
    public void test_delete_formateur_typeformation() {

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


        // Sélectionne le type de formation dont le libellé est distanciel
        final List<TypeFormation> typesFormations = listeTypeFormationDB
                .stream()
                .filter(item -> item.getLibelleTypeFormation().equals("Distanciel") || item.getLibelleTypeFormation().equals("Présentiel"))
                .toList();

        // Vérifie que la liste n'est ni nulle ni vide et contient deux formations
        assertThat(typesFormations).isNotNull();
        assertThat(typesFormations).isNotEmpty();
        assertThat(typesFormations.size()).isEqualTo(2);

        // Association entre le formateur et la liste des types de formations
        formateurCoco.setTypeFormationDispensee(typesFormations);
        log.info(formateurCoco.getTypeFormationDispensee().toString());

        // Persistance du formateur dans la base de test
        final Formateur formateurCocoDB = formateurRepository.save(formateurCoco);


        // Vérifie que le formateur a bien un identifiant et les types de formation sont associés
        assertThat(formateurCocoDB.getIdPersonne()).isGreaterThan(0);
        assertThat(formateurCocoDB.getTypeFormationDispensee()).isNotNull();
        assertThat(formateurCocoDB.getTypeFormationDispensee()).isNotEmpty();

        // Récupère la liste des types de formations associés depuis la base
        List<TypeFormation> typeFormationDispenseDB = formateurCocoDB.getTypeFormationDispensee();
        assertThat(typeFormationDispenseDB).isNotNull();
        assertThat(typeFormationDispenseDB).isNotEmpty();
        assertThat(typeFormationDispenseDB.size()).isEqualTo(2);

        // Suppression du formateur via le repository
        formateurRepository.delete(formateurCoco);

        // Vérification que l'entité a été supprimée
        final Formateur formateurCocoDB2 = entityManager.find(Formateur.class, formateurCoco.getIdPersonne());
        assertNull(formateurCocoDB2);

        // Vérifie que les types de formations associés existent toujours en base (pas de suppression en cascade)
        List<TypeFormation> typeFormationsDispenseesDB2 = typeFormationRepository.findAll();
        assertThat(typeFormationsDispenseesDB2).isNotNull();
        assertThat(typeFormationsDispenseesDB2).isNotEmpty();
        assertThat(typeFormationsDispenseesDB2.size()).isEqualTo(2);


    }
}
