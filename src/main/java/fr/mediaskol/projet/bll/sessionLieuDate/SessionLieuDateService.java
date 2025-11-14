package fr.mediaskol.projet.bll.sessionLieuDate;

import fr.mediaskol.projet.bo.sessionLieuDate.SessionLieuDate;
import fr.mediaskol.projet.dto.sessionLieuDate.SessionLieuDateInputDTO;

import java.util.List;

public interface SessionLieuDateService {

    /***
     * Fonctionnalité qui permet de charger toutes les lieux et dates des sessions
     */
    List<SessionLieuDate> chargerToutesSessionsLieuDate();

    /**
     * Fonctionnalité qui permet de charger le lieu et la date d'une session par son id
     */
    SessionLieuDate chargerSessionLieuDateParId(long idSessionLieuDate);


    /**
     * Fonctionnalité qui permet d'ajouter à une session un lieu et une date
     * @param sessionLieuDate
     */
    SessionLieuDate ajouterSessionLieuDate(SessionLieuDate sessionLieuDate);

    /**
     * Fonctionnalité qui permet de modifier le lieu et/ou la date d'une session
     * @param sessionLieuDateInputDTO
     * @return
     */
    SessionLieuDate modifierSessionLieuDate(SessionLieuDateInputDTO sessionLieuDateInputDTO);

    /**
     * Fonctionnalité qui permet de supprimer un lieu et/ou une date d'une session
     */
    void supprimerSessionLieuDate(long idSessionLieuDate);
}
