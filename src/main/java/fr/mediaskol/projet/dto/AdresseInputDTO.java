package fr.mediaskol.projet.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AdresseInputDTO {


    private long idAdresse;

    @Size(max = 250, message = "{adresse.rue.size}")
    private String rue;

    @Size(max = 5, message = "{adresse.codePostal.size}")
    private String codePostal;

    @Size(max = 200, message ="{adresse.ville.size}" )
    private String ville;

    private Long departementId;
}

