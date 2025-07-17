package fr.mediaskol.projet.bll;

import fr.mediaskol.projet.bo.departement.Departement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


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

    @Autowired
    DepartementService departementService;

}
