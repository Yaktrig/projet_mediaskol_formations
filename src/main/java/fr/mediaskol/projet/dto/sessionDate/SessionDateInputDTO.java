package fr.mediaskol.projet.dto.sessionDate;


import fr.mediaskol.projet.bo.sessionDate.StatutSessionDate;
import fr.mediaskol.projet.dto.formateur.SessionFormateurRespDTO;
import fr.mediaskol.projet.dto.salle.SessionSalleRespDTO;
import fr.mediaskol.projet.dto.sessionFormation.SessionFOPResponseDTO;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SessionDateInputDTO {

    /**
     * Déclaration des attributs
     */
    private Long idSessionDate;

    private LocalDate dateSession;


    private Integer duree;

    private LocalDateTime heureVisio;

    private StatutSessionDate statutSessionDate;

    private SessionFormateurRespDTO sessionFormateurId;

    private SessionFOPResponseDTO sessionFormationId;

    private SessionSalleRespDTO sessionSalleId;


}
