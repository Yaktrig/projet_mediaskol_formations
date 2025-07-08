package fr.mediaskol.projet.dal;

import fr.mediaskol.projet.bo.sessionFormation.SessionFormationPresentiel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionFormationPresentielRepository extends JpaRepository<SessionFormationPresentiel, Long> {
}
