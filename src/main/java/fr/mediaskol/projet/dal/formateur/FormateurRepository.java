package fr.mediaskol.projet.dal.formateur;

import fr.mediaskol.projet.bo.formateur.Formateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FormateurRepository extends JpaRepository<Formateur, Long> {

    /**
     * Requête qui permet de faire un filtrage libre sur les formateurs
     * parmi le nom, l'email, le numéro du portable, le numéro du département, la ville, le thème de la formation
     * dispensé, le libellé de la formation dispensée, le type de formation dispensé
     */
    @Query("SELECT DISTINCT f FROM Formateur f " +
            "LEFT JOIN f.formationsDispensees fd " +
            "LEFT JOIN f.typesFormationDispensees tf " +
            "WHERE LOWER(f.nom) LIKE LOWER(CONCAT('%', :termeRecherche, '%')) OR " +
            "LOWER(f.email) LIKE LOWER(CONCAT('%', :termeRecherche, '%')) OR " +
            "LOWER(f.numPortable) LIKE LOWER(CONCAT('%', :termeRecherche, '%')) OR " +
            "LOWER(f.adresse.departement.numDepartement) LIKE LOWER(CONCAT('%', :termeRecherche, '%')) OR " +
            "LOWER(f.adresse.ville) LIKE LOWER(CONCAT('%', :termeRecherche, '%')) OR " +
            "LOWER(fd.themeFormation) LIKE LOWER(CONCAT('%', :termeRecherche, '%')) OR " +
            "LOWER(fd.libelleFormation) LIKE LOWER(CONCAT('%', :termeRecherche, '%')) OR " +
            "LOWER(tf.libelleTypeFormation) LIKE LOWER(CONCAT('%', :termeRecherche, '%'))" )
    List<Formateur> findFormateursBySearchText(@Param("termeRecherche") String termeRecherche);
}
