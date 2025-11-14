package fr.mediaskol.projet.bll.sessionFormation;

import fr.mediaskol.projet.bo.sessionFormationDistanciel.SessionFormationDistanciel;
import fr.mediaskol.projet.dto.sessionFormation.SessionFOADInputDTO;

import java.util.List;

public interface SessionFOADService {

    /***
     * Fonctionnalité qui permet de charger toutes les Sessions de formations à distance
     */
    List<SessionFormationDistanciel> chargerToutesSessionsFoads();

    /**
     * Todo permettre la recherche d'une session de formation à distance par son thème, département
     */


    /**
     * Fonctionnalité qui permet de charger une session de formation à distance par son id
     */
    SessionFormationDistanciel chargerSessionFoadParId(long idSessionFormationDistanciel);


    /**
     * Fonctionnalité qui permet d'ajouter une session de formation à distance
     * @param sessionFoad
     */
    SessionFormationDistanciel ajouterSessionFoad(SessionFormationDistanciel sessionFoad);

    /**
     * Fonctionnalité qui permet de modifier une session de formation à distance
     * @param sessionFoadInputDTO
     * @return
     */
    SessionFormationDistanciel modifierSessionFoad(SessionFOADInputDTO sessionFoadInputDTO);

    /**
     * Fonctionnalité qui permet de supprimer une session de formation à distance
     */
    void supprimerSessionFoad(long idSessionFormationDistanciel);



}
