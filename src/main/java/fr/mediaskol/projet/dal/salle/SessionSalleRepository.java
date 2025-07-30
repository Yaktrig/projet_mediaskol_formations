package fr.mediaskol.projet.dal.salle;

import fr.mediaskol.projet.bo.salle.SessionSalle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessionSalleRepository extends JpaRepository<SessionSalle, Long> {

    /**
     * Requête qui récupère les sessions salle par ID de session formation présentiel
     */
    List<SessionSalle> findBySessionFormationPresentielIdSessionFormation(Long idSessionFormationPresentiel);
}
