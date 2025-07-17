package fr.mediaskol.projet.dal.departement;

import fr.mediaskol.projet.bo.departement.Departement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartementRepository extends JpaRepository<Departement, Long> {
}
