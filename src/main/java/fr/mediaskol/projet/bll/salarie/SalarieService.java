package fr.mediaskol.projet.bll.salarie;

import fr.mediaskol.projet.bo.formation.Formation;
import fr.mediaskol.projet.bo.salarie.Salarie;
import fr.mediaskol.projet.dto.formation.FormationInputDTO;

import java.util.List;
import java.util.Optional;

public interface SalarieService {

    /**
     * Fonctionnalité qui permet d'ajouter un salarié
     *
     * @param salarie
     */
    void ajouterSalarie(Salarie salarie);

    /**
     * Fonctionnalité qui va retourner un salarié par rapport à son identifiant
     *
     * @param idSalarie
     * @return
     */
    Salarie chargerUnSalarieParId(long idSalarie);

    /**
     * Fonctionnalité qui va retourner la liste des salariés
     */
    List<Salarie> chargerTousSalaries();


    /**
     * Fonctionnalité qui permet de supprimer un salarié
     * @param idSalarie
     */
    void supprimerSalarie(long idSalarie);


    /**
     * Fonctionnalité qui permet de modifier un salarie
     *
     * @param salarie
     * @return
     */
    Salarie modifierSalarie(Salarie salarie);

}
