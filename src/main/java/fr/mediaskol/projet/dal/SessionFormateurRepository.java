package fr.mediaskol.projet.dal;

import fr.mediaskol.projet.bo.formateur.SessionFormateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionFormateurRepository extends JpaRepository<SessionFormateur, Long> {
}
