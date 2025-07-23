package fr.mediaskol.projet.dto.salle;

import fr.mediaskol.projet.bo.salle.Salle;
import fr.mediaskol.projet.dto.adresse.AdresseResponseDTO;

public class SalleResponseDTO {

    private Long idSalle;
    private String nomSalle;

    private String nomContact;

    private String portableContact;

    private String mailContact;

    private boolean cleSalle;

    private String digiCodeSalle;

    private String commentaireSalle;

    private AdresseResponseDTO adresse;

    public SalleResponseDTO(Salle salle) {
        this.idSalle = salle.getIdSalle();
        this.nomSalle = salle.getNomSalle();
        this.nomContact = salle.getNomContact();
        this.portableContact = salle.getPortableContact();
        this.mailContact = salle.getMailContact();
        this.cleSalle = salle.getCleSalle();
        this.digiCodeSalle = salle.getDigiCodeSalle();
        this.commentaireSalle = salle.getCommentaireSalle();
        this.adresse = new AdresseResponseDTO(salle.getAdresse());


    }


}
