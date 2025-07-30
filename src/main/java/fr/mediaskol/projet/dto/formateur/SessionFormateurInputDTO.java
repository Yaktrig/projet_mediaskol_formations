package fr.mediaskol.projet.dto.formateur;

import fr.mediaskol.projet.bo.formateur.StatutSessionFormateur;
import fr.mediaskol.projet.dto.sessionFormation.SessionFOPResponseDTO;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SessionFormateurInputDTO {

    private Long idSessionFormateur;

    @Size(max = 2000, message = "{sessionFormateur.commentaireFormateur.size}")
    private String commentaireSessionFormateur;

    private Float coutSessionFormateur;

    private StatutSessionFormateur statutSessionFormateur;

    private FormateurResponseDTO formateur;

    private SessionFOPResponseDTO sessionFormation;
}
