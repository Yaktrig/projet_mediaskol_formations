package fr.mediaskol.projet.dal.sessionDate;

import fr.mediaskol.projet.bo.sessionDate.SessionDate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessionDateRepository extends JpaRepository<SessionDate, Long> {

    /**
     * Requête qui récupère les sessions salle par ID de session formation présentiel
     */
    List<SessionDate> findBySessionFormationIdSessionFormation(Long idSessionFormation);
}
