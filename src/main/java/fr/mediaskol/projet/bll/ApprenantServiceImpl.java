package fr.mediaskol.projet.bll;

import fr.mediaskol.projet.bo.apprenant.Apprenant;
import fr.mediaskol.projet.dal.apprenant.ApprenantRepository;
import fr.mediaskol.projet.dal.apprenant.ApprenantSpecifications;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Service
public class ApprenantServiceImpl implements  ApprenantService {

    // Injection des repository en couplage faible
    ApprenantRepository apprenantRepository;

    /**
     * Appel de la méthode findAll() du repository qui retourne tous les apprenants
     * @return
     */
    @Override
    public List<Apprenant> chargerTousApprenants() {

        return apprenantRepository.findAll();

    }

    /**
     * @param nom
     * @param email
     * @param dateNaissance
     * @param numDepartement
     * @param ville
     * @return
     */
    @Override
    public List<Apprenant> rechercheApprenants(String nom, String email, LocalDate dateNaissance, Long numDepartement, String ville) {

        Specification<Apprenant> specApprenant = ApprenantSpecifications
                .nomContains(nom)
                .and(ApprenantSpecifications.emailContains(email))
                .and(ApprenantSpecifications.dateNaissanceContains(dateNaissance))
                .and(ApprenantSpecifications.numDepartement(numDepartement))
                .and(ApprenantSpecifications.villeContains(ville));

        return apprenantRepository.findAll(specApprenant);
    }


    // Méthodes de contrôle de contraintes

    // Validation si les chaines de caractères ne sont pas nulles ou vides
    private void validerChaineNonNulle(String chaine, String msgErreur) {
        if (chaine == null || chaine.isBlank()) {

            // on déclenche une exception
            throw new RuntimeException(msgErreur);
        }
    }
}
