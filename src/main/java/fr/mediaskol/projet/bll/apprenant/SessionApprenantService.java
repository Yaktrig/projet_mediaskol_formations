package fr.mediaskol.projet.bll.apprenant;

import fr.mediaskol.projet.bo.apprenant.SessionApprenant;
import fr.mediaskol.projet.bo.formation.Formation;
import fr.mediaskol.projet.dto.apprenant.SessionApprenantInputDTO;
import org.hibernate.Session;


import java.util.List;

public interface SessionApprenantService {

    /***
     * Fonctionnalité qui permet de charger toutes les sessions des apprenants
     */
    List<SessionApprenant> chargerToutesSessionsApprenant();

    /**
     * Fonctionnalité qui permet de charger une sessionApprenant par son id
     */
    SessionApprenant chargerSessionApprenantParId(long idSessionApprenant);


    /**
     * Fonctionnalité qui permet d'ajouter une session d'un apprenant
     * @param sessionApprenant
     */
    SessionApprenant ajouterSessionApprenant(SessionApprenant sessionApprenant);

    /**
     * Fonctionnalité qui permet de supprimer une session d'un apprenant
     */
    void supprimerSessionApprenant(long idSessionApprenant);



    /**
     * Fonctionnalité qui permet de modifier une sessionApprenant
     * @param sessionApprenantInputDTO
     * @return
     */
    SessionApprenant modifierSessionApprenant(SessionApprenantInputDTO sessionApprenantInputDTO);
}
