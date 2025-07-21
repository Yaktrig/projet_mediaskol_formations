package fr.mediaskol.projet.dal.formateur;

import fr.mediaskol.projet.bo.formateur.Formateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormateurRepository extends JpaRepository<Formateur, Long> {
}
