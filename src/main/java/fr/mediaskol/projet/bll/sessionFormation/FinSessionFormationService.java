package fr.mediaskol.projet.bll.sessionFormation;


import fr.mediaskol.projet.bo.sessionFormation.FinSessionFormation;
import fr.mediaskol.projet.dto.sessionFormation.FinSessionFormationInputDTO;
import java.util.List;

public interface FinSessionFormationService {

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


    /**
     * Fonctionnalité qui permet d'ajouter une fin de session de formation
     * @param finSessionFormation
     */
    FinSessionFormation ajouterFinSessionFormation(FinSessionFormation finSessionFormation);

    /**
     * Fonctionnalité qui permet de supprimer une fin de session de formation
     */
    void supprimerFinSessionFormation(long idFinSessionFormation);


    /**
     * Fonctionnalité qui permet de modifier une fin de session de formation
     * @param finSessionFormationInputDTO
     * @return
     */
    FinSessionFormation modifierFinSessionFormation(FinSessionFormationInputDTO finSessionFormationInputDTO);
}
