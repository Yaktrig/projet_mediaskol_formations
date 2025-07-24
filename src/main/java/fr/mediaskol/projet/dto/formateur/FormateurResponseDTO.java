package fr.mediaskol.projet.dto.formateur;

import fr.mediaskol.projet.bo.formateur.Formateur;
import fr.mediaskol.projet.bo.formation.Formation;
import fr.mediaskol.projet.bo.formation.TypeFormation;
import fr.mediaskol.projet.dto.adresse.AdresseResponseDTO;
import fr.mediaskol.projet.dto.formation.FormationResponseDTO;
import fr.mediaskol.projet.dto.formation.TypeFormationDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
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
    private AdresseResponseDTO adresse;
    private List<FormationResponseDTO> formations;
    private Set<TypeFormationDTO> typesFormations;


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


        if (formateur.getTypesFormationDispensees() != null) {
            this.typesFormations = formateur.getTypesFormationDispensees().stream()
                    .map(TypeFormationDTO::new)
                    .collect(Collectors.toSet());
        } else {
            this.typesFormations = Collections.emptySet();
        }


        if (formateur.getFormationsDispensees() != null) {
            this.formations = formateur.getFormationsDispensees().stream()
                    .map(FormationResponseDTO::new)
                    .collect(Collectors.toList());
        } else {
            this.formations = Collections.emptyList();
        }



    }
}
