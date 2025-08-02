package fr.mediaskol.projet.dto.sessionFormation;

import fr.mediaskol.projet.bo.sessionFormation.StatutSessionFormation;
import fr.mediaskol.projet.dto.adresse.DepartementDTO;
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
public class SessionFOPInputDTO {

    private long idSessionFormationPresentiel;

    @Size(max = 30, message = "{formation.numAfYoda.size}")
    private String noYoda;

    @Size(max = 50, message="{sessionFormation.libelle.size}")
    private String libelleSessionFormation;

    @Size(min=1, max = 5, message = "{sessionFormation.statutYoda.size}")
    @NotBlank(message = "{sessionFormation.statutYoda.notblank}")
    private String statutYoda;

    private StatutSessionFormation statutSessionFormation ;

    @Size(max=100, message="{sessionFormation.lieu.size}")
    private String lieuSessionFormation;

    private String commanditaire;

    private String confirmationRPE;

    private LocalDate dateDebutSession;

    private Integer nbHeureSession;

    private SalarieResponseDTO salarie;

    private FormationResponseDTO formation;

    private DepartementDTO departement;

    private FinSessionFormationRespDTO finSessionFormation;



}
