package fr.mediaskol.projet.controller.apprenant;

import fr.mediaskol.projet.bll.ApprenantService;
import fr.mediaskol.projet.bo.adresse.Adresse;
import fr.mediaskol.projet.bo.apprenant.Apprenant;
import fr.mediaskol.projet.bo.formation.TypeFormation;
import fr.mediaskol.projet.dto.ApprenantResponseDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

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
        // Sinon, on retourne Statut 200 : Ok + dans le body les apprenants
        //return ResponseEntity.ok(apprenants);
        // Conversion liste Apprenant -> liste ApprenantDTO
        List<ApprenantResponseDTO> apprenantResponseDTOS = apprenantsRecherches.stream()
                .map(ApprenantResponseDTO::new)
                .toList();

        return ResponseEntity.ok(apprenantResponseDTOS);
        //return ResponseEntity.ok(apprenantsRecherches);


    }

    /**
     * TODO Ajouter un apprenant
     */
//    @PostMapping
//    @Valid
//    public ResponseEntity<?> ajoutApprenant(@Valid @RequestBody Apprenant apprenant, Adresse adresse, TypeFormation typeFormation) {
//
//        // L'apprenant ne doit pas être nul
//        if (apprenant == null) {
//            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("L'apprenant à ajouter est obligatoire");
//        }
//
//        // L'apprenant ne doit pas avoir d'identifiant de saisi
//        if (apprenant.getIdPersonne() != null) {
//            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Impossible de sauvegarder un apprenant");
//        }
//        try {
//            apprenantService.ajouterApprenant(apprenant, adresse, typeFormation);
//            return ResponseEntity.ok(apprenant);
//        } catch (RuntimeException e) {
//            // Erreur BLL ou DAL
//            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
//        }
//    }


    /**
     * Modifier un apprenant
     */
//    @PutMapping
//    @Valid
//    public ResponseEntity<?> modifierApprenant(@Valid @RequestBody Apprenant apprenant, Adresse adresse, TypeFormation typeFormation) {
//
//        // L'apprenant ne doit pas être nul - l'identifiant ne pas être nul ou inférieur ou égal à 0
//        if (apprenant == null || apprenant.getIdPersonne() == null || apprenant.getIdPersonne() <= 0) {
//            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("L'identifiant de l'appreannt et l'apprenant sont obligatoires.");
//        }

//        try {
//            apprenantService.ajouterApprenant(apprenant, adresse, typeFormation);
//                return ResponseEntity.ok(apprenant);
//            } catch(RuntimeException e){
//                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
//            }
    //}

    /**
     * TODO Désactiver un apprenant
     */

    /**
     * TODO Supprimer un apprenant
     */


}

