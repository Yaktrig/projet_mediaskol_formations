package fr.mediaskol.projet.dto.sessionFormation;

import fr.mediaskol.projet.bo.sessionFormation.SessionFormationPresentiel;

import fr.mediaskol.projet.dto.adresse.DepartementDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SessionFOPResponseDTO {


    private String lieuSessionFormation;
    private String commanditaire;
    private String confirmationRPE;
    private DepartementDTO departement;

    /**
     * Constructeur
     */
    public SessionFOPResponseDTO(SessionFormationPresentiel sessionFOP) {




        if (sessionFOP.getDepartement() != null) {
            this.departement = new DepartementDTO(sessionFOP.getDepartement());
        } else {
            this.departement = null;
        }



        this.lieuSessionFormation = sessionFOP.getLieuSessionFormation();
        this.commanditaire = sessionFOP.getCommanditaire();
        this.confirmationRPE = sessionFOP.getConfirmationRPE();
    }

}
