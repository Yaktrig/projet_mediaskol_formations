package fr.mediaskol.projet.bll.sessionFormation;

import fr.mediaskol.projet.bo.sessionFormation.SessionFormation;
import fr.mediaskol.projet.dto.sessionFormation.SessionFormationInputDTO;

import java.util.List;

public interface SessionFormationService {

    /***
     * Fonctionnalité qui permet de charger toutes les Sessions de formations
     */
    List<SessionFormation> chargerToutesSessionsFormations();

    /**
     * Todo permettre la recherche d'une session de formation par son thème, département
     */


    /**
     * Fonctionnalité qui permet de charger une session de formation par son id
     */
    SessionFormation chargerSessionFormationParId(long idSessionFormation);


    /**
     * Fonctionnalité qui permet d'ajouter une session de formation
     * @param sessionFormation
     */
    SessionFormation ajouterSessionFormation(SessionFormation sessionFormation);

    /**
     * Fonctionnalité qui permet de supprimer une session de formation
     */
    void supprimerSessionFormation(long idSessionFormation);


    /**
     * Fonctionnalité qui permet de modifier une session de formation
     * @param sessionFormationInputDTO
     * @return
     */
    SessionFormation modifierSessionFormation(SessionFormationInputDTO sessionFormationInputDTO);
}
