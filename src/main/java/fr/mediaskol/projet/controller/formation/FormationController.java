package fr.mediaskol.projet.controller.formation;

import fr.mediaskol.projet.bll.formation.FormationService;
import fr.mediaskol.projet.bo.formation.Formation;
import fr.mediaskol.projet.bo.formation.TypeFormation;
import fr.mediaskol.projet.dal.formation.TypeFormationRepository;
import fr.mediaskol.projet.dto.formation.FormationInputDTO;
import fr.mediaskol.projet.dto.formation.FormationResponseDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur qui permet :
 * d'afficher les formations
 * d'ajouter une formation
 * de rechercher une formation ?
 * de supprimer une formation
 * Utilisation de lombok pour injecter le service à la place d'un constructeur
 */

@AllArgsConstructor
@RestController
@RequestMapping("/mediaskolFormation/formations")
public class FormationController {

    /**
     * Injection de dépendances pour aller chercher le service qui correspond aux formations
     */
    private FormationService formationService;
    private TypeFormationRepository typeFormationRepository;

    /**
     * Retourne la liste des formations en Json dans l'url "mediaskolFormation/formations en méthode GET
     */
    @GetMapping
    public ResponseEntity<?> afficherToutesLesFormations() {

        final List<Formation> formations = formationService.chargerToutesFormations();

        if (formations == null || formations.isEmpty()) {
            // Statut 204 : No content - Pas de body car rien à afficher
            return ResponseEntity.noContent().build();
        }

        // Sinon, on retourne Statut 200 : Ok + dans le body les formations
        List<FormationResponseDTO> formationResponseDTOS = formations.stream()
                .map(FormationResponseDTO::new)
                .toList();

        return ResponseEntity.ok(formationResponseDTOS);
    }


    /**
     * Retourne un ou des formations en Json dans l'url "mediaskolFormation/formations/recherche
     * selon le critère saisi dans le champ
     * Todo nécessaire ?
     */
//    @GetMapping("/recherche")
//    public ResponseEntity<List<Formation>> rechercheFormations(@RequestParam String formationRecherchee) {
//
//        final List<Formation> formationsRecherchees = formationService.rechercheFormations(formationRecherchee);
//
//        if (formationsRecherchees == null || formationsRecherchees.isEmpty()) {
//            // Statut 204 : No content - Pas de body car rien à afficher
//            return ResponseEntity.noContent().build();
//        }
//        // Sinon, on retourne Statut 200 : Ok + dans le body les formations
//
//        return ResponseEntity.ok(formationsRecherchees);
//
//    }

    /**
     * Méthode qui permet de créer une nouvelle formation dans la base de données
     */
    @PostMapping
    @Valid
    public ResponseEntity<?> ajouterFormation(@Valid @RequestBody FormationInputDTO formationInputDTO) {

        // La formation ne doit pas être nulle
        if (formationInputDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("La formation à ajouter est obligatoire");
        }

        try {
            // Recherche du type de formation en base à partir de l'id fourni dans le DTO
            TypeFormation typeFormation = typeFormationRepository.findById(formationInputDTO.getTypeFormationId())
                    .orElseThrow(() -> new RuntimeException(
                            "Type de formation inexistant pour l'id : " + formationInputDTO.getTypeFormationId()));

            // Construction de l'entité Formation à partir du DTO et de l'entité associée
            Formation formation = Formation.builder()
                    .themeFormation(formationInputDTO.getThemeFormation())
                    .libelleFormation(formationInputDTO.getLibelleFormation())
                    .typeFormation(typeFormation)
                    .build();

            // Persist en base via le service
            Formation nouvelleFormation = formationService.ajouterFormation(formation);

            // Prépare la réponse au client
            FormationResponseDTO responseDTO = new FormationResponseDTO(nouvelleFormation);

            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        } catch (RuntimeException e) {
            // Erreur BLL ou DAL
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

    /**
     * Modifier une formation
     */
    @PutMapping
    @Valid
    public ResponseEntity<?> modifierFormation(@Valid @RequestBody FormationInputDTO formationInputDTO) {

        // La formation ne doit pas être nulle - l'identifiant ne pas être nul ou inférieur ou égal à 0.
        if (formationInputDTO == null ||formationInputDTO.getIdFormation() <= 0) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("L'identifiant de la formation et la formation sont obligatoires.");
        }

        try {
          Formation formationModifiee = formationService.modifierFormation(formationInputDTO);
          return ResponseEntity.ok(new FormationResponseDTO(formationModifiee));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

    /**
     * Supprimer une formation
     */
    @DeleteMapping("/{id}")
    @Valid
    public ResponseEntity<?> supprimerFormation(@PathVariable("id") String idInPath) {

        try {
            final int idFormation = Integer.parseInt(idInPath);
            formationService.supprimerFormation(idFormation);
            return ResponseEntity.ok("La formation " + idFormation + " est supprimée de la base de données.");
        } catch(NumberFormatException e){
            // Statut 406 : No acceptable
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Votre identifiant n'est pas un entier.");
        } catch(RuntimeException e){
            // Erreur BLL ou DAL
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

}
