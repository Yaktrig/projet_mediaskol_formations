package fr.mediaskol.projet.dal.sessionFormation;


import fr.mediaskol.projet.bo.sessionFormation.SessionFormation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionFormationRepository extends JpaRepository<SessionFormation, Long> {

    /**
     * Méthode qui permet de retrouver le numéro Yoda d'une session de formation
     *
     * @param noYoda
     * @return
     */
    Optional<SessionFormation> findByNoYoda(String noYoda);

}
