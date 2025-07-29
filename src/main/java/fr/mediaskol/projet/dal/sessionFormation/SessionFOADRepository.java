package fr.mediaskol.projet.dal.sessionFormation;

import fr.mediaskol.projet.bo.sessionFormation.SessionFormationDistanciel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionFOADRepository extends JpaRepository<SessionFormationDistanciel, Long> {
}
