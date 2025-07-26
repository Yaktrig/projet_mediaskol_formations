package fr.mediaskol.projet.controller.apprenant;

import fr.mediaskol.projet.bll.apprenant.ApprenantService;
import fr.mediaskol.projet.bo.apprenant.Apprenant;
import fr.mediaskol.projet.dto.apprenant.ApprenantResponseDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur qui permet :
 * d'afficher les apprenants
 * d'ajouter un apprenant
 * de rechercher un ou des apprenants avec saisie libre
 * de supprimer un apprenant
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
     * Retourne un apprenant par rapport à son identifiant
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> rechercherApprenantParId(@PathVariable("id") String idInPath) {

        try {
            long id = Long.parseLong(idInPath);
            final Apprenant apprenant = apprenantService.chargerApprenantParId(id);
            return ResponseEntity.ok(apprenant);
        } catch (NumberFormatException e) {
            // Statut 406 : No Acceptable
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Votre identifiant n'est pas valide");
        } catch (RuntimeException e) {
            // Statut 404 : Not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

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
        //return ResponseEntity.ok(apprenants);
        // Conversion liste Apprenant -> liste ApprenantDTO
        List<ApprenantResponseDTO> apprenantResponseDTOS = apprenants.stream()
                .map(ApprenantResponseDTO::new)
                .toList();

        return ResponseEntity.ok(apprenantResponseDTOS);
    }

    /**
     * Retourne un ou des apprenants en Json dans l'url "mediaskolFormation/apprenants/recherche
     * selon le critère saisi dans le champ
     */
    @GetMapping("/recherche")
    public ResponseEntity<List<Apprenant>> rechercheApprenants(@RequestParam String termeRecherche) {

        final List<Apprenant> apprenantsRecherches = apprenantService.rechercheApprenants(termeRecherche);

        if (apprenantsRecherches == null || apprenantsRecherches.isEmpty()) {
            // Statut 204 : No content - Pas de body car rien à afficher
            return ResponseEntity.noContent().build();
        }
        // Sinon, on retourne Statut 200 : Ok + dans le body les apprenants

        return ResponseEntity.ok(apprenantsRecherches);

    }



    /**
     * Méthode qui permet de créer un nouvel apprenant dans la base de données
     */
    @PostMapping
    @Valid
    public ResponseEntity<?> ajouterApprenant(@Valid @RequestBody Apprenant apprenant) {

        // L'apprenant ne doit pas être nul
        if (apprenant == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("L'apprenant à ajouter est obligatoire");
        }

        // L'apprenant ne doit pas avoir d'identifiant de saisi
        if (apprenant.getIdPersonne() != null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Impossible de sauvegarder un apprenant");
        }
        try {
            apprenantService.ajouterApprenant(apprenant, apprenant.getAdresse(), apprenant.getTypesFormationSuivies());
            return ResponseEntity.ok(apprenant);
        } catch (RuntimeException e) {
            // Erreur BLL ou DAL
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }


    /**
     * Modifier un apprenant
     */
    @PutMapping
    @Valid
    public ResponseEntity<?> modifierApprenant(@Valid @RequestBody Apprenant apprenant) {

        // L'apprenant ne doit pas être nul - l'identifiant ne pas être nul ou inférieur ou égal à 0
        if (apprenant == null || apprenant.getIdPersonne() == null || apprenant.getIdPersonne() <= 0) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("L'identifiant de l'apprenant et l'apprenant sont obligatoires.");
        }

        try {
            apprenantService.ajouterApprenant(apprenant, apprenant.getAdresse(), apprenant.getTypesFormationSuivies());
                return ResponseEntity.ok(apprenant);
            } catch(RuntimeException e){
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
            }
    }


    /**
     * Supprimer un apprenant
     */
    @DeleteMapping("/{id}")
    @Valid
    public ResponseEntity<?> supprimerApprenant(@PathVariable("id") String idInPath) {


        try {
            final int idApprenant = Integer.parseInt(idInPath);
            apprenantService.supprimerApprenant(idApprenant);
            return ResponseEntity.ok("Apprenant " + idApprenant + " est supprimé de la base de données.");
        } catch(NumberFormatException e){
            // Statut 406 : No acceptable
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Votre identifiant n'est pas un entier.");
        } catch(RuntimeException e){
            // Erreur BLL ou DAL
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }




}

