package fr.mediaskol.projet.bll.formation;

import fr.mediaskol.projet.bo.formation.Formation;
import fr.mediaskol.projet.bo.salle.Salle;
import fr.mediaskol.projet.dto.formation.FormationInputDTO;
import fr.mediaskol.projet.dto.formation.FormationUpdateDTO;

import java.util.List;

public interface FormationService {

    /***
     * Fonctionnalité qui permet de charger toutes les formations
     */
    List<Formation> chargerToutesFormations();

    /**
     * Fonctionnalité qui permet de charger une formation par son id
     */
    Formation chargerFormationParId(long idFormation);


    /**
     * Fonctionnalité qui permet d'ajouter une formation
     * @param formation
     */
    Formation ajouterFormation(Formation formation);

    /**
     * Fonctionnalité qui permet de supprimer une formation
     */
    void supprimerFormation(long idFormation);



    /**
     * Fonctionnalité qui permet de modifier une formation
     * @param formationUpdateDTO
     * @return
     */
    Formation modifierFormation(FormationUpdateDTO formationUpdateDTO);
}
