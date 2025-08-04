package fr.mediaskol.projet.dto.formation;

import fr.mediaskol.projet.bo.formation.TypeFormation;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TypeFormationDTO {

    private Long idTypeFormation;
    private String libelleTypeFormation;




    public TypeFormationDTO(TypeFormation tf) {
        this.idTypeFormation = tf.getIdTypeFormation() ;
        this.libelleTypeFormation = tf.getLibelleTypeFormation();
    }


}
