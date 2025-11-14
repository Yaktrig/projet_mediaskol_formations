package fr.mediaskol.projet.dal.sessionFormation;

import fr.mediaskol.projet.bo.sessionFormation.SessionFormation;
import fr.mediaskol.projet.bo.sessionFormationDistanciel.SessionFormationDistanciel;
import fr.mediaskol.projet.bo.sessionFormation.SessionFormationPresentiel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SessionFOADRepository extends JpaRepository<SessionFormationDistanciel, Long> {

    /**
     * todo modifier la requête pour adapter au FOAD
     * Requête qui permet de faire un filtrage libre sur les sessions de formation
     * parmi le numéro Yoda, le numéro du département, le libellé, la date de début de session, le statut de la session
     */
//    @Query("SELECT sf FROM SessionFormationPresentiel sf  " +
//            "LEFT JOIN sf.departement dep " +
//            "WHERE LOWER(sf.noYoda) LIKE LOWER(CONCAT('%', :termeRecherche, '%')) OR " +
//            "LOWER(sf.libelleSessionFormation) LIKE LOWER(CONCAT('%', :termeRecherche, '%')) OR " +
//            "LOWER(dep.numDepartement) LIKE LOWER(CONCAT('%', :termeRecherche, '%'))")
//    List<SessionFormationPresentiel> findSessionFormationsBySearchText(@Param("termeRecherche") String termeRecherche);

//    /**
//     * Requête qui permet de retourner une session de formation en distanciel qui commence à la date saisie
//     *
//     * @param dateDebutSession
//     * @return
//     */
//    List<SessionFormationDistanciel> findByDateDebutSession(LocalDate dateDebutSession);


//    /**
//     * Requête qui retourne les sessions de formation en présentiel compris entre deux dates (début et fin)
//     *
//     * @param dateDebutSession
//     * @param dateFinSession
//     */
//    List<SessionFormationDistanciel> findbydateDebutSessionBetween(LocalDate dateDebutSession, LocalDate dateFinSession);
//
//
//    /**
//     * Requête qui retourne les sessions de formation en présentiel compris entre deux dates (début et fin)
//     *
//     * @param dateDebutSession
//     * @param dateFinSession
//     */
//    List<SessionFormationDistanciel> findbydateFinSessionBetween(LocalDate dateDebutSession, LocalDate dateFinSession);
}
