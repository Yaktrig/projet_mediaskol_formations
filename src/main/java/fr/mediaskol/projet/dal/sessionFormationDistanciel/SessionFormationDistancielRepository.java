package fr.mediaskol.projet.dal.sessionFormationDistanciel;

import fr.mediaskol.projet.bo.sessionFormationDistanciel.SessionFormationDistanciel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionFormationDistancielRepository extends JpaRepository<SessionFormationDistanciel, Long> {
}
