package fr.mediaskol.projet.bll;

public interface AdresseService {

    // Méthode qui permet d'ajouter le numéro du département en fonction du code postal
    String getNumDepartement(String codePostal);
}
