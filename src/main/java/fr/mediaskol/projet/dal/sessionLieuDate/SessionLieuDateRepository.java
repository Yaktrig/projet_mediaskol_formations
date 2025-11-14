package fr.mediaskol.projet.dal.sessionLieuDate;

import fr.mediaskol.projet.bo.salle.SessionSalle;
import fr.mediaskol.projet.bo.sessionLieuDate.SessionLieuDate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessionLieuDateRepository extends JpaRepository<SessionLieuDate, Long> {

    /**
     * Requête qui récupère les sessions salle par ID de session formation présentiel
     */
    List<SessionLieuDate> findBySessionFormationIdSessionFormation(Long idSessionFormation);
}
