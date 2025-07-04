package fr.mediaskol.projet.bo.adresse;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// POJO Lombok
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

@Entity
@Table(name="ADRESSE")
public class Adresse {

    // Identifiant de l'adresse
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ADRESSE_ID")
    private long idAdresse;

    // Numéroe et Nom de la rue - peut être nul si on n'a pas le numéro et nom de la rue
    @Column(name = "RUE", nullable = true, length = 250)
    private String rue;

    // Code postal - peut être nulle si on n'a pas le code postal
    @Column(name = "CODE_POSTAL", nullable = true, length = 5)
    private String codePostal;

    // Nom de la ville - peut être nulle si on n'a pas le nom de la ville
    @Column(name = "VILLE", nullable = true, length = 200)
    private String ville;

    // Le numéro du département est connu si le code postal est connu
    @Column(name = "NUM_DEPARTEMENT", nullable = true, length = 3)
    private String noDepartement;

    // Le nom de la région peut être nul - mais serait intéressant pour une visibilité commerciale
    @Column(name = "REGION", nullable = true, length = 120)
    private String region;

}
