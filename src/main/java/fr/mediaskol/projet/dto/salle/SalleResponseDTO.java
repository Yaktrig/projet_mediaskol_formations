package fr.mediaskol.projet.dto.salle;

import fr.mediaskol.projet.bo.salle.Salle;
import fr.mediaskol.projet.dto.adresse.AdresseResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SalleResponseDTO {

    private Long idSalle;
    private String nomSalle;
    private String nomContact;
    private String portableContact;
    private String mailContact;
    private Boolean cleSalle;
    private String digicodeSalle;
    private String commentaireSalle;
    private AdresseResponseDTO adresse;

    public SalleResponseDTO(Salle salle) {
        this.idSalle = salle.getIdSalle();
        this.nomSalle = salle.getNomSalle();
        this.nomContact = salle.getNomContact();
        this.portableContact = salle.getPortableContact();
        this.mailContact = salle.getMailContact();
        this.cleSalle = salle.getCleSalle();
        this.digicodeSalle = salle.getDigicodeSalle();
        this.commentaireSalle = salle.getCommentaireSalle();
        if (salle.getAdresse() != null) {
            this.adresse = new AdresseResponseDTO(salle.getAdresse());
        } else {
            this.adresse = null;
        }


    }


}
