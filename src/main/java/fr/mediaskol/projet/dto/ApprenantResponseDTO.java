package fr.mediaskol.projet.dto;

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

    private Long idPersonne;
    private String nom;
    private String prenom;
    private String email;
    private String numPortable;
    private LocalDate dateNaissance;
    private String numPasseport;
    private StatutNumPasseport statutNumPasseport;
    private String commentaireApprenant;
    private String adresse;
    private Set<TypeFormation> typeFormations;


    /**
     * Constructeur avec entité (ou utilisation d'un mapper)
     */
    public ApprenantResponseDTO(Apprenant apprenant){
        this.idPersonne = apprenant.getIdPersonne();
        this.nom = apprenant.getNom();
        this.prenom = apprenant.getPrenom();
        this.email = apprenant.getEmail();
        this.numPortable = apprenant.getNumPortable();
        this.dateNaissance = apprenant.getDateNaissance();
        this.adresse =apprenant.getAdresse() != null ? apprenant.getAdresse().getRue() + " " + apprenant.getAdresse().getCodePostal() + " " + apprenant.getAdresse().getVille() : "";
        this.numPasseport = apprenant.getNumPasseport();
        this.statutNumPasseport = apprenant.getStatutNumPasseport();
        this.commentaireApprenant = apprenant.getCommentaireApprenant();
        this.typeFormations = apprenant.getTypesFormationSuivies();
    }



}
