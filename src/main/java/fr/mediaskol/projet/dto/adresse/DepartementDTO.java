package fr.mediaskol.projet.dto.adresse;

import fr.mediaskol.projet.bo.departement.Departement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class DepartementDTO {


    private String numDepartement;
    private String couleurDepartement;

    public DepartementDTO(Departement d) {

        this.numDepartement = d.getNumDepartement();
        this.couleurDepartement = d.getCouleurDepartement();
    }
}
