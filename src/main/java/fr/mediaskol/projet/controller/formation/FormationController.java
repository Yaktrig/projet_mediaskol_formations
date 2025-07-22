package fr.mediaskol.projet.controller.formation;

import fr.mediaskol.projet.bll.FormationService;
import fr.mediaskol.projet.bo.formation.Formation;
import fr.mediaskol.projet.dto.FormationResponseDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur qui permet :
 * d'afficher les formations
 * d'ajouter une formation
 * de rechercher une formation ?
 * de supprimer une formation
 * Utilisation de lombok pour injecter le service à la place d'un constructeur
 */

@AllArgsConstructor
@RestController
@RequestMapping("/mediaskolFormation/formations")
public class FormationController {

    /**
     * Injection de dépendances pour aller chercher le service qui correspond aux formations
     */
    private FormationService formationService;

    /**
     * Retourne la liste des formations en Json dans l'url "mediaskolFormation/formations en méthode GET
     */
    @GetMapping
    public ResponseEntity<?> afficherToutesLesFormations() {

        final List<Formation> formations = formationService.chargerToutesFormations();

        if (formations == null || formations.isEmpty()) {
            // Statut 204 : No content - Pas de body car rien à afficher
            return ResponseEntity.noContent().build();
        }

        // Sinon, on retourne Statut 200 : Ok + dans le body les formations
        List<FormationResponseDTO> formationResponseDTOS = formations.stream()
                .map(FormationResponseDTO::new)
                .toList();

        return ResponseEntity.ok(formationResponseDTOS);
    }


    /**
     * Retourne un ou des formations en Json dans l'url "mediaskolFormation/formations/recherche
     * selon le critère saisi dans le champ
     * Todo nécessaire ?
     */
//    @GetMapping("/recherche")
//    public ResponseEntity<List<Formation>> rechercheFormations(@RequestParam String formationRecherchee) {
//
//        final List<Formation> formationsRecherchees = formationService.rechercheFormations(formationRecherchee);
//
//        if (formationsRecherchees == null || formationsRecherchees.isEmpty()) {
//            // Statut 204 : No content - Pas de body car rien à afficher
//            return ResponseEntity.noContent().build();
//        }
//        // Sinon, on retourne Statut 200 : Ok + dans le body les formations
//
//        return ResponseEntity.ok(formationsRecherchees);
//
//    }

    /**
     * Méthode qui permet de créer une nouvelle formation dans la base de données
     */
    @PostMapping
    @Valid
    public ResponseEntity<?> ajouterFormation(@Valid @RequestBody Formation formation) {

        // La formation ne doit pas être nulle
        if (formation == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("La formation à ajouter est obligatoire");
        }

        // La formation ne doit pas avoir d'identifiant de saisi
        if (formation.getIdFormation() != null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Impossible de sauvegarder une formation");
        }
        try {
            formationService.ajouterFormation(formation, formation.getTypeFormation());
            return ResponseEntity.ok(formation);
        } catch (RuntimeException e) {
            // Erreur BLL ou DAL
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

    /**
     * Modifier une formation
     */
    @PutMapping
    @Valid
    public ResponseEntity<?> modifierFormation(@Valid @RequestBody Formation formation) {

        // La formation ne doit pas être nulle - l'identifiant ne pas être nul ou inférieur ou égal à 0.
        if (formation == null || formation.getIdFormation() == null || formation.getIdFormation() <= 0) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("L'identifiant de la formation et la formation sont obligatoires.");
        }

        try {
            formationService.ajouterFormation(formation, formation.getTypeFormation());
            return ResponseEntity.ok(formation);
        } catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

    /**
     * Supprimer une formation
     */
    @DeleteMapping("/{id}")
    @Valid
    public ResponseEntity<?> supprimerFormation(@PathVariable("id") String idInPath) {

        try {
            final int idFormation = Integer.parseInt(idInPath);
            formationService.supprimerFormation(idFormation);
            return ResponseEntity.ok("La formation " + idFormation + " est supprimée de la base de données.");
        } catch(NumberFormatException e){
            // Statut 406 : No acceptable
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Votre identifiant n'est pas un entier.");
        } catch(RuntimeException e){
            // Erreur BLL ou DAL
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

}
