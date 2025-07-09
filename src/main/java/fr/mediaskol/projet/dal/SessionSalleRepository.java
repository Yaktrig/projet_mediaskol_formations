package fr.mediaskol.projet.dal;

import fr.mediaskol.projet.bo.salle.SessionSalle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionSalleRepository extends JpaRepository<SessionSalle, Long> {
}
