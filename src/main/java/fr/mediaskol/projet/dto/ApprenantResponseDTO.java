package fr.mediaskol.projet.dto;

import fr.mediaskol.projet.bo.adresse.Adresse;
import fr.mediaskol.projet.bo.apprenant.Apprenant;
import fr.mediaskol.projet.bo.apprenant.StatutNumPasseport;
import fr.mediaskol.projet.bo.formation.TypeFormation;
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
    private AdresseResponseDTO adresse;
    private Set<TypeFormation> typeFormations;


    /**
     * Constructeur avec entité
     */
    public ApprenantResponseDTO(Apprenant apprenant){
        this.idApprenant = apprenant.getIdPersonne();
        this.nom = apprenant.getNom();
        this.prenom = apprenant.getPrenom();
        this.email = apprenant.getEmail();
        this.numPortable = apprenant.getNumPortable();
        this.dateNaissance = apprenant.getDateNaissance();
        this.adresse = new AdresseResponseDTO(apprenant.getAdresse());
        this.numPasseport = apprenant.getNumPasseport();
        this.statutNumPasseport = apprenant.getStatutNumPasseport();
        this.commentaireApprenant = apprenant.getCommentaireApprenant();
        this.typeFormations = apprenant.getTypesFormationSuivies();
    }



}
