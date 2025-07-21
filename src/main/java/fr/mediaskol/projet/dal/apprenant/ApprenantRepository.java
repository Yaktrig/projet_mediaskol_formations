package fr.mediaskol.projet.dal.apprenant;

import fr.mediaskol.projet.bo.apprenant.Apprenant;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;


public interface ApprenantRepository  extends JpaRepository<Apprenant, Long>, JpaSpecificationExecutor<Apprenant> {



    /**
     * Méthode qui permet de retrouver le numéro de passeport d'un apprenant.
     * @param numPasseport
     * @return
     */
    Optional<Apprenant> findByNumPasseport(@Size(max = 120, message = "{apprenant.numPasseport.size}") String numPasseport);
}
