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

    private Long id;
    private String libelle;




    public TypeFormationDTO(TypeFormation tf) {
        this.id = tf.getIdTypeFormation() ;
        this.libelle = tf.getLibelleTypeFormation();
    }


}
