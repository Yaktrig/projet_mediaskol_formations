package fr.mediaskol.projet.controller.salle;

import fr.mediaskol.projet.bll.salle.SalleService;
import fr.mediaskol.projet.bo.salle.Salle;
import fr.mediaskol.projet.dto.salle.SalleResponseDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur qui permet :
 * d'afficher les salles
 * d'ajouter une salle
 * de rechercher une ou des salles avec saisie libre
 * de supprimer une salle
 * Utilisation de lombok pour injecter le service à la place d'un constructeur
 */

@AllArgsConstructor
@RestController
@RequestMapping("/mediaskolFormation/salles")
public class SalleController {

    /**
     * Injection de dépendances pour aller chercher le service qui correspond aux salles
     */
    private SalleService salleService;

    /**
     * Retourne la liste des salles en Json dans l'url "mediaskolFormation/salles en méthode GET
     */
    @GetMapping
    public ResponseEntity<?> afficherTousLesSalles() {

        final List<Salle> salles = salleService.chargerToutesSalles();

        if (salles == null || salles.isEmpty()) {
            // Statut 204 : No content - Pas de body car rien à afficher
            return ResponseEntity.noContent().build();
        }

        // Sinon, on retourne Statut 200 : Ok + dans le body les salles
        // return ResponseEntity.ok(salles);
        // Conversion liste Salle -> liste SalleDTO
        List<SalleResponseDTO> salleResponseDTOS = salles.stream()
                .map(SalleResponseDTO::new)
                .toList();

        return ResponseEntity.ok(salleResponseDTOS);
    }

    /**
     * Retourne une salle par rapport à son identifiant
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> rechercherSalleParId(@PathVariable("id") String idInPath) {

        try {
            long id = Long.parseLong(idInPath);
            final Salle salle = salleService.chargerSalleParId(id);
            return ResponseEntity.ok(salle);
        } catch (NumberFormatException e) {
            // Statut 406 : No Acceptable
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Votre identifiant n'est pas valide");
        } catch (RuntimeException e) {
            // Statut 404 : Not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Retourne un ou des salles en Json dans l'url "mediaskolFormation/salles/recherche
     * selon le critère saisi dans le champ
     */
  //  @GetMapping("/recherche")
//    public ResponseEntity<List<Salle>> rechercheSalles(@RequestParam String termeRecherche) {
//
//        final List<Salle> sallesRecherches = salleService.rechercheSalles(termeRecherche);
//
//        if (sallesRecherches == null || sallesRecherches.isEmpty()) {
//            // Statut 204 : No content - Pas de body car rien à afficher
//            return ResponseEntity.noContent().build();
//        }
//        // Sinon, on retourne Statut 200 : Ok + dans le body les salles
//
//        return ResponseEntity.ok(sallesRecherches);
//
//    }

    /**
     * Méthode qui permet de créer une nouvelle salle dans la base de données
     * @param salle
     */
    @PostMapping
    @Valid
    public ResponseEntity<?> ajouterSalle(@Valid @RequestBody Salle salle) {

        // La salle ne doit pas être nulle
        if (salle == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("La salle à ajouter est obligatoire");
        }

        // La salle ne doit pas avoir d'identifiant de saisi
        if (salle.getIdSalle() != null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Impossible de sauvegarder une salle");
        }
        try {
            salleService.ajouterSalle(salle, salle.getAdresse());
            return ResponseEntity.ok(salle);
        } catch (RuntimeException e) {
            // Erreur BLL ou DAL
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

    /**
     * Méthode qui permet de modifier une salle
     * @param salle
     */
    @PutMapping
    @Valid
    public ResponseEntity<?> modifierSalle(@Valid @RequestBody Salle salle) {

        // La salle ne doit pas être nulle - l'identifiant ne pas être nul ou inférieur ou égal à 0
        if (salle == null || salle.getIdSalle() == null || salle.getIdSalle() <= 0) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("L'identifiant de la salle et la salle sont obligatoires.");
        }

        try {
            salleService.ajouterSalle(salle, salle.getAdresse());
            return ResponseEntity.ok(salle);
        } catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

    /**
     * Supprimer un salle
     */
    @DeleteMapping("/{id}")
    @Valid
    public ResponseEntity<?> supprimerSalle(@PathVariable("id") String idInPath) {


        try {
            final int idSalle = Integer.parseInt(idInPath);
            salleService.supprimerSalle(idSalle);
            return ResponseEntity.ok("Salle " + idSalle + " est supprimée de la base de données.");
        } catch(NumberFormatException e){
            // Statut 406 : No acceptable
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Votre identifiant n'est pas un entier.");
        } catch(RuntimeException e){
            // Erreur BLL ou DAL
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

}
