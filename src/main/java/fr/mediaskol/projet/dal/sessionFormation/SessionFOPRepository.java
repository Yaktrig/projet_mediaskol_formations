package fr.mediaskol.projet.dal.sessionFormation;

import fr.mediaskol.projet.bo.sessionFormation.SessionFormationPresentiel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionFOPRepository extends JpaRepository<SessionFormationPresentiel, Long> {
}
