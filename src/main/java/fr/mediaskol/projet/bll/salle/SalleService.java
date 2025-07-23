package fr.mediaskol.projet.bll.salle;

import fr.mediaskol.projet.bo.adresse.Adresse;
import fr.mediaskol.projet.bo.salle.Salle;
import fr.mediaskol.projet.dto.salle.SalleInputDTO;

import java.util.List;

public interface SalleService {

    /**
     * Fonctionnalité qui permet de charger toutes les salles
     * @return
     */
    List<Salle> chargerToutesSalles();

    /**
     * Fonctionnalité qui permet de charger une salle par son id
     */
    Salle chargerSalleParId(long idSalle);

    /**
     * Fonctionnalité qui retourne une ou des salles selon des critères
     *
     * @param termeRecherche
     */
    List<Salle> rechercheSalle(String termeRecherche);


    /**
     * Fonctionnalité qui va ajouter une salle
     * @param salle
     * @param adresse
     */
    void ajouterSalle(Salle salle, Adresse adresse);

    /**
     * Fonctionnalité qui va modifier une salle
     * @param dto
     * @return
     */
    Salle modifierSalle(SalleInputDTO dto);


    /**
     * Fonctionnalité qui supprimer une salle
     * @param idSalle
     */
    void supprimerSalle(long idSalle);
}
