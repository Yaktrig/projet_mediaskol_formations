package fr.mediaskol.projet.bll.apprenant;

import fr.mediaskol.projet.bo.adresse.Adresse;
import fr.mediaskol.projet.bo.apprenant.Apprenant;
import fr.mediaskol.projet.bo.formation.TypeFormation;
import fr.mediaskol.projet.dto.apprenant.ApprenantInputDTO;


import java.util.List;
import java.util.Set;

public interface ApprenantService {

    /**
     *     Fonctionnalité qui permet de charger tous les apprenants
      */
    List<Apprenant> chargerTousApprenants();

    /**
     * Fonctionnalité qui retourne un ou des apprenants selon des critères de recherches
     *
     * @param termeRecherche
     */
    List<Apprenant> rechercheApprenants(String termeRecherche);

//
//    /**
//     * Fonctionnalité qui retourne les apprenants d'une session de formation
//     */
//    List<Apprenant> find



    /**
     * Fonctionnalité qui va ajouter un apprenant
     * @param apprenant
     * @param adresse
     * @param typesFormationSuivies
     */
    void ajouterApprenant(Apprenant apprenant, Adresse adresse, Set<TypeFormation> typesFormationSuivies);


    /**
     * Fonctionnalité qui va modifier un apprenant
     * @param dto
     * @return
     */
    Apprenant modifierApprenant(ApprenantInputDTO dto);

    /**
     * Fonctionnalité qui va supprimer un apprenant à partir de son identifiant
     * @param idApprenant
     */
    void supprimerApprenant(long idApprenant);




}
