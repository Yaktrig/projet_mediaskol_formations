package fr.mediaskol.projet.dal;

import fr.mediaskol.projet.bo.NoteDeFrais.NoteDeFrais;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteDeFraisRepository extends JpaRepository<NoteDeFrais, Long> {
}
