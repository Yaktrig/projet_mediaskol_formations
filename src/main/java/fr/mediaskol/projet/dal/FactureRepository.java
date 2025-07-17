package fr.mediaskol.projet.dal;

import fr.mediaskol.projet.bo.facture.Facture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FactureRepository extends JpaRepository<Facture, Long> {
}
