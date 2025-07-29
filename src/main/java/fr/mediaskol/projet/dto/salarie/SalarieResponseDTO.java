package fr.mediaskol.projet.dto.salarie;

import fr.mediaskol.projet.bo.salarie.Salarie;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SalarieResponseDTO {

    /**
     * Déclaration des attributs
     */
    private Long idSalarie;
    private String nom;
    private String prenom;
    private String mail;
    private String couleurSalarie;

    /**
     * Constructeur
     */
    public SalarieResponseDTO(Salarie salarie) {
        this.idSalarie = salarie.getIdPersonne();
        this.nom = salarie.getNom();
        this.prenom = salarie.getPrenom();
        this.mail = salarie.getEmail();
        this.couleurSalarie = salarie.getCouleurSalarie();
    }
}
