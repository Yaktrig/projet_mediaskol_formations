package fr.mediaskol.projet.dto;

import fr.mediaskol.projet.bo.formation.Formation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FormationResponseDTO {

    private Long idFormation;
    private String themeFormation;
    private String libelleFormation;
    private Long idTypeFormation;

    /**
     * Constructeur avec entité
     */
    public FormationResponseDTO(Formation formation) {
        this.idFormation = formation.getIdFormation();
        this.idTypeFormation = formation.getTypeFormation().getIdTypeFormation();
        this.libelleFormation = formation.getLibelleFormation();
        this.themeFormation = formation.getThemeFormation();
    }
}
