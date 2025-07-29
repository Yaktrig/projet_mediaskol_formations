package fr.mediaskol.projet.dto.sessionFormation;

import fr.mediaskol.projet.bo.sessionFormation.StatutSessionFormation;
import fr.mediaskol.projet.dto.formation.FormationResponseDTO;
import fr.mediaskol.projet.dto.salarie.SalarieResponseDTO;
import jakarta.validation.constraints.NotBlank;
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

    private Long idSessionFormation;

    @Size(max = 30, message = "{formation.numAfYoda.size}")
    private String noYoda;

    @Size(max = 50, message = "{sessionFormation.libelle.size}")
    private String libelleSessionFormation;

    @Size(min = 1, max = 5, message = "{sessionFormation.statutYoda.size}")
    @NotBlank(message = "{sessionFormation.statutYoda.notblank}")
    private String statutYoda;

    private LocalDate dateDebutSession;

    private Integer nbHeureSession;

    private StatutSessionFormation statutSessionFormation;

    private FormationResponseDTO formation;

    private FinSessionFormationRespDTO finSessionFormation;

    private SalarieResponseDTO salarie;

    @Size(max = 300, message = "{sessionFormationDistanciel.contratSessionFoad.size}")
    private String contratSessionFormationDistanciel;

    private Integer nbBlocSessionFormationDistanciel;

    private LocalDate dateFinSessionFormationDistanciel;

    private LocalDate dateRelanceSessionFormationDistanciel;

    @Size(max = 2000, message = "{sessionFormationDistanciel.commentaireSessionFoad.size}")
    private String commentaireSessionFormationDistanciel;
}
