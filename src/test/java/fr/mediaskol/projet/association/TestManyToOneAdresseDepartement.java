package fr.mediaskol.projet.association;

import fr.mediaskol.projet.bo.adresse.Adresse;
import fr.mediaskol.projet.bo.departement.Departement;
import fr.mediaskol.projet.dal.AdresseRepository;
import fr.mediaskol.projet.dal.DepartementRepository;
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
 * Entre les entités Adresse et Departement
 */

// Configure un contexte Spring Boot limité à la couche JPA
@Slf4j
@DataJpaTest
public class TestManyToOneAdresseDepartement {

    // Permet des opérations avancées sur l'EntityManager pour les tests
    @Autowired
    private TestEntityManager entityManager;

    // Comme il n'y a pas de cascade dans notre @ManyToOne d'Adresse vers Departement
    // on doit appeler le repository pour créer ici nos départements
    @Autowired
    DepartementRepository departementRepository;

    @Autowired
    AdresseRepository adresseRepository;

    private Departement cotesdarmor;
    private Departement finistere;
    private Departement illeetvilaine;
    private Departement morbihan;

    @BeforeEach
    public void initDepartement() {
        cotesdarmor = Departement
                .builder()
                .idDepartement(22L)
                .nomDepartement("Côtes d'Armor")
                .couleurDepartement("#9AC8EB")
                .region("Bretagne")
                .build();

        finistere = Departement
                .builder()
                .idDepartement(29L)
                .nomDepartement("Finistère")
                .couleurDepartement("#CE6A6B")
                .region("Bretagne")
                .build();

        illeetvilaine = Departement
                .builder()
                .idDepartement(35L)
                .nomDepartement("Ille et Vilaine")
                .couleurDepartement("#F7F6CF")
                .region("Bretagne")
                .build();

        morbihan = Departement
                .builder()
                .idDepartement(56L)
                .nomDepartement("Morbihan")
                .couleurDepartement("#D5CD90")
                .region("Bretagne")
                .build();

        departementRepository.save(cotesdarmor);
        departementRepository.save(finistere);
        departementRepository.save(illeetvilaine);
        departementRepository.save(morbihan);

    }

    // Sauvegarde d'une adresse et de son département
    @Test
    public void test_save_adresse(){

        // Création d'une nouvelle adresse avec le builder Lombok
        final Adresse adresseBrest = Adresse
                .builder()
                .rue("44, boulevard de Lagarde")
                .codePostal("29200")
                .ville("Brest")
                .build();

        // Association ManyToOne entre l'adresse et le département
        adresseBrest.setDepartement(finistere);

        // Sauvegarde de l'adresse en base via le repository
        final Adresse adresseBrestDB = adresseRepository.save(adresseBrest);

        // Log pour visualiser l'objet persisté
        log.info(adresseBrestDB.toString());

        // Vérification de la cascade de l'association
        // Vérifie que l'objet retourné n'est pas null
        assertThat(adresseBrestDB.getIdAdresse()).isGreaterThan(0);
        assertThat(adresseBrestDB.getDepartement()).isNotNull();
        assertThat(adresseBrestDB.getDepartement()).isEqualTo(finistere);

    }

    //
    @Test
    public void test_find_all(){

        // Récupération des données de la méthode jeuDeDonnees()
        List<Adresse> adresses = jeuDeDonnees();

        // Sauvegarde du jeu de données dans la base
        adresses.forEach(adresse -> {
            adresseRepository.save(adresse);
            assertThat(adresse.getIdAdresse()).isGreaterThan(0);
        });

        // Vérifie l'identifiant des adresses
        final List<Adresse> adressesDB = adresseRepository.findAll();
        adressesDB.forEach(adresse -> {
            assertThat(adresse.getIdAdresse()).isGreaterThan(0);

            // Vérification du département
            assertThat(adresse.getDepartement()).isNotNull();
        });
    }



    @Test
    public void test_delete_adresse(){

        // Création d'une nouvelle adresse avec le builder Lombok
        final Adresse adresseBrest = Adresse
                .builder()
                .rue("44, boulevard de Lagarde")
                .codePostal("29200")
                .ville("Brest")
                .build();

        // Association ManyToOne entre l'adresse et le département
        adresseBrest.setDepartement(finistere);

       // Persistance de l'adresse dans la base de test
        final Adresse adresseBrestDB = adresseRepository.save(adresseBrest);
        entityManager.flush();

        // Vérification s'il y a au moins un identifiant dans Adresse, s'il n'est pas null,
        // et si son département est égal au département finistere
        assertThat(adresseBrestDB.getIdAdresse()).isGreaterThan(0);
        assertThat(adresseBrestDB.getDepartement()).isNotNull();
        assertThat(adresseBrestDB.getDepartement()).isEqualTo(finistere);

        // Suppression de l'adresse
        adresseRepository.delete(adresseBrestDB);

        // Vérifie que l'entité Adresse n'est plus présente en base (doit être null)
        Adresse adresseBrestDB2 = entityManager.find(Adresse.class, adresseBrestDB.getIdAdresse());
        assertNull(adresseBrestDB2);


        // Vérifie que les départements associés existent toujours en base (pas de suppression en cascade)
        List<Departement> departements = departementRepository.findAll();
        assertThat(departements).isNotNull();
        assertThat(departements).isNotEmpty();
        assertThat(departements.size()).isEqualTo(4);


    }




    // Création d'un jeu de données d'adresses
    private List<Adresse> jeuDeDonnees() {
        List<Adresse> adresses = new ArrayList<>();
        adresses.add(Adresse.builder()
                .departement(illeetvilaine)
                .rue("Terre-Plein du Naye")
                .codePostal("35400")
                .ville("Saint-Malo")
                .build());
        adresses.add(Adresse.builder()
                .departement(morbihan)
                .rue("Lieu Dit Kerpont, Les Hauts De Kerousse - Lann Sevelin")
                .codePostal("56600")
                .ville("Lanester")
                .build());
        adresses.add(Adresse.builder()
                .departement(cotesdarmor)
                .rue("78 Boulevard Atlantique")
                .codePostal("22000")
                .ville("Saint-Brieuc")
                .build());
        return adresses;
    }

}
