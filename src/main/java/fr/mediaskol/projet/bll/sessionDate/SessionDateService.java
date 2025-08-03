package fr.mediaskol.projet.bll.sessionDate;

import fr.mediaskol.projet.bo.sessionDate.SessionDate;
import fr.mediaskol.projet.dto.sessionDate.SessionDateInputDTO;

import java.util.List;

public interface SessionDateService {

    /***
     * Fonctionnalité qui permet de charger toutes les lieux et dates des sessions
     */
    List<SessionDate> chargerToutesSessionsLieuDate();

    /**
     * Fonctionnalité qui permet de charger le lieu et la date d'une session par son id
     */
    SessionDate chargerSessionLieuDateParId(long idSessionLieuDate);


    /**
     * Fonctionnalité qui permet d'ajouter à une session un lieu et une date
     * @param sessionDate
     */
    SessionDate ajouterSessionLieuDate(SessionDate sessionDate);

    /**
     * Fonctionnalité qui permet de modifier le lieu et/ou la date d'une session
     * @param sessionDateInputDTO
     * @return
     */
    SessionDate modifierSessionLieuDate(SessionDateInputDTO sessionDateInputDTO);

    /**
     * Fonctionnalité qui permet de supprimer un lieu et/ou une date d'une session
     */
    void supprimerSessionLieuDate(long idSessionLieuDate);
}
