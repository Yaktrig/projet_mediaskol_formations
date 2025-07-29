package fr.mediaskol.projet.dal.sessionFormation;

import fr.mediaskol.projet.bo.sessionFormation.SessionFormation;
import fr.mediaskol.projet.bo.sessionFormation.SessionFormationPresentiel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SessionFOPRepository extends JpaRepository<SessionFormationPresentiel, Long> {

    /**
     * Méthode qui permet de retrouver le numéro Yoda d'une session de formation
     *
     * @param noYoda
     * @return
     */
    Optional<SessionFormationPresentiel> findByNoYoda(String noYoda);


    /**
     * Requête qui permet de faire un filtrage libre sur les sessions de formation
     * parmi le numéro Yoda, le numéro du département, le libellé, la date de début de session, le statut de la session
     */
    @Query("SELECT sf FROM SessionFormationPresentiel sf  " +
            "LEFT JOIN sf.departement dep " +
            "WHERE LOWER(sf.noYoda) LIKE LOWER(CONCAT('%', :termeRecherche, '%')) OR " +
            "LOWER(sf.libelleSessionFormation) LIKE LOWER(CONCAT('%', :termeRecherche, '%')) OR " +
            "LOWER(dep.numDepartement) LIKE LOWER(CONCAT('%', :termeRecherche, '%'))")
    List<SessionFormationPresentiel> findSessionFormationsBySearchText(@Param("termeRecherche") String termeRecherche);

    /**
     * Requête qui permet de retourner une date de début de session
     *
     * @param dateDebutSession
     * @return
     */
    List<SessionFormationPresentiel> findByDateDebutSession(LocalDate dateDebutSession);



}
