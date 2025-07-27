package fr.mediaskol.projet.dal.salarie;

import fr.mediaskol.projet.bo.salarie.Salarie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SalarieRepository extends JpaRepository<Salarie, Long> {

    /**
     * Rechercher un salarié par son email
     * @param mail
     * @return
     */
   Salarie findByEmail(@Param("mail") String mail);


}
