package fr.mediaskol.projet.association;

import fr.mediaskol.projet.bo.apprenant.Apprenant;
import fr.mediaskol.projet.bo.formation.TypeFormation;
import fr.mediaskol.projet.dal.ApprenantRepository;
import fr.mediaskol.projet.dal.TypeFormationRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static fr.mediaskol.projet.bo.apprenant.StatutNumPasseport.NUM_PASSEPORT_CREE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Test unitaire qui permet de valider l'association ManyToMany
 * Entre les entités Apprenant et TypeFormation
 */
@Slf4j
// Configure un contexte Spring Boot limité à la couche JPA
@DataJpaTest
public class ApprenantTypeFormationRelationTest {

    // Permet des opérations avancées sur l'EntityManager pour les tests - Injecte TestEntityManager
    @Autowired
    private TestEntityManager entityManager;

    // Repository pour effectuer des opérations CRUD sur l'entité Apprenant
    @Autowired
    private ApprenantRepository apprenantRepository;

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
     * Teste la sauvegarde d'un apprenant et l'association avec un type de formation (ManyToMany).
     * Vérifie que la relation est bien persistée et que les données sont cohérentes.
     */
    @Test
    public void test_save_apprenant_typeformation() {

        // Création d'un apprenant tigrou avec le builder Lombok
        final Apprenant tigrou = Apprenant
                .builder()
                .nom("Le tigre")
                .prenom("Tigrou")
                .email("tigrou.letigre@gmail.fr")
                .numPortable("0600000000")
                .dateNaissance(LocalDate.parse("2000-12-12"))
                .apprenantActif(true)
                .numPasseport("A123456")
                .statutNumPasseport(NUM_PASSEPORT_CREE)
                .commentaireApprenant("Fais des bonds partout")
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

        // Association entre l'apprenant et la liste des types de formations
        tigrou.setTypeFormationSuivie(typesFormations);
        log.info(tigrou.getTypeFormationSuivie().toString());

        // Sauvegarde de l'apprenant en base via le repository
        final Apprenant tigrouDB = apprenantRepository.save(tigrou);

        // Vérification de l'identifiant de l'apprenant
        assertThat(tigrouDB.getIdPersonne()).isGreaterThan(0);

        // Vérifie que l'association ManyToMany est bien persistée
        assertThat(tigrouDB.getTypeFormationSuivie()).isNotNull();
        assertThat(tigrouDB.getTypeFormationSuivie()).isNotEmpty();
        assertThat(tigrouDB.getTypeFormationSuivie().size()).isEqualTo(1);
        log.info(tigrouDB.toString());

    }

    /**
     * Teste la suppression d'un apprenant et vérifie que les formations associées
     * ne sont pas supprimées (pas de suppression en cascade).
     */
    @Test
    public void test_delete_apprenant_typeformation() {

        // Création d'un apprenant tigrou avec le builder Lombok
        final Apprenant tigrou = Apprenant
                .builder()
                .nom("Le tigre")
                .prenom("Tigrou")
                .email("tigrou.letigre@gmail.fr")
                .numPortable("0600000000")
                .dateNaissance(LocalDate.parse("2000-12-12"))
                .apprenantActif(true)
                .numPasseport("A123456")
                .statutNumPasseport(NUM_PASSEPORT_CREE)
                .commentaireApprenant("Fais des bonds partout")
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

        // Association entre l'apprenant et la liste des types de formations
        tigrou.setTypeFormationSuivie(typesFormations);
        log.info(tigrou.getTypeFormationSuivie().toString());

        // Persistance de l'apprenant dans la base de test
        final Apprenant tigrouDB = apprenantRepository.save(tigrou);


        // Vérifie que l'apprenant a bien un identifiant et les types de formation sont associés
        assertThat(tigrouDB.getIdPersonne()).isGreaterThan(0);
        assertThat(tigrouDB.getTypeFormationSuivie()).isNotNull();
        assertThat(tigrouDB.getTypeFormationSuivie()).isNotEmpty();

        // Récupère la liste des types de formations associés depuis la base
        List<TypeFormation> typeFormationSuivieDB = tigrouDB.getTypeFormationSuivie();
        assertThat(typeFormationSuivieDB).isNotNull();
        assertThat(typeFormationSuivieDB).isNotEmpty();
        assertThat(typeFormationSuivieDB.size()).isEqualTo(2);

        // Suppression d'un apprenant via le repository
        apprenantRepository.delete(tigrou);

        // Vérification que l'entité a été supprimée
        final Apprenant apprenantTigrouDB2 = entityManager.find(Apprenant.class, tigrou.getIdPersonne());
        assertNull(apprenantTigrouDB2);

        // Vérifie que les types de formations associés existent toujours en base (pas de suppression en cascade)
        List<TypeFormation> typeFormationsSuiviesDB2 = typeFormationRepository.findAll();
        assertThat(typeFormationsSuiviesDB2).isNotNull();
        assertThat(typeFormationsSuiviesDB2).isNotEmpty();
        assertThat(typeFormationsSuiviesDB2.size()).isEqualTo(2);


    }
}
