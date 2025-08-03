package fr.mediaskol.projet.dto.sessionFormation;

import fr.mediaskol.projet.bo.sessionFormation.SessionFormationPresentiel;

import fr.mediaskol.projet.bo.sessionFormation.StatutSessionFormation;
import fr.mediaskol.projet.dto.adresse.DepartementDTO;
import fr.mediaskol.projet.dto.formateur.SessionFormateurRespDTO;
import fr.mediaskol.projet.dto.formation.FormationResponseDTO;
import fr.mediaskol.projet.dto.salarie.SalarieResponseDTO;
import fr.mediaskol.projet.dto.salle.SessionSalleRespDTO;
import fr.mediaskol.projet.dto.sessionDate.SessionDateRespDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder
public class SessionFOPResponseDTO extends SessionFormationResponseDTO {

    private Long idSessionFormationPresentiel;
    private String noYoda;
    private String libelleSessionFormation;
    private String statutYoda;
    private LocalDate dateDebutSession;
    private Integer nbHeureSession;
    private StatutSessionFormation statutSessionFormation = StatutSessionFormation.SESSION_FORMATION_NON_COMMENCEE;
    private FormationResponseDTO formation;
    private FinSessionFormationRespDTO finSessionFormation;
    private SalarieResponseDTO salarie;
    private String lieuSessionFormation;
    private String commanditaire;
    private String confirmationRPE;
    private DepartementDTO departement;
    private List<SessionSalleRespDTO> sessionsSalle;
    private List<SessionFormateurRespDTO> sessionsFormateur;
    private List<SessionDateRespDTO> sessionsLieuDate;

    /**
     * Constructeur
     */
    public SessionFOPResponseDTO(SessionFormationPresentiel sessionFOP) {

        this.idSessionFormationPresentiel = sessionFOP.getIdSessionFormation();
        this.noYoda = sessionFOP.getNoYoda();
        this.libelleSessionFormation = sessionFOP.getLibelleSessionFormation();
        this.statutYoda = sessionFOP.getStatutYoda();
        this.dateDebutSession = sessionFOP.getDateDebutSession();
        this.nbHeureSession = sessionFOP.getNbHeureSession();
        this.statutSessionFormation = sessionFOP.getStatutSessionFormation();

        if (sessionFOP.getFormation() != null) {
            this.formation = new FormationResponseDTO(sessionFOP.getFormation());
        } else {
            this.formation = null;
        }


        if (sessionFOP.getFinSessionFormation() != null) {
            this.finSessionFormation = new FinSessionFormationRespDTO(sessionFOP.getFinSessionFormation());
        } else {
            this.finSessionFormation = null;
        }


        if (sessionFOP.getSalarie() != null) {
            this.salarie = new SalarieResponseDTO(sessionFOP.getSalarie());
        } else {
            this.salarie = null;
        }


        if (sessionFOP.getDepartement() != null) {
            this.departement = new DepartementDTO(sessionFOP.getDepartement());
        } else {
            this.departement = null;
        }

        this.lieuSessionFormation = sessionFOP.getLieuSessionFormation();
        this.commanditaire = sessionFOP.getCommanditaire();
        this.confirmationRPE = sessionFOP.getConfirmationRPE();
        this.sessionsSalle = new ArrayList<>();
        this.sessionsFormateur = new ArrayList<>();
        this.sessionsLieuDate =  new ArrayList<>();
    }

}
