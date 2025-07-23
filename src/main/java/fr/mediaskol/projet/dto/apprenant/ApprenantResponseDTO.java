package fr.mediaskol.projet.dto.apprenant;

import fr.mediaskol.projet.bo.apprenant.Apprenant;
import fr.mediaskol.projet.bo.apprenant.StatutNumPasseport;
import fr.mediaskol.projet.bo.formation.TypeFormation;
import fr.mediaskol.projet.dto.adresse.AdresseResponseDTO;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ApprenantResponseDTO {

    private Long idApprenant;
    private String nom;
    private String prenom;
    private String email;
    private String numPortable;
    private LocalDate dateNaissance;
    private String numPasseport;
    private StatutNumPasseport statutNumPasseport;
    private String commentaireApprenant;
    private Boolean apprenantActif;
    private AdresseResponseDTO adresse;
    private Set<TypeFormation> typeFormationsIds;


    /**
     * Constructeur avec entité
     */
    public ApprenantResponseDTO(Apprenant apprenant) {
        this.idApprenant = apprenant.getIdPersonne();
        this.nom = apprenant.getNom();
        this.prenom = apprenant.getPrenom();
        this.email = apprenant.getEmail();
        this.numPortable = apprenant.getNumPortable();
        this.dateNaissance = apprenant.getDateNaissance();
        this.apprenantActif = apprenant.getApprenantActif();

        if (apprenant.getAdresse() != null) {
            this.adresse = new AdresseResponseDTO(apprenant.getAdresse());
        } else {
            this.adresse = null;
        }

        this.numPasseport = apprenant.getNumPasseport();
        this.statutNumPasseport = apprenant.getStatutNumPasseport();
        this.commentaireApprenant = apprenant.getCommentaireApprenant();
        this.typeFormationsIds = apprenant.getTypesFormationSuivies();
    }


}
