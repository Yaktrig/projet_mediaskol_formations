package fr.mediaskol.projet.bll.salle;

import fr.mediaskol.projet.bo.salle.SessionSalle;
import fr.mediaskol.projet.dto.salle.SessionSalleInputDTO;

import java.util.List;

public interface SessionSalleService {
    /***
     * Fonctionnalité qui permet de charger toutes les sessions des salles
     */
    List<SessionSalle> chargerToutesSessionsSalle();

    /**
     * Fonctionnalité qui permet de charger une sessionSalle par son id
     */
    SessionSalle chargerSessionSalleParId(long idSessionSalle);


    /**
     * Fonctionnalité qui permet d'ajouter une session d'une salle
     * @param sessionSalle
     */
    SessionSalle ajouterSessionSalle(SessionSalle sessionSalle);

    /**
     * Fonctionnalité qui permet de modifier une sessionSalle
     * @param sessionSalleInputDTO
     * @return
     */
    SessionSalle modifierSessionSalle(SessionSalleInputDTO sessionSalleInputDTO);

    /**
     * Fonctionnalité qui permet de supprimer une session d'une salle
     */
    void supprimerSessionSalle(long idSessionSalle);
}
