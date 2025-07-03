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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ADDRESSE_ID")
    private long idAdresse;

    @Column(name="NOM_RUE", nullable=true, length = 250)
    private String nomRue;

    @Column(name="CODE_POSTAL", nullable=true, length = 5)
    private String codePostal;

    @Column(name="VILLE", nullable = true, length = 200)
    private String ville;

    @Column(name="NUM_DEPARTEMENT", nullable = true, length = 3)
    private String noDepartement;

    @Column(name="REGION", nullable = true, length = 120)
    private String region;
}
