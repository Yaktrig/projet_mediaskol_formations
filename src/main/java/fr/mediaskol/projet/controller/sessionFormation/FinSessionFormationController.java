package fr.mediaskol.projet.controller.sessionFormation;

import fr.mediaskol.projet.bll.sessionFormation.FinSessionFormationService;
import fr.mediaskol.projet.bo.sessionFormation.FinSessionFormation;
import fr.mediaskol.projet.dto.sessionFormation.FinSessionFormationRespDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur qui permet :
 * d'afficher les fins de sessions de formation
 * d'ajouter une fin de session de formation
 * de rechercher une fin de session de formation par son id
 * de rechercher une ou plusieurs fins de sessions de formation
 * de supprimer une fin de session de formation
 * Utilisation de lombok pour injecter le service à la place d'un constructeur
 */

@AllArgsConstructor
@RestController
@RequestMapping("/mediaskolFormation/finsSessionsFormations")
public class FinSessionFormationController {

    /**
     * Injection de dépendances pour aller chercher le service qui correspond aux sessions de formation
     */
    private FinSessionFormationService finSessionFormationService;

    /**
     * Retourne la liste des fins de sessions de formation en Json dans l'url "mediaskolFormation/finsSessionsFormations en méthode GET
     */
    @GetMapping
    public ResponseEntity<?> afficherTousLesFinsSessionsFormations() {

        final List<FinSessionFormation> finSessionsFormations = finSessionFormationService.chargerToutesFinSessionFormations();

        if (finSessionsFormations == null || finSessionsFormations.isEmpty()) {
            // Statut 204 : No content - Pas de body car rien à afficher
            return ResponseEntity.noContent().build();
        }

        // Sinon, on retourne Statut 200 : Ok + dans le body les finSessionFormation
        // Conversion liste FinSessionFormation -> liste FinSessionFormationRespDTO
        List<FinSessionFormationRespDTO> finSessionFormationRespDTOS = finSessionsFormations.stream()
                .map(FinSessionFormationRespDTO::new)
                .toList();

        return ResponseEntity.ok(finSessionFormationRespDTOS);
    }


    /**
     * Retourne une fin de session de formation par rapport à son identifiant
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> rechercherFinSessionFormationParId(@PathVariable("id") String idInPath) {

        try {
            long id = Long.parseLong(idInPath);
            final FinSessionFormation finSessionFormation = finSessionFormationService.chargerFinSessionFormationParId(id);

            FinSessionFormationRespDTO finSessionFormationDto = new FinSessionFormationRespDTO(finSessionFormation);
            return ResponseEntity.ok(finSessionFormationDto);
        } catch (NumberFormatException e) {
            // Statut 406 : No Acceptable
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Votre identifiant n'est pas valide");
        } catch (RuntimeException e) {
            // Statut 404 : Not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

    /**
     * Méthode qui permet de créer une nouvelle fin de session de formation dans la base de données
     */
    @PostMapping
    @Valid
    public ResponseEntity<?> ajouterFinSessionFormation(@Valid @RequestBody FinSessionFormation finSessionFormation) {

        // La fin de session de formation ne doit pas être nulle
        if (finSessionFormation == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("La fin de session de formation à ajouter " +
                    "est obligatoire");
        }

        // La fin de session de formation ne doit pas avoir d'identifiant de saisi
        if (finSessionFormation.getIdFinSessionFormation() != null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Impossible de sauvegarder une fin de session " +
                    "de formation");
        }
        try {
            finSessionFormationService.ajouterFinSessionFormation(finSessionFormation);
            return ResponseEntity.ok(finSessionFormation);
        } catch (RuntimeException e) {
            // Erreur BLL ou DAL
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

    /**
     * Modifier une fin de session de formation
     */
    @PutMapping
    @Valid
    public ResponseEntity<?> modifierFinSessionFormation(@Valid @RequestBody FinSessionFormation finSessionFormation) {

        // La fin de session de formation ne doit pas être nulle - l'identifiant ne pas être nul ou inférieur ou égal à 0
        if (finSessionFormation == null || finSessionFormation.getIdFinSessionFormation() == null ||
                finSessionFormation.getIdFinSessionFormation() <= 0) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("L'identifiant de la finSessionFormation et la " +
                    "finSessionFormation sont obligatoires.");
        }

        try {
            finSessionFormationService.ajouterFinSessionFormation(finSessionFormation);
            return ResponseEntity.ok(finSessionFormation);
        } catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

    /**
     * Supprimer une fin de session de formation
     */
    @DeleteMapping("/{id}")
    @Valid
    public ResponseEntity<?> supprimerFinSessionFormation(@PathVariable("id") String idInPath) {


        try {
            final int idFinSessionFormation = Integer.parseInt(idInPath);
            finSessionFormationService.supprimerFinSessionFormation(idFinSessionFormation);
            return ResponseEntity.ok("La fin de session formation " + idFinSessionFormation + " est supprimée de la base de données.");
        } catch(NumberFormatException e){
            // Statut 406 : No acceptable
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Votre identifiant n'est pas un entier.");
        } catch(RuntimeException e){
            // Erreur BLL ou DAL
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

}
