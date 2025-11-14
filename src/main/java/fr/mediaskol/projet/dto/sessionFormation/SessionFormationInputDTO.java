package fr.mediaskol.projet.dto.sessionFormation;

import java.time.LocalDate;
import fr.mediaskol.projet.entities.StatutSessionFormation;

public class SessionFormationInputDTO {

    private Long idSessionFormation;
    private String libelleSessionFormation;
    private String noYoda;
    private String statutYoda;
    private LocalDate dateDebutSession;
    private int nbHeureSession;
    private StatutSessionFormation statutSessionFormation;
    private Long departementId;
    private Long finSessionFormationId;
    private Long sessionFormationDistancielId;

    // Getters
    public Long getIdSessionFormation() {
        return idSessionFormation;
    }

    public String getLibelleSessionFormation() {
        return libelleSessionFormation;
    }

    public String getNoYoda() {
        return noYoda;
    }

    public String getStatutYoda() {
        return statutYoda;
    }

    public LocalDate getDateDebutSession() {
        return dateDebutSession;
    }

    public int getNbHeureSession() {
        return nbHeureSession;
    }

    public StatutSessionFormation getStatutSessionFormation() {
        return statutSessionFormation;
    }

    public Long getDepartementId() {
        return departementId;
    }

    public Long getFinSessionFormationId() {
        return finSessionFormationId;
    }

    public Long getSessionFormationDistancielId() {
        return sessionFormationDistancielId;
    }
}
