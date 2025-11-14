package fr.mediaskol.projet.controller.sessionFormationDistanciel;

import fr.mediaskol.projet.bll.sessionFormation.SessionFOADService;
import fr.mediaskol.projet.bo.sessionFormationDistanciel.SessionFormationDistanciel;
import fr.mediaskol.projet.dto.sessionFormation.SessionFOADResponseDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur qui permet :
 * d'afficher les sessions de formation à distance
 * d'ajouter une session de formation à distance
 * de rechercher une session de formation par son id à distance
 * de rechercher une ou plusieurs sessions de formation à distance
 * de supprimer une session de formation à distance
 * Utilisation de lombok pour injecter le service à la place d'un constructeur
 */

@AllArgsConstructor
@RestController
@RequestMapping("/mediaskolFormation/sessionsFormationsDistanciels")
public class SessionFormationDistancielController {

    /**
     * Injection de dépendances pour aller chercher le service qui correspond aux sessions de formation à distance
     */
    private SessionFOADService sessionFoadService;




    /**
     * Retourne la liste des sessions de formation à distance en Json dans l'url "mediaskolFormation/sessionsFormationsDistanciels en méthode GET
     */
    @GetMapping
    public ResponseEntity<?> afficherTousLesSessionsFoads() {

        final List<SessionFormationDistanciel> sessionsFoads = sessionFoadService.chargerToutesSessionsFoads();

        if (sessionsFoads == null || sessionsFoads.isEmpty()) {
            // Statut 204 : No content - Pas de body car rien à afficher
            return ResponseEntity.noContent().build();
        }

        // Sinon, on retourne Statut 200 : Ok + dans le body les sessionFormationDistanciel
        // Conversion liste SessionFormationDistanciel -> liste SessionFOADDTO
        List<SessionFOADResponseDTO> sessionFoadRespDTOS = sessionsFoads.stream()
                .map(SessionFOADResponseDTO::new)
                .toList();

        return ResponseEntity.ok(sessionFoadRespDTOS);
    }

    /**
     * Retourne une session de formation à distance par rapport à son identifiant
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> rechercherSessionFoadParId(@PathVariable("id") String idInPath) {

        try {
            long id = Long.parseLong(idInPath);
            final SessionFormationDistanciel sessionFoad = sessionFoadService.chargerSessionFoadParId(id);

            SessionFOADResponseDTO sessionFoadDto = new SessionFOADResponseDTO(sessionFoad);
            return ResponseEntity.ok(sessionFoadDto);
        } catch (NumberFormatException e) {
            // Statut 406 : No Acceptable
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Votre identifiant n'est pas valide");
        } catch (RuntimeException e) {
            // Statut 404 : Not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

    /**
     * Retourne une ou des sessions de formations à distance en Json dans l'url
     * "mediaskolFormation/sessionsFormationsDistanciels/recherche
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
     * Méthode qui permet de créer une nouvelle session de formation à distance dans la base de données
     */
    @PostMapping
    @Valid
    public ResponseEntity<?> ajouterSessionFormation(@Valid @RequestBody SessionFormationDistanciel sessionFoad) {

        // La session de formation ne doit pas être nulle
        if (sessionFoad == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("La session de formation à distance à ajouter " +
                    "est obligatoire");
        }

        // La session de formation ne doit pas avoir d'identifiant de saisi
        if (sessionFoad.getIdSessionFormationDistanciel() != null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Impossible de sauvegarder une session " +
                    "de formation à distance");
        }
        try {
            sessionFoadService.ajouterSessionFoad(sessionFoad);
            return ResponseEntity.ok(sessionFoad);
        } catch (RuntimeException e) {
            // Erreur BLL ou DAL
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

    /**
     * Modifier une session de formation à distance
     */
    @PutMapping
    @Valid
    public ResponseEntity<?> modifierSessionFoad(@Valid @RequestBody SessionFormationDistanciel sessionFoad) {

        // La session de formation à distance ne doit pas être nulle - l'identifiant ne pas être nul ou inférieur ou égal à 0
        if (sessionFoad == null || sessionFoad.getIdSessionFormationDistanciel() == null ||
                sessionFoad.getIdSessionFormationDistanciel() <= 0) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("L'identifiant de la session de formation" +
                    " à distance et la session de formation à distance sont obligatoires.");
        }

        try {
            sessionFoadService.ajouterSessionFoad(sessionFoad);
            return ResponseEntity.ok(sessionFoad);
        } catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

    /**
     * Supprimer une session de formation à distance
     */
    @DeleteMapping("/{id}")
    @Valid
    public ResponseEntity<?> supprimerSessionFoad(@PathVariable("id") String idInPath) {


        try {
            final int idSessionFoad = Integer.parseInt(idInPath);
            sessionFoadService.supprimerSessionFoad(idSessionFoad);
            return ResponseEntity.ok("La session formation à distance " + idSessionFoad + " est supprimé " +
                    "de la base de données.");
        } catch(NumberFormatException e){
            // Statut 406 : No acceptable
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Votre identifiant n'est pas un entier.");
        } catch(RuntimeException e){
            // Erreur BLL ou DAL
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

}
