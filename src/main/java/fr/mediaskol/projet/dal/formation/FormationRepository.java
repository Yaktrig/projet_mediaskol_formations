package fr.mediaskol.projet.dal.formation;

import fr.mediaskol.projet.bo.formation.Formation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FormationRepository extends JpaRepository<Formation, Long> {

    /**
     * Méthode qui permet de retrouver le thème d'une formation.
     *
     * @param themeFormation
     * @param idTypeFormation
     */
    Optional<Formation> findByThemeFormationAndTypeFormation_IdTypeFormation(String themeFormation, Long idTypeFormation);




}
