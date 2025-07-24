package fr.mediaskol.projet.bll.formateur;

import fr.mediaskol.projet.bo.formateur.SessionFormateur;
import fr.mediaskol.projet.dto.formateur.SessionFormateurInputDTO;

import java.util.List;

public interface SessionFormateurService {

    /***
     * Fonctionnalité qui permet de charger toutes les sessions des formateurs
     */
    List<SessionFormateur> chargerToutesSessionsFormateur();

    /**
     * Fonctionnalité qui permet de charger une sessionFormateur par son id
     */
    SessionFormateur chargerSessionFormateurParId(long idSessionFormateur);




    /**
     * Fonctionnalité qui permet d'ajouter une session d'un formateur
     * @param sessionFormateur
     */
    SessionFormateur ajouterSessionFormateur(SessionFormateur sessionFormateur);

    /**
     * Fonctionnalité qui permet de modifier une sessionFormateur
     * @param sessionFormateurInputDTO
     * @return
     */
    SessionFormateur modifierSessionFormateur(SessionFormateurInputDTO sessionFormateurInputDTO);

    /**
     * Fonctionnalité qui permet de supprimer une session d'un formateur
     */
    void supprimerSessionFormateur(long idSessionFormateur);




}
