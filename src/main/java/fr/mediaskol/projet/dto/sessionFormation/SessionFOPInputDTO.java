package fr.mediaskol.projet.dto.sessionFormation;

import fr.mediaskol.projet.dto.adresse.DepartementDTO;
import fr.mediaskol.projet.dto.formation.FormationResponseDTO;
import fr.mediaskol.projet.dto.salarie.SalarieResponseDTO;
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



    @Size(max=100, message="{sessionFormation.lieu.size}")
    private String lieuSessionFormation;

    private String commanditaire;

    private String confirmationRPE;

    private LocalDate dateDebutSession;

    private Integer nbHeureSession;

    private SalarieResponseDTO salarie;

    private FormationResponseDTO formation;

    private DepartementDTO departement;

    private Long finSessionFormationId;



}
