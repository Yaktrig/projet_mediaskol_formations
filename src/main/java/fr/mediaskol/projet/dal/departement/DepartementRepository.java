package fr.mediaskol.projet.dal.departement;

import fr.mediaskol.projet.bo.departement.Departement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartementRepository extends JpaRepository<Departement, Long> {

    /**
     * Recherche un département par son numéro.
     *
     * @param numDepartement le numéro du département (ex: "75", "2A", "971")
     * @return un Optional contenant le département s'il existe, vide sinon
     */
    Optional<Departement> findByNumDepartement(String numDepartement);
}
