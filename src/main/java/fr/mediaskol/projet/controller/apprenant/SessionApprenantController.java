package fr.mediaskol.projet.controller.apprenant;

import fr.mediaskol.projet.bll.apprenant.SessionApprenantService;
import fr.mediaskol.projet.bo.apprenant.SessionApprenant;
import fr.mediaskol.projet.dto.apprenant.SessionApprenantRespDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur qui permet :
 * d'afficher les sessions apprenants d'un apprenant
 * d'afficher une session apprenant à partir de son id
 * d'ajouter une session apprenant
 * de rechercher une session apprenant ?
 * de supprimer une session apprenant
 * Utilisation de lombok pour injecter le service à la place d'un constructeur
 */

@AllArgsConstructor
@RestController
@RequestMapping("/mediaskolFormation/sessionsApprenants")
public class SessionApprenantController {

    /**
     * Injection de dépendances pour aller chercher le service qui correspond aux sessions d'apprenant
     */
    private SessionApprenantService sessionApprenantService;


    /**
     * Retourne la liste des sessions apprenant en Json dans l'url "mediaskolFormation/sessionsApprenants en méthode GET
     */
    @GetMapping
    public ResponseEntity<?> afficherToutesLesSessionsApprenants() {

        final List<SessionApprenant> sessionApprenants = sessionApprenantService.chargerToutesSessionsApprenant();

        if (sessionApprenants == null || sessionApprenants.isEmpty()) {
            // Statut 204 : No content - Pas de body car rien à afficher
            return ResponseEntity.noContent().build();
        }

        // Sinon, on retourne Statut 200 : Ok + dans le body les sessionApprenants
        List<SessionApprenantRespDTO> sessionApprenantRespDTOS = sessionApprenants.stream()
                .map(SessionApprenantRespDTO::new)
                .toList();

        return ResponseEntity.ok(sessionApprenantRespDTOS);
    }

    /**
     * Retourne une session d'apprenant par rapport à son identifiant
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> rechercherSessionApprenantParId(@PathVariable("id") String idInPath) {

        try {
            long id = Long.parseLong(idInPath);
            final SessionApprenant sessionApprenant = sessionApprenantService.chargerSessionApprenantParId(id);

            SessionApprenantRespDTO sessionApprenantDto = new SessionApprenantRespDTO(sessionApprenant);
            return ResponseEntity.ok(sessionApprenantDto);
        } catch (NumberFormatException e) {
            // Statut 406 : No Acceptable
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Votre identifiant n'est pas valide");
        } catch (RuntimeException e) {
            // Statut 404 : Not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    /**
     * Méthode qui permet de créer une nouvelle session apprenant dans la base de données
     */
    @PostMapping
    @Valid
    public ResponseEntity<?> ajouterApprenant(@Valid @RequestBody SessionApprenant sessionApprenant) {

        // La session de l'apprenant ne doit pas être nulle
        if (sessionApprenant == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("La session de l'apprenant à ajouter " +
                    "est obligatoire");
        }

        // La session de l'apprenant ne doit pas avoir d'identifiant de saisi
        if (sessionApprenant.getIdSessionApprenant() != null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Impossible de sauvegarder une session " +
                    "d'apprenant.");
        }

        try {
            sessionApprenantService.ajouterSessionApprenant(sessionApprenant);
            return ResponseEntity.ok(sessionApprenant);
        } catch (RuntimeException e) {
            // Erreur BLL ou DAL
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

    /**
     * Modifier une sessionApprenant
     */
    @PutMapping
    @Valid
    public ResponseEntity<?> modifierSessionApprenant(@Valid @RequestBody SessionApprenant sessionApprenant) {

        // La session de l'apprenant ne doit pas être nulle - l'identifiant ne pas être nul ou inférieur ou égal à 0
        if (sessionApprenant == null || sessionApprenant.getIdSessionApprenant() == null ||
                sessionApprenant.getIdSessionApprenant() <= 0) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("L'identifiant de la sessionApprenant et la " +
                    "sessionApprenant sont obligatoires.");
        }

        try {
            sessionApprenantService.ajouterSessionApprenant(sessionApprenant);
            return ResponseEntity.ok(sessionApprenant);
        } catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

    /**
     * Supprimer une sessionApprenant
     */
    @DeleteMapping("/{id}")
    @Valid
    public ResponseEntity<?> supprimerSessionApprenant(@PathVariable("id") String idInPath) {

        try {
            final int idSessionApprenant = Integer.parseInt(idInPath);
            sessionApprenantService.supprimerSessionApprenant(idSessionApprenant);
            return ResponseEntity.ok("La session de l'apprenant " + idSessionApprenant + " est supprimée de la base de données.");
        } catch(NumberFormatException e){
            // Statut 406 : No acceptable
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Votre identifiant n'est pas un entier.");
        } catch(RuntimeException e){
            // Erreur BLL ou DAL
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }
}
