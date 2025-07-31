package fr.mediaskol.projet.controller.departement;

import fr.mediaskol.projet.bll.departement.DepartementService;
import fr.mediaskol.projet.bo.departement.Departement;
import fr.mediaskol.projet.dto.adresse.DepartementDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/mediaskolFormation/departements")
public class DepartementController {

    /**
     * Injection des dépendances pour aller chercher le service FormateurService qui correspond aux formateurs
     */
    private DepartementService departementService;

    /**
     * Retourne la liste des départements en Json dans l'url "mediaskolFormation/formateurs en méthode GET
     */
    @GetMapping("/{bretagne}")
    public ResponseEntity<?> afficherDepartementsBretagnes(){

        final List<Departement> departements = departementService.chargerDepartementDeBretagne("Bretagne");

        if (departements == null || departements.isEmpty()) {
            // Statut 204 : No content - Pas de body car rien à afficher
            return ResponseEntity.noContent().build();
        }

        // Conversion liste Départements -> liste DepartementDTO
        List<DepartementDTO> departementRespDTOS = departements.stream()
                .map(DepartementDTO::new)
                .toList();

        // Sinon, on retourne Statut 200 : Ok + dans le body les formateurs
        return ResponseEntity.ok(departementRespDTOS);
    }

}
