package fr.mediaskol.projet.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FormateurInputDTO {

    private Long idFormateur;

    @Size(min=3, max = 90, message="{personne.nom.size}")
    @NotNull(message="{personne.nom.notnull}")
    @NotBlank(message="{personne.nom.notblank}")
    private String nom;

    @Size(min=3, max = 150, message="{personne.prenom.size}")
    @NotNull(message="{personne.prenom.notnull}")
    @NotBlank(message="{personne.prenom.notblank}")
    private String prenom;

    @Size(min=10, max = 255, message = "{personne.email.size}")
    @NotNull(message = "{personne.email.notnull}")
    @NotBlank(message = "{personne.email.notblank}")
    @Email
    private String email;

    @Size(max = 10, message = "{apprenant.numPortable.size}")
    private String numPortable;

    @Size(min=1, max = 10, message = "{formateur.statutFormateur.size}")
    @NotNull(message = "{formateur.statutFormateur.notnull}")
    @NotBlank(message = "{formateur.statutFormateur.notblank}")
    private String statutFormateur;

    @Size(max = 1000, message = "{formateur.zoneIntervention.size}")
    private String zoneIntervention;


    @Size(max = 2000,message = "{formateur.commentaireFormateur.size}")
    private String commentaireFormateur;


    private Long adresseId;

    private List<Long> formationsIds;

    private Set<Long> typeFormationIds;
}
