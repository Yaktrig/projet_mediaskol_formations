package fr.mediaskol.projet.dal;

import fr.mediaskol.projet.bo.apprenant.SessionApprenant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionApprenantRepository extends JpaRepository<SessionApprenant, Long> {
}
