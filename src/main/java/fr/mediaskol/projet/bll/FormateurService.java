package fr.mediaskol.projet.bll;

import fr.mediaskol.projet.bo.adresse.Adresse;
import fr.mediaskol.projet.bo.formateur.Formateur;
import fr.mediaskol.projet.bo.formation.TypeFormation;
import fr.mediaskol.projet.dto.FormateurInputDTO;

import java.util.List;
import java.util.Set;

public interface FormateurService {

    /**
     * Fonctionnalité qui permet de charger tous les formateurs
     */
    List<Formateur> chargerTousFormateurs();

    /**
     * Fonctionnalité qui retourne un ou des formateurs selon des critères
     *
     * @param termeRecherche
     */
    List<Formateur> rechercheFormateur(String termeRecherche);


    /**
     * Fonctionnalité qui va ajouter un formateur
     * @param formateur
     * @param adresse
     * @param typesFormationDispensees
     */
    void ajouterFormateur(Formateur formateur, Adresse adresse, Set<TypeFormation> typesFormationDispensees);

    /**
     * Fonctionnalité qui va modifier un formateur
     * @param dto
     * @return
     */
    Formateur modifierFormateur(FormateurInputDTO dto);


    /**
     * Fonctionnalité qui supprimer un formateur
     * @param idFormateur
     */
    void supprimerFormateur(long idFormateur);
}
