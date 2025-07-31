package fr.mediaskol.projet.bll.departement;

import fr.mediaskol.projet.bo.departement.Departement;
import fr.mediaskol.projet.dal.departement.DepartementRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class DepartementServiceImpl implements DepartementService{

    /**
     * Injection des repository en couplage faible
     */
    private final DepartementRepository departementRepository;



    /**
     * Fonctionnalité qui retourne les départements de la région Bretagne
     *
     * @param nomRegion
     */
    @Override
    public List<Departement> chargerDepartementDeBretagne(String nomRegion) {
        return departementRepository.findByRegion(nomRegion);
    }
}
