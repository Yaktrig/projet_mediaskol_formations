package fr.mediaskol.projet.bll.sessionFormation;

import fr.mediaskol.projet.bo.sessionFormation.SessionFormationPresentiel;
import fr.mediaskol.projet.dto.formateur.SessionFormateurRespDTO;
import fr.mediaskol.projet.dto.salle.SessionSalleRespDTO;
import fr.mediaskol.projet.dto.sessionFormation.SessionFOPInputDTO;
import fr.mediaskol.projet.dto.sessionFormation.SessionFOPResponseDTO;
import fr.mediaskol.projet.dto.sessionLieuDate.SessionLieuDateRespDTO;

import java.util.List;

public interface SessionFOPService {

    /***
     * Fonctionnalité qui permet de charger toutes les Sessions de formations en présentiel
     */
    List<SessionFormationPresentiel> chargerToutesSessionsFop();

    /**
     * Todo permettre la recherche d'une session de formation par son thème, département
     */
    /**
     * Fonctionnalité qui retourne une ou des sessions de formation en présentiel selon des critères de recherches
     *
     * @param termeRecherche
     */
    List<SessionFormationPresentiel> rechercheSessionFop(String termeRecherche);



    /**
     * Fonctionnalité qui permet de charger une session de formation en présentiel par son id
     */
    SessionFormationPresentiel chargerSessionFopParId(long idSessionFop);


    /**
     * Fonctionnalité qui permet d'ajouter une session de formation en présentiel
     * @param sessionFop
     */
    SessionFormationPresentiel ajouterSessionFop (SessionFormationPresentiel sessionFop);

    /**
     * Fonctionnalité qui permet de supprimer une session de formation en présentiel
     */
    void supprimerSessionFop(long idSessionFormation);


    /**
     * Fonctionnalité qui permet de modifier une session de formation
     * @param sessionFopInputDTO
     * @return
     */
    SessionFormationPresentiel modifierSessionFop(SessionFOPInputDTO sessionFopInputDTO);

    /**
     * Fonctionnalité qui récupère les sessions salle liées à une session formation en présentiel.
     * Les convertit en DTO
     */
    List<SessionSalleRespDTO> getSessionsSalleBySessionId(Long idSessionFormationPresentiel);

    /**
     * Fonctionnalité qui récupère les sessions formateurs liées à une session formation en présentiel.
     * Les convertit en DTO
     */
    List<SessionFormateurRespDTO> getSessionsFormateurBySessionId(Long idSessionFormationPresentiel);

    /**
     * Fonctionnalité qui récupère les sessions lieu date liées à une session formation en présentiel.
     * Les convertit en DTO
     */
    List<SessionLieuDateRespDTO> getSessionsLieuDateBySessionId(Long idSessionFormation);

    /**
     * Fonctionnalité qui récupère les sessions de formation qui ont moins de 6 sessions d'apprenants d'inscrits
     */
    List<SessionFormationPresentiel> getSessionFormationsAvecMoinsDe6Apprenants();


}
