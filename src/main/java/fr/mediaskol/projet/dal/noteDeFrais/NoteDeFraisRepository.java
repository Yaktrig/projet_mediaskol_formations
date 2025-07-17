package fr.mediaskol.projet.dal.noteDeFrais;

import fr.mediaskol.projet.bo.noteDeFrais.NoteDeFrais;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteDeFraisRepository extends JpaRepository<NoteDeFrais, Long> {
}
