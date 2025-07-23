package fr.mediaskol.projet.dto.apprenant;

import fr.mediaskol.projet.bo.apprenant.StatutNumPasseport;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ApprenantInputDTO {

    private Long idApprenant;

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

    @NotNull(message = "{apprenant.dateNaissance.notnull}")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate dateNaissance;


    private boolean apprenantActif;

    @Size(max = 120, message = "{apprenant.numPasseport.size}")
    private String numPasseport;

    @NotNull(message = "{apprenant.statutNumPasseport.notnull}")
    private StatutNumPasseport statutNumPasseport;

    @Size(max = 2000, message = "{apprenant.commentaireApprenant.size}")
    private String commentaireApprenant;


    private Long adresseId;

    private Set<Long> typeFormationIds;

}
