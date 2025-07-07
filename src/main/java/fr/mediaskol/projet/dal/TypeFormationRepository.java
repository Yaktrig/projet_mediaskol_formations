package fr.mediaskol.projet.dal;

import fr.mediaskol.projet.bo.formation.TypeFormation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeFormationRepository extends JpaRepository<TypeFormation, Long> {
}
