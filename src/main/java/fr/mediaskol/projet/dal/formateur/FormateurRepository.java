package fr.mediaskol.projet.dal.formateur;

import fr.mediaskol.projet.bo.formateur.Formateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FormateurRepository extends JpaRepository<Formateur, Long> {

    /**
     * Requête qui permet de faire un filtrage libre sur les formateurs
     * parmi le nom, l'email, le numéro du portable, le numéro du département, la ville
     */
    @Query("SELECT f FROM Formateur f WHERE " +
            "LOWER(f.nom) LIKE LOWER(CONCAT('%', :termeRecherche, '%')) OR " +
            "LOWER(f.email) LIKE LOWER(CONCAT('%', :termeRecherche, '%')) OR " +
            "LOWER(f.numPortable) LIKE LOWER(CONCAT('%', :termeRecherche, '%')) OR " +
            "LOWER(f.adresse.departement.numDepartement) LIKE LOWER(CONCAT('%', :termeRecherche, '%')) ")
    List<Formateur> findFormateursBySearchText(@Param("termeRecherche") String termeRecherche);
}
