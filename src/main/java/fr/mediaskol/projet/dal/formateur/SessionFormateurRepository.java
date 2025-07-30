package fr.mediaskol.projet.dal.formateur;

import fr.mediaskol.projet.bo.formateur.SessionFormateur;
import fr.mediaskol.projet.bo.salle.SessionSalle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessionFormateurRepository extends JpaRepository<SessionFormateur, Long> {

    /**
     * Requête qui récupère les sessions formateurs par ID de session formation présentiel
     */
    List<SessionFormateur> findBySessionFormationIdSessionFormation(Long idSessionFormation);
}
