package fr.mediaskol.projet.controller.salle;

import fr.mediaskol.projet.bll.salle.SessionSalleService;
import fr.mediaskol.projet.bo.salle.SessionSalle;
import fr.mediaskol.projet.dto.salle.SessionSalleRespDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur qui permet :
 * d'afficher les sessions salles d'une salle
 * d'afficher une session salle à partir de son id
 * d'ajouter une session salle
 * de rechercher une session salle ?
 * de supprimer une session salle
 * Utilisation de lombok pour injecter le service à la place d'un constructeur
 */

@AllArgsConstructor
@RestController
@RequestMapping("/mediaskolFormation/sessionsSalles")
public class SessionSalleController {

    /**
     * Injection de dépendances pour aller chercher le service qui correspond aux sessions d'une salle
     */
    private SessionSalleService sessionSalleService;

    /**
     * Retourne la liste des sessions salle en Json dans l'url "mediaskolFormation/sessionsSalles en méthode GET
     */
    @GetMapping
    public ResponseEntity<?> afficherToutesLesSessionsSalles() {

        final List<SessionSalle> sessionSalles = sessionSalleService.chargerToutesSessionsSalle();

        if (sessionSalles == null || sessionSalles.isEmpty()) {
            // Statut 204 : No content - Pas de body car rien à afficher
            return ResponseEntity.noContent().build();
        }

        // Sinon, on retourne Statut 200 : Ok + dans le body les sessionSalles
        List<SessionSalleRespDTO> sessionSalleRespDTOS = sessionSalles.stream()
                .map(SessionSalleRespDTO::new)
                .toList();

        return ResponseEntity.ok(sessionSalleRespDTOS);
    }

    /**
     * Retourne une session d'une salle par rapport à son identifiant
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> rechercherSessionSalleParId(@PathVariable("id") String idInPath) {

        try {
            long id = Long.parseLong(idInPath);
            final SessionSalle sessionSalle = sessionSalleService.chargerSessionSalleParId(id);

            SessionSalleRespDTO sessionSalleDto = new SessionSalleRespDTO(sessionSalle);
            return ResponseEntity.ok(sessionSalleDto);
        } catch (NumberFormatException e) {
            // Statut 406 : No Acceptable
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Votre identifiant n'est pas valide");
        } catch (RuntimeException e) {
            // Statut 404 : Not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Méthode qui permet de créer une nouvelle session salle dans la base de données
     */
    @PostMapping
    @Valid
    public ResponseEntity<?> ajouterSalle(@Valid @RequestBody SessionSalle sessionSalle) {

        // La session de la salle ne doit pas être nulle
        if (sessionSalle == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("La session de la salle à ajouter " +
                    "est obligatoire");
        }

        // La session de la salle ne doit pas avoir d'identifiant de saisi
        if (sessionSalle.getIdSessionSalle() != null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Impossible de sauvegarder une session " +
                    "de la salle.");
        }

        try {
            sessionSalleService.ajouterSessionSalle(sessionSalle);
            return ResponseEntity.ok(sessionSalle);
        } catch (RuntimeException e) {
            // Erreur BLL ou DAL
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

    /**
     * Modifier une sessionSalle
     */
    @PutMapping
    @Valid
    public ResponseEntity<?> modifierSessionSalle(@Valid @RequestBody SessionSalle sessionSalle) {

        // La session de la salle ne doit pas être nulle - l'identifiant ne pas être nul ou inférieur ou égal à 0
        if (sessionSalle == null || sessionSalle.getIdSessionSalle() == null ||
                sessionSalle.getIdSessionSalle() <= 0) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("L'identifiant de la sessionSalle et la " +
                    "sessionSalle sont obligatoires.");
        }

        try {
            sessionSalleService.ajouterSessionSalle(sessionSalle);
            return ResponseEntity.ok(sessionSalle);
        } catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

    /**
     * Supprimer une sessionSalle
     */
    @DeleteMapping("/{id}")
    @Valid
    public ResponseEntity<?> supprimerSessionSalle(@PathVariable("id") String idInPath) {

        try {
            final int idSessionSalle = Integer.parseInt(idInPath);
            sessionSalleService.supprimerSessionSalle(idSessionSalle);
            return ResponseEntity.ok("La session de la salle " + idSessionSalle + " est supprimée de la base de données.");
        } catch(NumberFormatException e){
            // Statut 406 : No acceptable
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Votre identifiant n'est pas un entier.");
        } catch(RuntimeException e){
            // Erreur BLL ou DAL
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }
}
