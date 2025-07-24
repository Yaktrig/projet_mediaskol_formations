package fr.mediaskol.projet.dal.apprenant;

import fr.mediaskol.projet.bo.apprenant.Apprenant;
import fr.mediaskol.projet.bo.apprenant.SessionApprenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessionApprenantRepository extends JpaRepository<SessionApprenant, Long> {


}
