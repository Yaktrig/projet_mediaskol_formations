package fr.mediaskol.projet.dal.departement;

import fr.mediaskol.projet.bo.departement.Departement;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DepartementRepository extends JpaRepository<Departement, Long> {

    /**
     * Recherche un département par son numéro.
     *
     * @param numDepartement le numéro du département (ex: "75", "2A", "971")
     * @return un Optional contenant le département s'il existe, vide sinon
     */
    Optional<Departement> findByNumDepartement(@Param("numDepartement") String numDepartement);

    /**
     * Retourne les départements d'une région
     */
    List<Departement> findByRegion(String nomRegion);
}
