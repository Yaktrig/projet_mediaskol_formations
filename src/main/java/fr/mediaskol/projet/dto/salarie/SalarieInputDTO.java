package fr.mediaskol.projet.dto.salarie;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SalarieInputDTO {

    private Long idSalarie;
    private String nom;
    private String prenom;

    @Email
    private String mail;

    @Size(min=3, max=7, message = "{salarie.couleurSalarie.size}")
    @NotBlank(message = "{salarie.couleurSalarie.notblank}")
    private String couleurSalarie;

}
