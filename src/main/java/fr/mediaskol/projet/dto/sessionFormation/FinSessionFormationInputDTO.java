package fr.mediaskol.projet.dto.sessionFormation;

import fr.mediaskol.projet.bo.sessionFormation.StatutFinSessionFormation;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FinSessionFormationInputDTO {

    /**
     * Déclaration des attributs de fin de formation
     */
    private Long idFinSessionFormation;

    @Size(max = 5, message="{sessionFinFormation.statutYoda.size}")
    private String statutYodaFinSessionFormation;


    private LocalDate dateLimiteYodaFinSessionFormation;

    @Size(max=2000, message="{sessionFinFormation.commentaire.size}")
    private String commentaireFinSessionFormation;


    private StatutFinSessionFormation statutFinSessionFormation;
}
