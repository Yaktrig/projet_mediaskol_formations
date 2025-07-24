package fr.mediaskol.projet.dal.apprenant;

import fr.mediaskol.projet.bo.apprenant.Apprenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface ApprenantRepository extends JpaRepository<Apprenant, Long>, JpaSpecificationExecutor<Apprenant> {


    /**
     * Méthode qui permet de retrouver le numéro de passeport d'un apprenant.
     *
     * @param numPasseport
     * @return
     */
    Optional<Apprenant> findByNumPasseport(String numPasseport);

    /**
     * Requête qui permet de faire un filtrage libre sur les apprenants
     * parmi le nom, l'email, le numéro du portable, la date de naissance, le numéro du département
     */
    @Query("SELECT app FROM Apprenant app WHERE " +
            "LOWER(app.nom) LIKE LOWER(CONCAT('%', :termeRecherche, '%')) OR " +
            "LOWER(app.email) LIKE LOWER(CONCAT('%', :termeRecherche, '%')) OR " +
            "LOWER(app.numPortable) LIKE LOWER(CONCAT('%', :termeRecherche, '%')) OR " +
            "LOWER(CAST(app.dateNaissance AS string)) LIKE LOWER(CONCAT('%', :termeRecherche, '%')) OR " +
            "LOWER(app.adresse.departement.numDepartement) LIKE LOWER(CONCAT('%', :termeRecherche, '%')) ")
    List<Apprenant> findApprenantsBySearchText(@Param("termeRecherche") String termeRecherche);

    List<Apprenant> findByDateNaissance(LocalDate dateNaissance);
}
