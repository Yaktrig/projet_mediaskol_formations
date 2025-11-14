package fr.mediaskol.projet.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "session_formation")
public class SessionFormation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String libelleSessionFormation;
    private String noYoda;
    private String statutYoda;
    private LocalDate dateDebutSession;
    private int nbHeureSession;

    @Enumerated(EnumType.STRING)
    private StatutSessionFormation statutSessionFormation;

    // Exemple de relation
    @OneToOne(cascade = CascadeType.ALL)
    private SessionFormationDistanciel sessionFormationDistanciel;

    @ManyToOne
    private Departement departement;

    // Getters et setters
    public Long getId() {
        return id;
    }

    public String getLibelleSessionFormation() {
        return libelleSessionFormation;
    }

    public void setLibelleSessionFormation(String libelleSessionFormation) {
        this.libelleSessionFormation = libelleSessionFormation;
    }

    public String getNoYoda() {
        return noYoda;
    }

    public void setNoYoda(String noYoda) {
        this.noYoda = noYoda;
    }

    public String getStatutYoda() {
        return statutYoda;
    }

    public void setStatutYoda(String statutYoda) {
        this.statutYoda = statutYoda;
    }

    public LocalDate getDateDebutSession() {
        return dateDebutSession;
    }

    public void setDateDebutSession(LocalDate dateDebutSession) {
        this.dateDebutSession = dateDebutSession;
    }

    public int getNbHeureSession() {
        return nbHeureSession;
    }

    public void setNbHeureSession(int nbHeureSession) {
        this.nbHeureSession = nbHeureSession;
    }

    public StatutSessionFormation getStatutSessionFormation() {
        return statutSessionFormation;
    }

    public void setStatutSessionFormation(StatutSessionFormation statutSessionFormation) {
        this.statutSessionFormation = statutSessionFormation;
    }

    public SessionFormationDistanciel getSessionFormationDistanciel() {
        return sessionFormationDistanciel;
    }

    public void setSessionFormationDistanciel(SessionFormationDistanciel sessionFormationDistanciel) {
        this.sessionFormationDistanciel = sessionFormationDistanciel;
    }

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }
}