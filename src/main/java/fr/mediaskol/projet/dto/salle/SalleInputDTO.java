package fr.mediaskol.projet.dto.salle;

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
public class SalleInputDTO {

    private Long idSalle;

    @Size(min=5, max = 255, message = "{salle.nom.size}")
    @NotBlank(message = "{salle.nom.notblank}")
    private String nomSalle;

    @Size(max = 200, message = "{salle.nomContact.size}")
    private String nomContact;

    @Size(max = 10, message = "{salle.portableContact.size}")
    private String portableContact;

    @Size(max = 255, message = "{salle.mailContact.size}")
    private String mailContact;


    private Boolean cleSalle;

    @Size(max = 10, message = "{salle.digiCodeSalle.size}")
    private String digicodeSalle;

    @Size(max = 2000, message = "{salle.commentaireSalle.size}")
    private String commentaireSalle;

    private Long adresseId;
}
