package fr.mediaskol.projet.repository;

import fr.mediaskol.projet.entities.SessionFormation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface SessionFormationRepository extends JpaRepository<SessionFormation, Long> {
    List<SessionFormation> findByDateDebutSession(LocalDate dateDebutSession);

    List<SessionFormation> findSessionFormationsBySearchText(@Param("text") String text);
}