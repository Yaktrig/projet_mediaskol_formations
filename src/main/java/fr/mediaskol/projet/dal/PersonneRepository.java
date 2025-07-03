package fr.mediaskol.projet.dal;

import fr.mediaskol.projet.bo.Personne;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonneRepository extends JpaRepository<Personne, Long> {
}
