package fr.mediaskol.projet.bll.sessionFormation;

import fr.mediaskol.projet.bo.sessionFormation.SessionFormation;

import java.util.List;

public interface FinSessionFormation {

    /***
     * Fonctionnalité qui permet de charger toutes les fins de sessions de formations
     */
    List<FinSessionFormation> chargerToutesFinSessionFormations();

    /**
     * Todo permettre la recherche d'une fin de session de formation par sa date ?
     */

    /**
     * Fonctionnalité qui permet de charger une fin de session de formation par son id
     */
    FinSessionFormation chargerFinSessionFormationParId(long idFinSessionFormation);




}
