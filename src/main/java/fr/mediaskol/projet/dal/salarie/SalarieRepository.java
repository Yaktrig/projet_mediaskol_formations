package fr.mediaskol.projet.dal.salarie;

import fr.mediaskol.projet.bo.salarie.Salarie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalarieRepository extends JpaRepository<Salarie, Long> {
}
