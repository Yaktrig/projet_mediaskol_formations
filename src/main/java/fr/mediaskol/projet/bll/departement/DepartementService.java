package fr.mediaskol.projet.bll.departement;

import fr.mediaskol.projet.bo.departement.Departement;

import java.util.List;

public interface DepartementService {

    /**
     * Fonctionnalité qui retourne les départements de la région Bretagne
     */
    List<Departement> chargerDepartementDeBretagne(String nomRegion);
}
