package fr.mediaskol.projet.bll;

import fr.mediaskol.projet.bo.adresse.Adresse;
import fr.mediaskol.projet.bo.apprenant.Apprenant;
import fr.mediaskol.projet.bo.formation.TypeFormation;


import java.time.LocalDate;
import java.util.List;

public interface ApprenantService {

    // Fonctionnalité qui permet de charger tous les apprenants
    List<Apprenant> chargerTousApprenants();

    /**
     * Fonctionnalités qui retourne un ou des apprenants selon des critères de recherches
     *
     * @param nom
     * @param email
     * @param dateNaissance
     * @param numDepartement
     * @param ville
     */
    List<Apprenant> rechercheApprenants(String nom, String email, LocalDate dateNaissance, Long numDepartement, String ville);


    /**
     * Fonctionnalité qui va ajouter un apprenant
     * @param apprenant
     */
    void ajouterApprenant(Apprenant apprenant);

    /**
     * Fonctionnalité qui va ajouter une adresse à un apprenant
     * @param apprenant
     * @param adresse
     */
    void ajouterAdresseApprenant(Apprenant apprenant, Adresse adresse);

    /**
     * Fonctionnalité qui va ajouter un type de formation à un apprenant
     * @param apprenant
     * @param typeFormation
     */
    void ajouterTypeFormationApprenant(Apprenant apprenant, TypeFormation typeFormation);
}
