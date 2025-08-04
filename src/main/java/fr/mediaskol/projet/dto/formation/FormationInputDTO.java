package fr.mediaskol.projet.dto.formation;

import fr.mediaskol.projet.bo.formation.TypeFormation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FormationInputDTO {


    private Long idFormation;

    @NotBlank(message = "{formation.themeFormation.notblank}")
    @Size(min = 3, max = 20 , message="{formation.themeFormation.size}")
    private String themeFormation;

    @NotBlank(message = "{formation.libelleFormation.notblank}")
    @Size(min = 3, max = 300)
    private String libelleFormation;

    //private Long typeFormationId;

    private TypeFormation typeFormation;



}
