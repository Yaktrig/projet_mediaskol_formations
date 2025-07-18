package fr.mediaskol.projet.dal.adresse;

import fr.mediaskol.projet.bo.adresse.Adresse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdresseRepository extends JpaRepository<Adresse, Long> {


}
