package fr.mediaskol.projet.dto.formation;

import fr.mediaskol.projet.bo.formation.TypeFormation;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TypeFormationDTO {

    private Long id;
    private String libelle;


    public TypeFormationDTO(TypeFormation tf) {
        this.id = tf.getIdTypeFormation() ;
        this.libelle = tf.getLibelleTypeFormation();
    }
}
