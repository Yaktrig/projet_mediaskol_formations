package fr.mediaskol.projet.bll.sessionDate;

import fr.mediaskol.projet.bo.sessionDate.SessionDate;
import fr.mediaskol.projet.dto.sessionDate.SessionDateInputDTO;

import java.util.List;

public interface SessionDateService {

    /***
     * Fonctionnalité qui permet de charger toutes les dates des sessions
     */
    List<SessionDate> chargerToutesSessionsDate();

    /**
     * Fonctionnalité qui permet de charger la date d'une session par son id
     */
    SessionDate chargerSessionDateParId(long idSessionDate);


    /**
     * Fonctionnalité qui permet d'ajouter à une session une date
     * @param sessionDate
     */
    SessionDate ajouterSessionDate(SessionDate sessionDate);

    /**
     * Fonctionnalité qui permet de modifier la date d'une session
     * @param sessionDateInputDTO
     * @return
     */
    SessionDate modifierSessionDate(SessionDateInputDTO sessionDateInputDTO);

    /**
     * Fonctionnalité qui permet de supprimer une date d'une session
     */
    void supprimerSessionDate(long idSessionDate);
}
