package fr.mediaskol.projet.bll.apprenant;

import fr.mediaskol.projet.bo.apprenant.Apprenant;
import fr.mediaskol.projet.bo.apprenant.SessionApprenant;
import fr.mediaskol.projet.bo.formation.Formation;
import fr.mediaskol.projet.bo.formation.TypeFormation;
import fr.mediaskol.projet.dal.apprenant.ApprenantRepository;
import fr.mediaskol.projet.dal.apprenant.SessionApprenantRepository;
import fr.mediaskol.projet.dto.apprenant.SessionApprenantInputDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class SessionApprenantServiceImpl implements SessionApprenantService {

    /**
     * Injection des repository en couplage faible.
     */
    private final SessionApprenantRepository sessionApprenantRepository;


    /***
     * Fonctionnalité qui permet de charger toutes les sessions des apprenants
     */
    @Override
    public List<SessionApprenant> chargerToutesSessionsApprenant() {
        return sessionApprenantRepository.findAll();
    }

    /**
     * Fonctionnalité qui permet de charger une sessionApprenant par son id
     *
     * @param idSessionApprenant
     */
    @Override
    public SessionApprenant chargerSessionApprenantParId(long idSessionApprenant) {

        // Validation de l'identifiant
        if (idSessionApprenant <= 0) {
            throw new RuntimeException("L'identifiant n'existe pas");
        }

        // On retourne une session d'un apprenant
        final Optional<SessionApprenant> opt = sessionApprenantRepository.findById(idSessionApprenant);
        if (opt.isPresent()) {
            return opt.get();
        }

        // Identifiant correspond à aucun enregistrement en base
        throw new RuntimeException("Aucune session d'apprenant ne correspond");
    }

    /**
     * Fonctionnalité qui permet d'ajouter une session d'un apprenant
     *
     * @param sessionApprenant
     * @return
     */
    @Override
    @Transactional
    public SessionApprenant ajouterSessionApprenant(SessionApprenant sessionApprenant) {

        if (sessionApprenant == null) {
            throw new RuntimeException("La session de l'apprenant n'est pas renseignée.");
        }

        if (sessionApprenant.getCommentaireSessionApprenant() != null) {
            validerCommentaire(sessionApprenant);
        }
        validerModeReception(sessionApprenant);
        validerEnumNonNulle(sessionApprenant.getStatutSessionApprenant(), "Le statut ne peut pas être nul.");
        validerApprenant(sessionApprenant.getApprenant().getIdPersonne());
        validerSessionFormation(sessionApprenant.getSessionFormation().getIdSessionFormation());


        try {
            return sessionApprenantRepository.save(sessionApprenant);
        } catch (RuntimeException e) {
            throw new RuntimeException("Impossible de sauvegarder - " + sessionApprenant.toString());
        }
    }


    /**
     * Fonctionnalité qui permet de modifier une sessionApprenant
     *
     * @param dto
     * @return
     */
    @Override
    public SessionApprenant modifierSessionApprenant(SessionApprenantInputDTO dto) {

        // 1. Vérifier que la session apprenant à modifier existe
        SessionApprenant sessionApprenant = sessionApprenantRepository.findById(dto.getIdSessionApprenant())
                .orElseThrow(() -> new EntityNotFoundException("Session de l'apprenant introuvable"));


        // 2. Appliquer les modifications aux champs autorisés
        sessionApprenant.setCommentaireSessionApprenant(dto.getCommentaireSessionApprenant());
        sessionApprenant.setModeReceptionInscription(dto.getModeReceptionInscription());
        sessionApprenant.setStatutSessionApprenant(dto.getStatutSessionApprenant());
        sessionApprenant.setApprenant(sessionApprenant.getApprenant());
        sessionApprenant.setSessionFormation(sessionApprenant.getSessionFormation());


        // 3. Valider si nécessaire
        validerCommentaire(sessionApprenant);
        validerModeReception(sessionApprenant);
        validerEnumNonNulle(sessionApprenant.getStatutSessionApprenant(), "Le statut ne peut pas être nul.");
        validerApprenant(sessionApprenant.getApprenant().getIdPersonne());
        validerSessionFormation(sessionApprenant.getSessionFormation().getIdSessionFormation());

        // 5. Sauvegarde finale
        return sessionApprenantRepository.save(sessionApprenant);
    }

    /**
     * Fonctionnalité qui permet de supprimer une session d'un apprenant
     *
     * @param idSessionApprenant
     */
    @Override
    public void supprimerSessionApprenant(long idSessionApprenant) {

        if (idSessionApprenant <= 0) {
            throw new IllegalArgumentException("L'identifiant de la session de l'apprenant n'existe pas.");
        }

        if (!sessionApprenantRepository.existsById(idSessionApprenant)) {
            throw new EntityNotFoundException("La session de la formation avec l'ID " + idSessionApprenant + " n'existe pas.");
        }

        try {
            sessionApprenantRepository.deleteById(idSessionApprenant);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Impossible de supprimer la sessionApprenant : il est liée à des sessions.");
        } catch (Exception e) {
            throw new RuntimeException("Impossible de supprimer la sessionApprenant (id = " + idSessionApprenant + ")" + e.getMessage());
        }

    }

    // Méthodes de contraintes de validation

    /**
     * Validation si les chaines de caractères ne sont pas nulles ou vides, sinon on déclenche une exception.
     */
    private void validerChaineNonNulle(String chaine, String msgErreur) {

        if (chaine == null || chaine.isBlank()) {
            throw new RuntimeException(msgErreur);
        }
    }

    /**
     * Valider commentaire, longueur max 2000
     */
    public void validerCommentaire(SessionApprenant sessionApprenant) {

        String commentaire = sessionApprenant.getCommentaireSessionApprenant();

        if (commentaire.length() > 2000) {
            throw new RuntimeException("Le commentaire ne doit pas dépasser 2000 caractères.");
        }

    }

    /**
     * Valider mode de réception, not null, longueur entre 3 et 20
     */
    public void validerModeReception(SessionApprenant sessionApprenant) {

        String modeReception = sessionApprenant.getModeReceptionInscription();

        validerChaineNonNulle(modeReception, "Le mode de réception est obligatoire.");

        if (modeReception.length() < 3 || modeReception.length() > 20) {
            throw new RuntimeException("Le nombre de caractères doit être compris entre 3 et 20.");
        }
    }


    /**
     * Validation si les énumérations ne sont pas nulles ou vides, sinon on déclenche une exception.
     */
    private void validerEnumNonNulle(Enum<?> statut, String msgErreur) {
        if (statut == null) {
            throw new RuntimeException(msgErreur);
        }
    }

    /**
     * Validation de l'apprenant, il ne doit pas être nul ou vide, sinon on déclenche une exception.
     */
    private void validerApprenant(Long idApprenant) {

        if (idApprenant == null || idApprenant <= 0) {
            throw new RuntimeException("L'apprenant doit être choisi.");
        }
    }

    /**
     * Validation de la session de formation, elle ne doit pas être nulle ou vide, sinon on déclenche une exception.
     */
    private void validerSessionFormation(Long idSessionFormation) {

        if (idSessionFormation == null || idSessionFormation <= 0) {
            throw new RuntimeException("La session de formation doit être choisie.");
        }
    }


}
