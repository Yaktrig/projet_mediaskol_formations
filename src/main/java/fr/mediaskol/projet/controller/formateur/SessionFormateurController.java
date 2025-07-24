package fr.mediaskol.projet.controller.formateur;

import fr.mediaskol.projet.bll.formateur.SessionFormateurService;
import fr.mediaskol.projet.bo.formateur.SessionFormateur;
import fr.mediaskol.projet.dto.formateur.SessionFormateurRespDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur qui permet :
 * d'afficher les sessions formateurs d'un formateur
 * d'afficher une session formateur à partir de son id
 * d'ajouter une session formateur
 * de rechercher une session formateur ?
 * de supprimer une session formateur
 * Utilisation de lombok pour injecter le service à la place d'un constructeur
 */

@AllArgsConstructor
@RestController
@RequestMapping("/mediaskolFormation/sessionsFormateurs")
public class SessionFormateurController {

    /**
     * Injection de dépendances pour aller chercher le service qui correspond aux sessions d'un formateur
     */
    private SessionFormateurService sessionFormateurService;

    /**
     * Retourne la liste des sessions formateur en Json dans l'url "mediaskolFormation/sessionsFormateurs en méthode GET
     */
    @GetMapping
    public ResponseEntity<?> afficherToutesLesSessionsFormateurs() {

        final List<SessionFormateur> sessionFormateurs = sessionFormateurService.chargerToutesSessionsFormateur();

        if (sessionFormateurs == null || sessionFormateurs.isEmpty()) {
            // Statut 204 : No content - Pas de body car rien à afficher
            return ResponseEntity.noContent().build();
        }

        // Sinon, on retourne Statut 200 : Ok + dans le body les sessionFormateurs
        List<SessionFormateurRespDTO> sessionFormateurRespDTOS = sessionFormateurs.stream()
                .map(SessionFormateurRespDTO::new)
                .toList();

        return ResponseEntity.ok(sessionFormateurRespDTOS);
    }

    /**
     * Retourne une session d'un formateur par rapport à son identifiant
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> rechercherSessionFormateurParId(@PathVariable("id") String idInPath) {

        try {
            long id = Long.parseLong(idInPath);
            final SessionFormateur sessionFormateur = sessionFormateurService.chargerSessionFormateurParId(id);

            SessionFormateurRespDTO sessionFormateurDto = new SessionFormateurRespDTO(sessionFormateur);
            return ResponseEntity.ok(sessionFormateurDto);
        } catch (NumberFormatException e) {
            // Statut 406 : No Acceptable
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Votre identifiant n'est pas valide");
        } catch (RuntimeException e) {
            // Statut 404 : Not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Méthode qui permet de créer une nouvelle session formateur dans la base de données
     */
    @PostMapping
    @Valid
    public ResponseEntity<?> ajouterFormateur(@Valid @RequestBody SessionFormateur sessionFormateur) {

        // La session du formateur ne doit pas être nulle
        if (sessionFormateur == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("La session du formateur à ajouter " +
                    "est obligatoire");
        }

        // La session du formateur ne doit pas avoir d'identifiant de saisi
        if (sessionFormateur.getIdSessionFormateur() != null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Impossible de sauvegarder une session " +
                    "du formateur.");
        }

        try {
            sessionFormateurService.ajouterSessionFormateur(sessionFormateur);
            return ResponseEntity.ok(sessionFormateur);
        } catch (RuntimeException e) {
            // Erreur BLL ou DAL
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

    /**
     * Modifier une sessionFormateur
     */
    @PutMapping
    @Valid
    public ResponseEntity<?> modifierSessionFormateur(@Valid @RequestBody SessionFormateur sessionFormateur) {

        // La session du formateur ne doit pas être nulle - l'identifiant ne pas être nul ou inférieur ou égal à 0
        if (sessionFormateur == null || sessionFormateur.getIdSessionFormateur() == null ||
                sessionFormateur.getIdSessionFormateur() <= 0) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("L'identifiant de la sessionFormateur et la " +
                    "sessionFormateur sont obligatoires.");
        }

        try {
            sessionFormateurService.ajouterSessionFormateur(sessionFormateur);
            return ResponseEntity.ok(sessionFormateur);
        } catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

    /**
     * Supprimer une sessionFormateur
     */
    @DeleteMapping("/{id}")
    @Valid
    public ResponseEntity<?> supprimerSessionFormateur(@PathVariable("id") String idInPath) {

        try {
            final int idSessionFormateur = Integer.parseInt(idInPath);
            sessionFormateurService.supprimerSessionFormateur(idSessionFormateur);
            return ResponseEntity.ok("La session du formateur " + idSessionFormateur + " est supprimée de la base de données.");
        } catch(NumberFormatException e){
            // Statut 406 : No acceptable
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Votre identifiant n'est pas un entier.");
        } catch(RuntimeException e){
            // Erreur BLL ou DAL
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }
}
