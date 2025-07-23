package fr.mediaskol.projet.dto.formation;

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

    @NotNull(message = "{formation.themeFormation.notnull}")
    @NotBlank(message = "{formation.themeFormation.notblank}")
    @Size(min = 3, max = 20)
    private String themeFormation;

    @NotNull(message = "{formation.libelleFormation.notnull}")
    @NotBlank(message = "{formation.libelleFormation.notblank}")
    @Size(min = 3, max = 300)
    private String libelleFormation;

    private Long typeFormationId;



}
