package fr.mediaskol.projet.dto.formation;

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
    private TypeFormationDTO typeFormation;

    /**
     * Constructeur avec entité
     */
    public FormationResponseDTO(Formation formation) {
        this.idFormation = formation.getIdFormation();
        this.libelleFormation = formation.getLibelleFormation();
        this.themeFormation = formation.getThemeFormation();
        if (formation.getTypeFormation() != null) {
            this.typeFormation = new TypeFormationDTO(formation.getTypeFormation());
        }
    }
}
