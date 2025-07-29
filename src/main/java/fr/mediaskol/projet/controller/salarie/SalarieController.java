package fr.mediaskol.projet.controller.salarie;

import fr.mediaskol.projet.bll.salarie.SalarieService;
import fr.mediaskol.projet.bo.salarie.Salarie;
import fr.mediaskol.projet.dto.salarie.SalarieResponseDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur qui permet :
 * d'afficher les salariés
 * d'ajouter un salarié
 * de rechercher un salarié par son id
 * de supprimer un salarié
 * Utilisation de lombok pour injecter le service à la place d'un constructeur
 */
@AllArgsConstructor
@RestController
@RequestMapping("/mediaskolFormation/salaries")
public class SalarieController {

    /**
     * Injection de dépendances pour aller chercher le service
     */
    private SalarieService salarieService;

    /**
     * Retourne un salarié par rapport à son identifiant
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> rechercherSalarieParId(@PathVariable("id") String idInPath) {

        try {
            long id = Long.parseLong(idInPath);
            final Salarie salarie = salarieService.chargerUnSalarieParId(id);
            return ResponseEntity.ok(salarie);
        } catch (NumberFormatException e) {
            // Statut 406 : No Acceptable
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Votre identifiant n'est pas valide");
        } catch (RuntimeException e) {
            // Statut 404 : Not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Retourne la liste des salariés en Json dans l'url "mediaskolFormation/salaries en méthode GET
     */
    @GetMapping
    public ResponseEntity<?> afficherTousLesSalaries() {

        final List<Salarie> salarie = salarieService.chargerTousSalaries();

        if (salarie == null || salarie.isEmpty()) {
            // Statut 204 : No content - Pas de body car rien à afficher
            return ResponseEntity.noContent().build();
        }

        // Sinon, on retourne Statut 200 : Ok + dans le body les salarie
        //return ResponseEntity.ok(salarie);
        // Conversion liste Salarié -> liste ApprenantDTO
        List<SalarieResponseDTO> salarieResponseDTOS = salarie.stream()
                .map(SalarieResponseDTO::new)
                .toList();

        return ResponseEntity.ok(salarieResponseDTOS);
    }


    /**
     * Retourne un ou des salariés en Json dans l'url "mediaskolFormation/salarie/recherche
     * selon le critère saisi dans le champ
     */
//    @GetMapping("/recherche")
//    public ResponseEntity<List<Salarie>> rechercheSalaries(@RequestParam String termeRecherche) {
//
//        final List<Salarie> salariesRecherches = salarieService.rechercheSalarie(termeRecherche);
//
//        if (salariesRecherches == null || salariesRecherches.isEmpty()) {
//            // Statut 204 : No content - Pas de body car rien à afficher
//            return ResponseEntity.noContent().build();
//        }
//        // Sinon, on retourne Statut 200 : Ok + dans le body les salaries
//
//        return ResponseEntity.ok(salariesRecherches);
//    }

    /**
     * Méthode qui permet de créer un nouvel salarie dans la base de données
     */
    @PostMapping
    @Valid
    public ResponseEntity<?> ajouterSalarie(@Valid @RequestBody Salarie salarie) {

        // Le salarié ne doit pas être nul
        if (salarie == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Le salarié à ajouter est obligatoire");
        }

        // Le salarié ne doit pas avoir d'identifiant de saisi
        if (salarie.getIdPersonne() != null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Impossible de sauvegarder un salarié");
        }
        try {
            salarieService.ajouterSalarie(salarie);
            return ResponseEntity.ok(salarie);
        } catch (RuntimeException e) {
            // Erreur BLL ou DAL
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

    /**
     * Modifier un salarié
     */
    @PutMapping
    @Valid
    public ResponseEntity<?> modifierSalarie(@Valid @RequestBody Salarie salarie) {

        // Le salarie ne doit pas être nul - l'identifiant ne pas être nul ou inférieur ou égal à 0
        if (salarie == null || salarie.getIdPersonne() == null || salarie.getIdPersonne() <= 0) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("L'identifiant du salarie et le salarie sont obligatoires.");
        }

        try {
            salarieService.ajouterSalarie(salarie);
            return ResponseEntity.ok(salarie);
        } catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

    /**
     * Supprimer un salarié
     */
    @DeleteMapping("/{id}")
    @Valid
    public ResponseEntity<?> supprimerSalarie(@PathVariable("id") String idInPath) {


        try {
            final int idSalarie = Integer.parseInt(idInPath);
            salarieService.supprimerSalarie(idSalarie);
            return ResponseEntity.ok("Le salarié " + idSalarie + " est supprimé de la base de données.");
        } catch(NumberFormatException e){
            // Statut 406 : No acceptable
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Votre identifiant n'est pas un entier.");
        } catch(RuntimeException e){
            // Erreur BLL ou DAL
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

}
