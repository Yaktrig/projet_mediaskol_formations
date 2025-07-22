package fr.mediaskol.projet.bll;

import fr.mediaskol.projet.bo.formation.Formation;
import fr.mediaskol.projet.bo.formation.TypeFormation;

import java.util.List;

public interface FormationService {

    /***
     * Fonctionnalité qui permet de charger toutes les formations
     */
    List<Formation> chargerToutesFormations();


    /**
     * Fonctionnalité qui permet d'ajouter une formation
     * @param formation
     * @param typeFormation
     */
    void ajouterFormation(Formation formation, TypeFormation typeFormation);

    /**
     * Fonctionnalité qui permet de supprimer une formation
     */
    void supprimerFormation(long idFormation);


}
