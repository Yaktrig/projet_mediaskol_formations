package fr.mediaskol.projet.heritage;

import fr.mediaskol.projet.bo.Personne;
import fr.mediaskol.projet.bo.apprenant.Apprenant;
import fr.mediaskol.projet.bo.formateur.Formateur;
import fr.mediaskol.projet.bo.salarie.Salarie;
import fr.mediaskol.projet.dal.ApprenantRepository;
import fr.mediaskol.projet.dal.FormateurRepository;
import fr.mediaskol.projet.dal.PersonneRepository;
import fr.mediaskol.projet.dal.SalarieRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static fr.mediaskol.projet.bo.apprenant.StatutNumPasseport.NUM_PASSEPORT_A_CREER;
import static org.assertj.core.api.Assertions.assertThat;


/**
 * Test qui permet de vérifier l'héritage entre
 * Personne et Apprenant
 * Personne et Formateur
 * Personne et Salarie
 */
@Slf4j
@DataJpaTest
public class TestHeritageDePersonne {
    // Permet des opérations avancées sur l'EntityManager pour les tests
    @Autowired
    private TestEntityManager entityManager;

    // Repository Spring Data JPA pour Personne
    @Autowired
    private PersonneRepository personneRepository;

    // Repository Spring Data JPA pour Apprenant
    @Autowired
    private ApprenantRepository apprenantRepository;

    // Repository Spring Data JPA pour Formateur
    @Autowired
    private FormateurRepository formateurRepository;

    // Repository Spring Data JPA pour Salarie
    @Autowired
    private SalarieRepository salarieRepository;


    @BeforeEach
    public void initDB() {

        List<Personne> personnes = new ArrayList<>();
        personnes.add(Personne
                .builder()
                .nom("L'Ourson")
                .prenom("Winnie")
                .email("winnie.lourson@gmail.fr")
                .build());


        personnes.add(Apprenant
                .builder()
                .nom("Le tigre")
                .prenom("Tigrou")
                .email("tigrou.letigre@gmail.fr")
                .apprenantActif(true)
                .dateNaissance(LocalDate.parse("2000-12-12"))
                .statutNumPasseport(NUM_PASSEPORT_A_CREER)
                .build());


        personnes.add(Formateur
                .builder()
                .nom("Lapin")
                .prenom("Coco")
                .email("coco.lapin@gmail.fr")
                .statutFormateur("AE") // accès via la méthode, pas le champ
               // .adresse(adresseCoco)
                .build());

        personnes.add(Salarie
                .builder()
                .nom("L'âne")
                .prenom("Bourriquet")
                .email("bourriquet.lane@gmail.fr")
                .roleSalarie("Admin")
                .couleurSalarie("#33daff")
                .statutInscription(true)
                .build());

        // Persistence de la liste dans la BDD
        personnes.forEach(p -> {
            entityManager.persist(p);
        });
    }


    // Tests qui retourne 4 personnes créées
    @Test
    public void test_findAll_Personne() {

        // Récupération de l'ensemble des Personnes - en initialisant la BDD
        final List<Personne> personnes = personneRepository.findAll();

        // Vérification que l'objet n'est pas null
        assertThat(personnes).isNotNull();

        // Vérification s'il y a bien 4 personnes d'insérées
        Assertions.assertThat(personnes.size()).isEqualTo(4);

        // Log pour visualiser l'objet persisté
        log.info(personnes.toString());

    }

    // Retourne uniquement l'apprenant créé
    @Test
    public void test_findAll_apprenant() {

        // Récupération de l'ensemble des apprenants
        final List<Apprenant> apprenants = apprenantRepository.findAll();

        // vérification
        Assertions.assertThat(apprenants).isNotNull();
        Assertions.assertThat(apprenants).isNotEmpty();
        Assertions.assertThat(apprenants.size()).isEqualTo(1);
        log.info(apprenants.toString());
    }

    // Retourne uniquement le formateur créé
    @Test
    public void test_findAll_formateur() {

        // Récupération de l'ensemble des formateurs
        final List<Formateur> formateurs = formateurRepository.findAll();

        // vérification
        Assertions.assertThat(formateurs).isNotNull();
        Assertions.assertThat(formateurs).isNotEmpty();
        Assertions.assertThat(formateurs.size()).isEqualTo(1);
        log.info(formateurs.toString());
    }

    // Retourne uniquement le salarié créé
    @Test
    public void test_findAll_salarie() {

        // Récupération de l'ensemble des salaries
        final List<Salarie> salaries = salarieRepository.findAll();

        // vérification
        Assertions.assertThat(salaries).isNotNull();
        Assertions.assertThat(salaries).isNotEmpty();
        Assertions.assertThat(salaries.size()).isEqualTo(1);
        log.info(salaries.toString());
    }


}
























