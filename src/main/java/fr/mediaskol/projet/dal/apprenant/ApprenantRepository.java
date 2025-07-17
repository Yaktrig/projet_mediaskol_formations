package fr.mediaskol.projet.dal.apprenant;

import fr.mediaskol.projet.bo.apprenant.Apprenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;



public interface ApprenantRepository  extends JpaRepository<Apprenant, Long>, JpaSpecificationExecutor<Apprenant> {



}
