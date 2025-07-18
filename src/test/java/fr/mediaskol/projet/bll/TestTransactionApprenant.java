package fr.mediaskol.projet.bll;

import fr.mediaskol.projet.bo.adresse.Adresse;
import fr.mediaskol.projet.bo.apprenant.Apprenant;
import fr.mediaskol.projet.bo.apprenant.StatutNumPasseport;
import fr.mediaskol.projet.bo.departement.Departement;
import fr.mediaskol.projet.dal.adresse.AdresseRepository;
import fr.mediaskol.projet.dal.apprenant.ApprenantRepository;
import fr.mediaskol.projet.dal.departement.DepartementRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * Test qui permet de passer directement par la base de données qui a été configurée
 * La bdd va se recréer à chaque fois
 * Ici, on teste la création d'un apprenant
 */
@Slf4j
@SpringBootTest
public class TestTransactionApprenant {

    // On injecte automatiquement un bean (service ou repository) géré par Spring dans le test
    // Evite de créer manuellement une instance
    @Autowired
    private ApprenantService apprenantService;

    @Autowired
    private ApprenantRepository apprenantRepository;

    @Autowired
    private AdresseService adresseService;

    @Autowired
    private AdresseRepository adresseRepository;

    @Autowired
    private DepartementRepository departementRepository;


    @Test
    public void test_transaction_OK() {


        // Création d'une nouvelle adresse
        final Adresse adresseBrest = Adresse
                .builder()
                .rue("44, boulevard de Lagarde")
                .codePostal("29200")
                .ville("Brest")
                .build();


        // Création d'un nouvel apprenant avec le builder Lombok
        final Apprenant tigrou = Apprenant
                .builder()
                .nom("Le tigre")
                .prenom("Tigrou")
                .email("tigrou.letigre@gmail.fr")
                .numPortable("0600000000")
                .dateNaissance(LocalDate.parse("2000-12-12"))
                .numPasseport("A123456")
                .commentaireApprenant("Fait des bonds partout")
                .statutNumPasseport(StatutNumPasseport.NUM_PASSEPORT_A_CREER)
                .build();

        // Persistence dans la base de données
        apprenantService.ajouterApprenant(tigrou, adresseBrest, null);

        // L'apprenant a été enregistré
        assertThat(tigrou.getIdPersonne()).isGreaterThan(0);
        log.info(tigrou.toString());

        // L'adresse a été enregistrée
        assertThat(adresseBrest.getIdAdresse()).isGreaterThan(0);
        log.info(adresseBrest.toString());

    }


    @Test
    public void test_transaction_Rollback() {


        // Création d'une nouvelle adresse
        final Adresse adresseLanester = Adresse
                .builder()
                .rue("Lieu Dit Kerpont, Les Hauts De Kerousse - Lann Sevelin")
                .codePostal("56600")
                .ville("Lanester")
                .build();


        // Création d'un nouvel apprenant avec le builder Lombok
        final Apprenant tigrou2 = Apprenant
                .builder()
                .nom("Le tigre2")
                .prenom("Tigrou2")
                .email("tigrou2.letigre@gmail.fr")
                .numPortable("0600000000")
                .dateNaissance(LocalDate.parse("2000-12-12"))
                .commentaireApprenant("Fait des bonds partout")
                .numPasseport("A123456") // Pour qu'une exception soit levée, je mets le même numéro de passeport, qui doit être unique.
                .statutNumPasseport(StatutNumPasseport.NUM_PASSEPORT_A_CREER)
                .build();


        assertThrows(RuntimeException.class, () -> apprenantService.ajouterApprenant(tigrou2, adresseLanester, null));

        // Vérification des données en base
        List<Adresse> adresses = adresseRepository.findAll();
        assertThat(adresses).isNotNull();

        Adresse adresseDB = adresses
                .stream()
                .filter(item -> item.getVille().equals("Lanester"))
                .findAny()
                .orElse(null);

        assertThat(adresseDB).isNull();


        List<Apprenant> apprenants = apprenantRepository.findAll();
        assertThat(apprenants).isNotNull();
        Apprenant apprenantDB = apprenants
                .stream()
                .filter(item -> item.getNom().equals("Le tigre2"))
                .findAny()
                .orElse(null);
        assertThat(apprenantDB).isNull();

    }
}
