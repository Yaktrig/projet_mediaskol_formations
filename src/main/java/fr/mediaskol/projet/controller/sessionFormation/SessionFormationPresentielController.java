package fr.mediaskol.projet.controller.sessionFormation;

import fr.mediaskol.projet.bll.sessionFormation.SessionFOPService;
import fr.mediaskol.projet.bo.ApiResponse;
import fr.mediaskol.projet.bo.sessionFormation.SessionFormation;
import fr.mediaskol.projet.bo.sessionFormation.SessionFormationPresentiel;
import fr.mediaskol.projet.dto.salle.SessionSalleRespDTO;
import fr.mediaskol.projet.dto.sessionFormation.SessionFOPResponseDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur qui permet :
 * d'afficher les sessions de formation en présentiel
 * d'ajouter une session de formation en présentiel
 * de rechercher une session de formation en présentiel par son id
 * de rechercher une ou plusieurs sessions de formation en présentiel
 * de supprimer une session de formation en présentiel
 * Utilisation de lombok pour injecter le service à la place d'un constructeur
 */

@AllArgsConstructor
@RestController
@RequestMapping("/mediaskolFormation/sessionsFormationsPresentiels")
public class SessionFormationPresentielController {

    /**
     * Injection de dépendances pour aller chercher le service qui correspond aux sessions de formation
     */
    private SessionFOPService sessionFOPService;

    /**
     * Retourne la liste des sessions de formation en Json dans l'url "mediaskolFormation/sessionsFormations en méthode GET
     */
    @GetMapping
    public ResponseEntity<?> afficherTousLesSessionsFormationsPresentiel() {

        final List<SessionFormationPresentiel> sessionsFop = sessionFOPService.chargerToutesSessionsFop();

        if (sessionsFop == null || sessionsFop.isEmpty()) {
            // Statut 204 : No content - Pas de body car rien à afficher
            return ResponseEntity.noContent().build();
        }

        // Sinon, on retourne Statut 200 : Ok + dans le body les sessionFormation
        // Conversion liste SessionFormation -> liste SessionFormationDTO
        List<SessionFOPResponseDTO> sessionFOPResponseDTOS = sessionsFop.stream()
                .map(session -> {
                    SessionFOPResponseDTO dto = new SessionFOPResponseDTO(session);
                    List<SessionSalleRespDTO> sallesDtos = sessionFOPService.getSessionsSalleBySessionId(session.getIdSessionFormation());
                    dto.setSessionsSalle(sallesDtos);
                    return dto;
                })
                .toList();

        return ResponseEntity.ok(sessionFOPResponseDTOS);
    }

    /**
     * Retourne une session de formation en présentiel par rapport à son identifiant
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> rechercherSessionFormationPresentielParId(@PathVariable("id") String idInPath) {

        try {
            long id = Long.parseLong(idInPath);
            final SessionFormationPresentiel sessionFop = sessionFOPService.chargerSessionFopParId(id);

            SessionFOPResponseDTO sessionFOPRespDTO = new SessionFOPResponseDTO(sessionFop);

            // Ajout : on va chercher les salles associées et on les met dans le DTO
            List<SessionSalleRespDTO> sallesDtos = sessionFOPService.getSessionsSalleBySessionId(id); // ou injecte le bon service
            sessionFOPRespDTO.setSessionsSalle(sallesDtos);

            return ResponseEntity.ok(sessionFOPRespDTO);

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
    @GetMapping("/recherche")
    public ResponseEntity<List<SessionFOPResponseDTO>> rechercheSessionFormationsPresentiel(@RequestParam String termeRecherche) {

        final List<SessionFormationPresentiel> sessionFopRecherches = sessionFOPService.rechercheSessionFop(termeRecherche);

        if (sessionFopRecherches == null || sessionFopRecherches.isEmpty()) {
            // Statut 204 : No content - Pas de body car rien à afficher
            return ResponseEntity.noContent().build();
        }

        List<SessionFOPResponseDTO> dtos = sessionFopRecherches.stream()
                .map(SessionFOPResponseDTO::new)
                .toList();

        // Sinon, on retourne Statut 200 : Ok + dans le body les sessionFormations
        return ResponseEntity.ok(dtos);
    }

    /**
     * Méthode qui permet de créer une nouvelle session de formation dans la base de données
     */
    @PostMapping
    @Valid
    public ResponseEntity<?> ajouterSessionFormationPresentiel(@Valid @RequestBody SessionFormationPresentiel sessionFop) {

        // La session de formation ne doit pas être nulle
        if (sessionFop == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("La session de formation à ajouter " +
                    "est obligatoire");
        }

        // La session de formation ne doit pas avoir d'identifiant de saisi
        if (sessionFop.getIdSessionFormation() != null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Impossible de sauvegarder une session " +
                    "de formation");
        }
        try {
            sessionFOPService.ajouterSessionFop(sessionFop);
            return ResponseEntity.ok(new ApiResponse(true, "Session ajoutée avec succès."));
        } catch (RuntimeException e) {
            // Erreur BLL ou DAL
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ApiResponse(false, "Erreur lors de la création de la session."));
        }
    }

    /**
     * Modifier une session de formation en présentiel
     */
    @PutMapping
    @Valid
    public ResponseEntity<?> modifierSessionFormationPresentiel(@Valid @RequestBody SessionFormationPresentiel sessionFop) {

        // La session de formation ne doit pas être nulle - l'identifiant ne pas être nul ou inférieur ou égal à 0
        if (sessionFop == null || sessionFop.getIdSessionFormation() == null ||
                sessionFop.getIdSessionFormation() <= 0) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("L'identifiant de la sessionFormation et la " +
                    "sessionFormation sont obligatoires.");
        }

        try {
            sessionFOPService.ajouterSessionFop(sessionFop);
            return ResponseEntity.ok(sessionFop);
        } catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

    /**
     * Supprimer une session de formation en présentiel
     */
    @DeleteMapping("/{id}")
    @Valid
    public ResponseEntity<?> supprimerSessionFormationPresentiel(@PathVariable("id") String idInPath) {


        try {
            final int idSessionFormation = Integer.parseInt(idInPath);
            sessionFOPService.supprimerSessionFop(idSessionFormation);
            return ResponseEntity.ok("La session formation " + idSessionFormation + " est supprimé de la base de données.");
        } catch(NumberFormatException e){
            // Statut 406 : No acceptable
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Votre identifiant n'est pas un entier.");
        } catch(RuntimeException e){
            // Erreur BLL ou DAL
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

}
