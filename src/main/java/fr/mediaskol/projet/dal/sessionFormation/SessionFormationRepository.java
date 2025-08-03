package fr.mediaskol.projet.dal.sessionFormation;


import fr.mediaskol.projet.bo.sessionFormation.SessionFormation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.Optional;

public interface SessionFormationRepository extends JpaRepository<SessionFormation, Long>, JpaSpecificationExecutor<SessionFormation> {

    /**
     * Méthode qui permet de retrouver le numéro Yoda d'une session de formation
     *
     * @param noYoda
     * @return
     */
    Optional<SessionFormation> findByNoYoda(String noYoda);


    /**
     * Requête qui permet de faire un filtrage libre sur les sessions de formation
     * parmi le numéro Yoda, le numéro du département, le libellé, la date de début de session, le statut de la session
     */
//        @Query("SELECT sf FROM SessionFormation sf " +
//            "LEFT JOIN sf.finSessionFormation fin " +
//            "WHERE LOWER(sf.noYoda) LIKE LOWER(CONCAT('%', :termeRecherche, '%')) OR " +
//            "LOWER(CAST(sf.departement.numDepartement AS string)) LIKE LOWER(CONCAT('%', :termeRecherche, '%')) OR " +
//            "LOWER(sf.libelleSessionFormation) LIKE LOWER(CONCAT('%', :termeRecherche, '%')) OR " +
//            "STR(sf.dateDebutSession) LIKE LOWER(CONCAT('%', :termeRecherche, '%')) OR " +
//            "LOWER(CAST(sf.statutSessionFormation AS string)) LIKE LOWER(CONCAT('%', :termeRecherche, '%')) OR " +
//            "LOWER(fin.statutYodaFinSessionFormation) LIKE LOWER(CONCAT('%', :termeRecherche, '%'))")
//    List<SessionFormation> findSessionFormationsBySearchText(@Param("termeRecherche") String termeRecherche);


//            "LOWER(sf.departement.numDepartement) LIKE LOWER(CONCAT('%', :termeRecherche, '%'))")
//            "LOWER(sf.libelleSessionFormation) LIKE LOWER(CONCAT('%', :termeRecherche, '%')) OR " +
//            "LOWER(CAST(sf.dateDebutSession AS string)) LIKE LOWER(CONCAT('%', :termeRecherche, '%')) OR " +
//            "LOWER(sf.statutSessionFormation) LIKE LOWER(CONCAT('%', :termeRecherche, '%')) OR " +
//            "LOWER(sf.finSessionFormation.statutFinSessionFormation) LIKE LOWER(CONCAT('%', :termeRecherche, '%'))" +

}
