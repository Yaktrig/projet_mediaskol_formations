package fr.mediaskol.projet.controller.formateur;


import fr.mediaskol.projet.bll.formateur.FormateurService;
import fr.mediaskol.projet.bo.formateur.Formateur;
import fr.mediaskol.projet.bo.salle.Salle;
import fr.mediaskol.projet.dto.formateur.FormateurResponseDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/mediaskolFormation/formateurs")
public class FormateurController {

    /**
     * Injection des dépendances pour aller chercher le service FormateurService qui correspond aux formateurs
     */
    private FormateurService formateurService;

    /**
     * Retourne la liste des formateurs en Json dans l'url "mediaskolFormation/formateurs en méthode GET
     */
    @GetMapping
    public ResponseEntity<?> afficherTousLesFormateurs() {

        final List<Formateur> formateurs = formateurService.chargerTousFormateurs();

        if (formateurs == null || formateurs.isEmpty()) {
            // Statut 204 : No content - Pas de body car rien à afficher
            return ResponseEntity.noContent().build();
        }


        // Conversion liste Formateur -> liste FormateurResponseDTO
        List<FormateurResponseDTO> formateurResponseDTOS = formateurs.stream()
                .map(FormateurResponseDTO::new)
                .toList();

        // Sinon, on retourne Statut 200 : Ok + dans le body les formateurs
        return ResponseEntity.ok(formateurResponseDTOS);
    }

    /**
     * Retourne un formateur par rapport à son identifiant
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> rechercherFormateurParId(@PathVariable("id") String idInPath) {

        try {
            long id = Long.parseLong(idInPath);
            final Formateur formateur = formateurService.chargerFormateurParId(id);

            FormateurResponseDTO dto = new FormateurResponseDTO(formateur);
            return ResponseEntity.ok(dto);

        } catch (NumberFormatException e) {
            // Statut 406 : No Acceptable
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Votre identifiant n'est pas valide");
        } catch (RuntimeException e) {
            // Statut 404 : Not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    /**
     * Retourne un ou des formateurs en Json dans l'url "mediaskolFormation/formateurs/recherche
     * selon le critère saisi dans le champ
     * @param termeRecherche
     */
    @GetMapping("/recherche")
    public ResponseEntity<List<Formateur>> rechercheFormateurs(@RequestParam String termeRecherche) {

        final List<Formateur> formateursRecherches = formateurService.rechercheFormateur(termeRecherche);

        if (formateursRecherches == null || formateursRecherches.isEmpty()) {
            // Statut 204 : No content - Pas de body car rien à afficher
            return ResponseEntity.noContent().build();
        }
        // Sinon, on retourne Statut 200 : Ok + dans le body les formateurs

        return ResponseEntity.ok(formateursRecherches);

    }



    /**
     * Méthode qui permet de créer un nouvel formateur dans la base de données
     * @param formateur
     */
    @PostMapping
    @Valid
    public ResponseEntity<?> ajouterFormateur(@Valid @RequestBody Formateur formateur) {

        // Le formateur ne doit pas être nul
        if (formateur == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Le formateur à ajouter est obligatoire");
        }

        // Le formateur ne doit pas avoir d'identifiant de saisi
        if (formateur.getIdPersonne() != null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Impossible de sauvegarder un formateur");
        }
        try {
            formateurService.ajouterFormateur(formateur, formateur.getAdresse(), formateur.getTypesFormationDispensees(), formateur.getFormationsDispensees());
            return ResponseEntity.ok(formateur);
        } catch (RuntimeException e) {
            // Erreur BLL ou DAL
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }


    /**
     * Modifier un formateur
     */
    @PutMapping
    @Valid
    public ResponseEntity<?> modifierFormateur(@Valid @RequestBody Formateur formateur) {

        // Le formateur ne doit pas être nul - l'identifiant ne pas être nul ou inférieur ou égal à 0
        if (formateur == null || formateur.getIdPersonne() == null || formateur.getIdPersonne() <= 0) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("L'identifiant du formateur et le formateur sont obligatoires.");
        }

        try {
            formateurService.ajouterFormateur(formateur, formateur.getAdresse(), formateur.getTypesFormationDispensees(), formateur.getFormationsDispensees());
            return ResponseEntity.ok(new FormateurResponseDTO(formateur));
        } catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

    /**
     * Supprimer un formateur.
     */
    @DeleteMapping("/{id}")
    @Valid
    public ResponseEntity<?> supprimerFormateur(@PathVariable("id") String idInPath) {

        try {
            final int idFormateur = Integer.parseInt(idInPath);
            formateurService.supprimerFormateur(idFormateur);
            return ResponseEntity.ok("Formateur " + idFormateur + " est supprimé de la base de données.");
        } catch(NumberFormatException e){
            // Statut 406 : No acceptable
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Votre identifiant n'est pas un entier.");
        } catch(RuntimeException e){
            // Erreur BLL ou DAL
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }


}
