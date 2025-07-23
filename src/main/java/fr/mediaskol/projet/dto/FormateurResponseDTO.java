package fr.mediaskol.projet.dto;

import fr.mediaskol.projet.bo.formateur.Formateur;
import fr.mediaskol.projet.bo.formation.Formation;
import fr.mediaskol.projet.bo.formation.TypeFormation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FormateurResponseDTO {

    /**
     * Déclaration des attributs
     */
    private Long idFormateur;
    private String nom;
    private String prenom;
    private String email;
    private String numPortable;
    private String statutFormateur;
    private String zoneIntervention;
    private String commentaireFormateur;
   // private Long adresseId;
    private AdresseResponseDTO adresse;
    private List<String> formationsIds;
    private Set<String> typeFormationIds;


    /**
     * Constructeur
     *
     * @param formateur
     */
    public FormateurResponseDTO(Formateur formateur) {
        this.idFormateur = formateur.getIdPersonne();
        this.nom = formateur.getNom();
        this.prenom = formateur.getPrenom();
        this.email = formateur.getEmail();
        this.numPortable = formateur.getNumPortable();
        this.statutFormateur = formateur.getStatutFormateur();
        this.zoneIntervention = formateur.getZoneIntervention();
        this.commentaireFormateur = formateur.getCommentaireFormateur();
        if(formateur.getAdresse() !=null){
            this.adresse = new AdresseResponseDTO(formateur.getAdresse());
        } else {
            this.adresse = null;
        }
        // Transformation explicite des collections entités en collections d'IDs
        if (formateur.getTypesFormationDispensees() != null) {
            this.typeFormationIds = formateur.getTypesFormationDispensees().stream()
                    .map(TypeFormation::getLibelleTypeFormation)// ou ton getter d'ID
                    .collect(Collectors.toSet());
        }


        if (formateur.getFormationsDispensees() != null) {
            this.formationsIds = formateur.getFormationsDispensees().stream()
                    .map(Formation::getLibelleFormation) // ou ton getter d'ID
                    .collect(Collectors.toList());
        }
    }
}
