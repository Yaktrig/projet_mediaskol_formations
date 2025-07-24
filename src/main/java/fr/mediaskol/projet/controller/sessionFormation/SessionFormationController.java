package fr.mediaskol.projet.controller.sessionFormation;

import fr.mediaskol.projet.bll.sessionFormation.SessionFormationService;
import fr.mediaskol.projet.bo.sessionFormation.SessionFormation;
import fr.mediaskol.projet.dto.sessionFormation.SessionFormationRespDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur qui permet :
 * d'afficher les sessions de formation
 * d'ajouter une session de formation
 * de rechercher une session de formation par son id
 * de rechercher une ou plusieurs sessions de formation
 * de supprimer une session de formation
 * Utilisation de lombok pour injecter le service à la place d'un constructeur
 */

@AllArgsConstructor
@RestController
@RequestMapping("/mediaskolFormation/sessionsFormations")
public class SessionFormationController {

    /**
     * Injection de dépendances pour aller chercher le service qui correspond aux sessions de formation
     */
    private SessionFormationService sessionFormationService;




    /**
     * Retourne la liste des sessions de formation en Json dans l'url "mediaskolFormation/sessionsFormations en méthode GET
     */
    @GetMapping
    public ResponseEntity<?> afficherTousLesSessionsFormations() {

        final List<SessionFormation> sessionsFormations = sessionFormationService.chargerToutesSessionsFormations();

        if (sessionsFormations == null || sessionsFormations.isEmpty()) {
            // Statut 204 : No content - Pas de body car rien à afficher
            return ResponseEntity.noContent().build();
        }

        // Sinon, on retourne Statut 200 : Ok + dans le body les sessionFormation
        // Conversion liste SessionFormation -> liste SessionFormationDTO
        List<SessionFormationRespDTO> sessionFormationRespDTOS = sessionsFormations.stream()
                .map(SessionFormationRespDTO::new)
                .toList();

        return ResponseEntity.ok(sessionFormationRespDTOS);
    }

    /**
     * Retourne une session de formation par rapport à son identifiant
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> rechercherSessionFormationParId(@PathVariable("id") String idInPath) {

        try {
            long id = Long.parseLong(idInPath);
            final SessionFormation sessionFormation = sessionFormationService.chargerSessionFormationParId(id);

            SessionFormationRespDTO sessionFormationDto = new SessionFormationRespDTO(sessionFormation);
            return ResponseEntity.ok(sessionFormationDto);
        } catch (NumberFormatException e) {
            // Statut 406 : No Acceptable
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Votre identifiant n'est pas valide");
        } catch (RuntimeException e) {
            // Statut 404 : Not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

    /**
     * Retourne une ou des sessions de formations en Json dans l'url "mediaskolFormation/sessionsFormations/recherche
     * selon le critère saisi dans le champ
     */
//    @GetMapping("/recherche")
//    public ResponseEntity<List<SessionFormation>> rechercheSessionFormations(@RequestParam String termeRecherche) {
//
//        final List<SessionFormation> sessionFormationsRecherches = sessionFormationService.rechercheSessionFormations(termeRecherche);
//
//        if (sessionFormationsRecherches == null || sessionFormationsRecherches.isEmpty()) {
//            // Statut 204 : No content - Pas de body car rien à afficher
//            return ResponseEntity.noContent().build();
//        }
//        // Sinon, on retourne Statut 200 : Ok + dans le body les sessionFormations
//
//        return ResponseEntity.ok(sessionFormationsRecherches);
//
//    }

    /**
     * Méthode qui permet de créer une nouvelle session de formation dans la base de données
     */
    @PostMapping
    @Valid
    public ResponseEntity<?> ajouterSessionFormation(@Valid @RequestBody SessionFormation sessionFormation) {

        // La session de formation ne doit pas être nulle
        if (sessionFormation == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("La session de formation à ajouter " +
                    "est obligatoire");
        }

        // La session de formation ne doit pas avoir d'identifiant de saisi
        if (sessionFormation.getIdSessionFormation() != null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Impossible de sauvegarder une session " +
                    "de formation");
        }

        try {
            sessionFormationService.ajouterSessionFormation(sessionFormation);
            return ResponseEntity.ok(sessionFormation);
        } catch (RuntimeException e) {
            // Erreur BLL ou DAL
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

    /**
     * Modifier une session de formation
     */
    @PutMapping
    @Valid
    public ResponseEntity<?> modifierSessionFormation(@Valid @RequestBody SessionFormation sessionFormation) {

        // La session de formation ne doit pas être nulle - l'identifiant ne pas être nul ou inférieur ou égal à 0
        if (sessionFormation == null || sessionFormation.getIdSessionFormation() == null ||
                sessionFormation.getIdSessionFormation() <= 0) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("L'identifiant de la sessionFormation et la " +
                    "sessionFormation sont obligatoires.");
        }

        try {
            sessionFormationService.ajouterSessionFormation(sessionFormation);
            return ResponseEntity.ok(sessionFormation);
        } catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

    /**
     * Supprimer une session de formation
     */
    @DeleteMapping("/{id}")
    @Valid
    public ResponseEntity<?> supprimerSessionFormation(@PathVariable("id") String idInPath) {


        try {
            final int idSessionFormation = Integer.parseInt(idInPath);
            sessionFormationService.supprimerSessionFormation(idSessionFormation);
            return ResponseEntity.ok("La session de formation " + idSessionFormation + " est supprimée de la base de données.");
        } catch(NumberFormatException e){
            // Statut 406 : No acceptable
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Votre identifiant n'est pas un entier.");
        } catch(RuntimeException e){
            // Erreur BLL ou DAL
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

}
