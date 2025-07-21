package fr.mediaskol.projet.dal.salle;

import fr.mediaskol.projet.bo.salle.Salle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalleRepository extends JpaRepository<Salle, Long> {
}
