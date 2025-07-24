package fr.mediaskol.projet.controller.sessionLieuDate;

import fr.mediaskol.projet.bll.sessionLieuDate.SessionLieuDateService;
import fr.mediaskol.projet.bo.sessionLieuDate.SessionLieuDate;
import fr.mediaskol.projet.dto.sessionLieuDate.SessionLieuDateRespDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur qui permet :
 * d'afficher les lieux et dates des sessions de formation
 * d'ajouter un lieu et une date d'une session de formation
 * de rechercher un lieu et une date d'une session de formation par son id
 * de rechercher un ou plusieurs lieux et dates des sessions de formation
 * de supprimer un lieu et une date d'une session de formation
 * Utilisation de lombok pour injecter le service à la place d'un constructeur
 */

@AllArgsConstructor
@RestController
@RequestMapping("/mediaskolFormation/sessionsLieuDates")
public class SessionLieuDateController {

    /**
     * Injection de dépendances pour aller chercher le service qui correspond aux lieux et dates des sessions de formation
     */
    private SessionLieuDateService sessionLieuDateService;


    /**
     * Retourne la liste des sessionLieuDate des sessions de formation en Json dans l'url "mediaskolFormation/sessionsLieuDates en méthode GET
     */
    @GetMapping
    public ResponseEntity<?> afficherTousLesSessionsLieuDates() {

        final List<SessionLieuDate> sessionsLieuDates = sessionLieuDateService.chargerToutesSessionsLieuDate();

        if (sessionsLieuDates == null || sessionsLieuDates.isEmpty()) {
            // Statut 204 : No content - Pas de body car rien à afficher
            return ResponseEntity.noContent().build();
        }

        // Sinon, on retourne Statut 200 : Ok + dans le body les sessionLieuDate
        // Conversion liste SessionLieuDate -> liste SessionLieuDateDTO
        List<SessionLieuDateRespDTO> sessionLieuDateRespDTOS = sessionsLieuDates.stream()
                .map(SessionLieuDateRespDTO::new)
                .toList();

        return ResponseEntity.ok(sessionLieuDateRespDTOS);
    }

    /**
     * Retourne une sessionLieuDate d'une session de formation par rapport à son identifiant
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> rechercherSessionLieuDateParId(@PathVariable("id") String idInPath) {

        try {
            long id = Long.parseLong(idInPath);
            final SessionLieuDate sessionLieuDate = sessionLieuDateService.chargerSessionLieuDateParId(id);

            SessionLieuDateRespDTO sessionLieuDateDto = new SessionLieuDateRespDTO(sessionLieuDate);
            return ResponseEntity.ok(sessionLieuDateDto);

        } catch (NumberFormatException e) {
            // Statut 406 : No Acceptable
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Votre identifiant n'est pas valide");
        } catch (RuntimeException e) {
            // Statut 404 : Not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

    /**
     * Retourne une ou des sessionLieuDate(s) des sessions de formation en Json dans l'url "mediaskolFormation/sessionsLieuDate/recherche
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
     * Méthode qui permet de créer une nouvelle sessionLieuDate pour une session de formation dans la base de données
     */
    @PostMapping
    @Valid
    public ResponseEntity<?> ajouterSessionLieuDate(@Valid @RequestBody SessionLieuDate sessionLieuDate) {

        // La sessionLieuDate ne doit pas être nulle.
        if (sessionLieuDate == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("La sessionLieuDate à ajouter " +
                    "est obligatoire");
        }

        // La ssessionLieuDate ne doit pas avoir d'identifiant de saisi
        if (sessionLieuDate.getIdSessionLieuDate() != null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Impossible de sauvegarder une sessionLieuDate " +
                    "pour la session de formation");
        }
        try {
            sessionLieuDateService.ajouterSessionLieuDate(sessionLieuDate);
            return ResponseEntity.ok(sessionLieuDate);
        } catch (RuntimeException e) {
            // Erreur BLL ou DAL
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

    /**
     * Modifier une date et/ou un lieu d'une session de formation
     */
    @PutMapping
    @Valid
    public ResponseEntity<?> modifierSessionLieuDate(@Valid @RequestBody SessionLieuDate sessionLieuDate) {

        // La sessionLieuDate d'une session de formation ne doit pas être nul - l'identifiant ne pas être nul ou inférieur ou égal à 0
        if (sessionLieuDate == null || sessionLieuDate.getIdSessionLieuDate() == null ||
                sessionLieuDate.getIdSessionLieuDate() <= 0) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("L'identifiant de la sessionLieuDate et la " +
                    "sessionLieuDate sont obligatoires.");
        }

        try {
            sessionLieuDateService.ajouterSessionLieuDate(sessionLieuDate);
            return ResponseEntity.ok(sessionLieuDate);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

    /**
     * Supprimer une date et/ou un lieu d'une session de formation
     */
    @DeleteMapping("/{id}")
    @Valid
    public ResponseEntity<?> supprimerSessionLieuDate(@PathVariable("id") String idInPath) {

        try {
            final int idSessionLieuDate = Integer.parseInt(idInPath);
            sessionLieuDateService.supprimerSessionLieuDate(idSessionLieuDate);
            return ResponseEntity.ok("La sessionLieuDate " + idSessionLieuDate + " est supprimée de la base de données.");
        } catch (NumberFormatException e) {
            // Statut 406 : No acceptable
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Votre identifiant n'est pas un entier.");
        } catch (RuntimeException e) {
            // Erreur BLL ou DAL
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

}
