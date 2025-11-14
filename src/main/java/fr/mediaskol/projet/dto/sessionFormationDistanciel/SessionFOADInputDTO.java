package fr.mediaskol.projet.dto.sessionFormationDistanciel;


import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SessionFOADInputDTO {

    private Long idSessionFormationDistanciel;

    @Size(max = 300, message = "{sessionFormationDistanciel.contratSessionFoad.size}")
    private String contratSessionFormationDistanciel;

    private Integer nbBlocSessionFormationDistanciel;

    private LocalDate dateDebutSessionFormationDistanciel;

    private LocalDate dateFinSessionFormationDistanciel;

    private LocalDate dateRelanceSessionFormationDistanciel;

    @Size(max = 2000, message = "{sessionFormationDistanciel.commentaireSessionFoad.size}")
    private String commentaireSessionFormationDistanciel;
}
