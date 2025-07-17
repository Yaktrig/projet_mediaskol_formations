package fr.mediaskol.projet.bll;

import fr.mediaskol.projet.bo.apprenant.Apprenant;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.List;

public interface ApprenantService {

    // Fonctionnalité qui permet de charger tous les apprenants
    List<Apprenant> chargerTousApprenants();

    /**
     * Fonctionnalités qui retourne un ou des apprenants selon des critères de recherches
     * @param nom
     * @param email
     * @param dateNaissance
     * @param numDepartement
     * @param ville
     */
    List<Apprenant> rechercheApprenants(String nom, String email, LocalDate dateNaissance, Long numDepartement, String ville);


}
