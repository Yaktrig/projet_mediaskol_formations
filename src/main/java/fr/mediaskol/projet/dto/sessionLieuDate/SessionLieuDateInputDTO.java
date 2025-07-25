package fr.mediaskol.projet.dto.sessionLieuDate;


import fr.mediaskol.projet.bo.sessionLieuDate.StatutSessionLieuDate;
import fr.mediaskol.projet.dto.formateur.SessionFormateurRespDTO;
import fr.mediaskol.projet.dto.salle.SessionSalleRespDTO;
import fr.mediaskol.projet.dto.sessionFormation.SessionFormationRespDTO;

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
public class SessionLieuDateInputDTO {

    /**
     * Déclaration des attributs
     */
    private Long idSessionLieuDate;

    private LocalDate dateSession;

    @Size(max = 100, message = "{sessionLieuDate.lieuSession.size}")
    private String lieuSession;

    private Integer duree;

    private LocalDateTime heureVisio;

    private StatutSessionLieuDate statutSessionLieuDate;

    private SessionFormateurRespDTO sessionFormateurId;

    private SessionFormationRespDTO sessionFormationId;

    private SessionSalleRespDTO sessionSalleId;


}
