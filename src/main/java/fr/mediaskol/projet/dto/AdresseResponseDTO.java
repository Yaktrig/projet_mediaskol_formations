package fr.mediaskol.projet.dto;

import fr.mediaskol.projet.bo.adresse.Adresse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AdresseResponseDTO {

    private long idAdresse;
    private String rue;
    private String codePostal;
    private String ville;
    private DepartementDTO departement;

    /**
     * Constructeur avec entité
     */
    public AdresseResponseDTO(Adresse adresse) {
        this.idAdresse = adresse.getIdAdresse();
        this.rue = adresse.getRue();
        this.codePostal = adresse.getCodePostal();
        this.ville = adresse.getVille();
        this.departement = new DepartementDTO(adresse.getDepartement());
    }
}
