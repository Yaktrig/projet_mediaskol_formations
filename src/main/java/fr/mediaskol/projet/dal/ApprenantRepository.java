package fr.mediaskol.projet.dal;

import fr.mediaskol.projet.bo.apprenant.Apprenant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprenantRepository  extends JpaRepository<Apprenant, Long> {
}
