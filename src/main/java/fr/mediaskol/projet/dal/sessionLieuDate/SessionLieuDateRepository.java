package fr.mediaskol.projet.dal.sessionLieuDate;

import fr.mediaskol.projet.bo.sessionLieuDate.SessionLieuDate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionLieuDateRepository extends JpaRepository<SessionLieuDate, Long> {
}
