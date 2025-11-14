package fr.mediaskol.projet.bo.sessionFormation;

import fr.mediaskol.projet.bo.formation.Formation;
import fr.mediaskol.projet.bo.salarie.Salarie;
import fr.mediaskol.projet.bo.departement.Departement;
import fr.mediaskol.projet.bo.sessionFormationDistanciel.SessionFormationDistanciel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

/**
 * Représente une session de formation (présentiel, distanciel) dans le système
 * de gestion.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@Entity
@Table(name = "SESSION_FORMATION")
@Inheritance(strategy = InheritanceType.JOINED)
public class SessionFormation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SESSION_FORMATION_ID")
    private Long idSessionFormation;

    @Column(name = "NO_AF_YODA", unique = true, length = 30, nullable = true)
    private String noYoda;

    @Column(name = "LIBELLE_SESSION_FORMATION", length = 50)
    @NotNull
    private String libelleSessionFormation;

    @Builder.Default
    @Column(name = "STATUT_YODA", nullable = false, length = 5)
    @NotNull
    private String statutYoda = "DO";

    @Column(name = "DATE_DEBUT_SESSION", nullable = false)
    @NotNull
    private LocalDate dateDebutSession;

    @Column(name = "NB_HEURE_SESSION", nullable = false)
    @NotNull
    private Integer nbHeureSession;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUT_SESSION_FORMATION", nullable = false)
    @NotNull
    @Builder.Default
    private StatutSessionFormation statutSessionFormation = StatutSessionFormation.SESSION_FORMATION_NON_COMMENCEE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FORMATION_ID")
    @NotNull
    private Formation formation;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "FIN_SESSION_FORMATION_ID")
    private FinSessionFormation finSessionFormation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SALARIE_ID")
    @NotNull
    private Salarie salarie;

    // Nouveau champ Departement
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPARTEMENT_ID")
    private Departement departement;

    // Nouveau champ SessionFormationDistanciel
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "SESSION_FORMATION_DISTANCIEL_ID")
    private SessionFormationDistanciel sessionFormationDistanciel;
}
