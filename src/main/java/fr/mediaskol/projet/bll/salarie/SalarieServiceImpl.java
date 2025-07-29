package fr.mediaskol.projet.bll.salarie;

import fr.mediaskol.projet.bo.apprenant.Apprenant;
import fr.mediaskol.projet.bo.salarie.Salarie;
import fr.mediaskol.projet.dal.salarie.SalarieRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class SalarieServiceImpl implements SalarieService {

    /**
     * Injection des repository en couplage faible.
     */
    private final SalarieRepository salarieRepository;


    /**
     * Fonctionnalité qui permet d'ajouter un salarié
     * @param salarie
     */
    @Override
    @Transactional
    public void ajouterSalarie(Salarie salarie) {

        if (salarie == null) {
            throw new RuntimeException("Le salarié n'est pas renseigné.");
        }

        // Validation du nom, prenom, mail
        validerChaineNonNulle(salarie.getNom(), "Vous devez renseigner un nom");
        validerChaineNonNulle(salarie.getPrenom(), "Vous devez renseigner un prénom");
        validerChaineNonNulle(salarie.getEmail(), "L'adresse mail est obligatoire.");
        validerEmail(salarie.getEmail(), "L'adresse mail doit correspondre au format email");

        salarieRepository.save(salarie);
    }

    /**
     * Fonctionnalité qui permet de charger un salarié par son id
     * @param idSalarie
     */
    @Override
    public Salarie chargerUnSalarieParId(long idSalarie) {

        // Validation de l'identifiant
        if (idSalarie <= 0) {
            throw new RuntimeException("L'identifiant n'existe pas");
        }

        // On retourne un salarié
        final Optional<Salarie> opt = salarieRepository.findById(idSalarie);
        if (opt.isPresent()) {
            return opt.get();
        }

        // Identifiant correspond à aucun enregistrement en base
        throw new RuntimeException("Aucun salarié ne correspond");
    }


    /**
     * Fonctionnalité qui permet de retourner tous les salariés
     * @return
     */
    @Override
    public List<Salarie> chargerTousSalaries() {
        return salarieRepository.findAll();
    }

    /**
     * Fonctionnalité qui permet de supprimer un salarié
     *
     * @param idSalarie
     */
    @Override
    public void supprimerSalarie(long idSalarie) {

        if (idSalarie <= 0) {
            throw new IllegalArgumentException("L'identifiant du salarié n'existe pas.");
        }

        if (!salarieRepository.existsById(idSalarie)) {
            throw new EntityNotFoundException("Le salarié avec l'ID " + idSalarie + " n'existe pas.");
        }

        try {
            salarieRepository.deleteById(idSalarie);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Impossible de supprimer le salarié.");
        } catch (Exception e) {
            throw new RuntimeException("Impossible de supprimer le salarié (id = " + idSalarie + ")" + e.getMessage());
        }
    }

    /**
     * Fonctionnalité qui permet de modifier un salarie
     *
     * @param salarie
     * @return
     */
    @Override
    @Transactional
    public Salarie modifierSalarie(Salarie salarie) {

        // 1. Vérifier que le salarié à modifier existe
        Salarie salarieDB = salarieRepository.findById(salarie.getIdPersonne())
                .orElseThrow(() -> new EntityNotFoundException("Salarié introuvable (id= " + salarie.getIdPersonne() + ")"));


        // 2. Valider les champs (chaînes, email, unicité...)
        validerChaineNonNulle(salarieDB.getNom(), "Le nom est obligatoire.");
        validerChaineNonNulle(salarieDB.getPrenom(), "Le prenom est obligatoire.");
        validerChaineNonNulle(salarieDB.getEmail(), "Le email est obligatoire.");
        validerEmail(salarieDB.getEmail(), "L'adresse mail doit correspondre au format email.");

        // 3. Sauvegarde finale
        return salarieRepository.save(salarie);
    }


    // Méthodes de contrôle de contraintes

    /**
     * Validation des chaînes de caractères, si non nulles ou non vides
     */
    private void validerChaineNonNulle(String chaine, String msgErreur) {
        if (chaine == null || chaine.isBlank()) {
            // on déclenche une exception
            throw new RuntimeException(msgErreur);
        }
    }

    /**
     * Validation si l'email n'est pas nul et s'il respecte le format attendu
     */
    private void validerEmail(String email, String msgErreur) {

        String regexEmail = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        if (email == null || !email.matches(regexEmail)) {
            throw new RuntimeException(msgErreur);
        }
    }

}
