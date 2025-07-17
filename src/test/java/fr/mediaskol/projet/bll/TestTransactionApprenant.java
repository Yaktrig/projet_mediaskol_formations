package fr.mediaskol.projet.bll;

import fr.mediaskol.projet.bo.apprenant.Apprenant;
import fr.mediaskol.projet.bo.apprenant.StatutNumPasseport;
import fr.mediaskol.projet.bo.departement.Departement;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;


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
    AdresseService adresseService;


    @Test
    public void test_save(){



        // Création d'un nouvel apprenant avec le builder Lombok
        final Apprenant tigrou = Apprenant
                .builder()
                .nom("Le tigre")
                .prenom("Tigrou")
                .email("tigrou.letigre@gmail.fr")
                .numPortable("0600000000")
                .dateNaissance(LocalDate.parse("2000-12-12"))
                .apprenantActif(true)
                .numPasseport("A123456")
                .commentaireApprenant("Fais des bonds partout")
                .statutNumPasseport(StatutNumPasseport.NUM_PASSEPORT_A_CREER)
                .build();

        // Persistence dans la base de données
        //final Apprenant tigrouDB = apprenantService.save
    }

}
