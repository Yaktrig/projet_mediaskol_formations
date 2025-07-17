package fr.mediaskol.projet.controller.apprenant;

import fr.mediaskol.projet.bll.ApprenantService;
import fr.mediaskol.projet.bo.apprenant.Apprenant;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

/**
 * Contrôleur qui permet :
 * d'afficher les apprenants
 * d'ajouter un apprenant
 * de rechercher un ou des apprenants *
 * Utilisation de lombok pour injecter le service à la place d'un constructeur
 */

@AllArgsConstructor
@RestController
@RequestMapping("/mediaskolFormation/apprenants")
public class ApprenantController {

    /**
     * Injection de dépendances pour aller chercher le service qui correspond aux apprenants
     */
    private ApprenantService apprenantService;

    /**
     * Retourne la liste des apprenants en Json dans l'url "mediaskolFormation/apprenants en méthode GET
     */
    @GetMapping
    public ResponseEntity<?> afficherTousLesApprenants() {

        final List<Apprenant> apprenants = apprenantService.chargerTousApprenants();

        if (apprenants == null || apprenants.isEmpty()) {
            // Statut 204 : No content - Pas de body car rien à afficher
            return ResponseEntity.noContent().build();
        }

        // Sinon, on retourne Statut 200 : Ok + dans le body les apprenants
        return ResponseEntity.ok(apprenants);
    }

    /**
     * Retourne un ou des apprenants en Json dans l'url "mediaskolFormation/apprenants/recherche
     * selon le critère saisi dans le champ
     */
    @GetMapping("/recherche")
    public ResponseEntity<?> rechercheApprenants(@RequestParam(required = false) String nom,
                                                 @RequestParam(required = false) String email,
                                                 @RequestParam(required = false) LocalDate dateNaissance,
                                                 @RequestParam(required = false) String ville,
                                                 @RequestParam(required = false) Long numDepartement
    ) {

        final List<Apprenant> apprenantsRecherches = apprenantService.rechercheApprenants(nom, email, dateNaissance, numDepartement, ville);

        if (apprenantsRecherches == null || apprenantsRecherches.isEmpty()) {
            // Statut 204 : No content - Pas de body car rien à afficher
            return ResponseEntity.noContent().build();
        }
        // Sinon, on retourne Statut 200 : Ok + dans le body les apprenants
        return ResponseEntity.ok(apprenantsRecherches);


    }


}
