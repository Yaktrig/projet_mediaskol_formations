package fr.mediaskol.projet.bll.adresse;

import fr.mediaskol.projet.bo.adresse.Adresse;

public interface AdresseService {

    /**
     * Validation de l'adresse
     * @param adresse
     * @return
     */
    void validerAdresse(Adresse adresse );


    /**
     *     Méthode qui permet d'ajouter le numéro du département en fonction du code postal
      */
    String getNumDepartement(String codePostal);
}
