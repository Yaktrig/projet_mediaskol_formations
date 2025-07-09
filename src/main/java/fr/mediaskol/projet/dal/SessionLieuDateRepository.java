package fr.mediaskol.projet.dal;

import fr.mediaskol.projet.bo.SessionLieuDate.SessionLieuDate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionLieuDateRepository extends JpaRepository<SessionLieuDate, Long> {
}
