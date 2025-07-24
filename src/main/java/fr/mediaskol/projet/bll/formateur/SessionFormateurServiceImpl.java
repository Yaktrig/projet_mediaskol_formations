package fr.mediaskol.projet.bll.formateur;


import fr.mediaskol.projet.bo.formateur.SessionFormateur;
import fr.mediaskol.projet.dal.formateur.SessionFormateurRepository;
import fr.mediaskol.projet.dto.formateur.SessionFormateurInputDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class SessionFormateurServiceImpl implements SessionFormateurService {

    /**
     * Injection des repository en couplage faible.
     */
    private final SessionFormateurRepository sessionFormateurRepository;


    /***
     * Fonctionnalité qui permet de charger toutes les sessions des formateurs
     */
    @Override
    public List<SessionFormateur> chargerToutesSessionsFormateur() {
        return sessionFormateurRepository.findAll();
    }

    /**
     * Fonctionnalité qui permet de charger une sessionFormateur par son id
     *
     * @param idSessionFormateur
     */
    @Override
    public SessionFormateur chargerSessionFormateurParId(long idSessionFormateur) {

        // Validation de l'identifiant
        if (idSessionFormateur <= 0) {
            throw new RuntimeException("L'identifiant n'existe pas");
        }

        // On retourne une session d'un formateur
        final Optional<SessionFormateur> opt = sessionFormateurRepository.findById(idSessionFormateur);
        if (opt.isPresent()) {
            return opt.get();
        }

        // Identifiant correspond à aucun enregistrement en base
        throw new RuntimeException("Aucune session de formateur ne correspond");
    }

    /**
     * Fonctionnalité qui permet d'ajouter une session d'un formateur
     *
     * @param sessionFormateur
     */
    @Override
    @Transactional
    public SessionFormateur ajouterSessionFormateur(SessionFormateur sessionFormateur) {

        if (sessionFormateur == null) {
            throw new RuntimeException("La session du formateur n'est pas renseignée.");
        }

        if (sessionFormateur.getCommentaireSessionFormateur() != null) {
            validerCommentaire(sessionFormateur);
        }
        validerEnumNonNulle(sessionFormateur.getStatutSessionFormateur(), "Le statut ne peut pas être nul.");
        validerFormateur(sessionFormateur.getFormateur().getIdPersonne());
        validerSessionFormation(sessionFormateur.getSessionFormation().getIdSessionFormation());

        try {
            return sessionFormateurRepository.save(sessionFormateur);
        } catch (RuntimeException e) {
            throw new RuntimeException("Impossible de sauvegarder - " + sessionFormateur.toString());
        }
    }


    /**
     * Fonctionnalité qui permet de modifier une sessionFormateur
     *
     * @param dto
     * @return
     */
    @Override
    @Transactional
    public SessionFormateur modifierSessionFormateur(SessionFormateurInputDTO dto) {

        // 1. Vérifier que la session du formateur à modifier existe
        SessionFormateur sessionFormateur = sessionFormateurRepository.findById(dto.getIdSessionFormateur())
                .orElseThrow(() -> new EntityNotFoundException("Session duformateur introuvable"));


        // 2. Vérification des champs
        if (sessionFormateur.getCommentaireSessionFormateur() != null) {
            validerCommentaire(sessionFormateur);
        }
        validerEnumNonNulle(sessionFormateur.getStatutSessionFormateur(), "Le statut ne peut pas être nul.");
        validerFormateur(sessionFormateur.getFormateur().getIdPersonne());
        validerSessionFormation(sessionFormateur.getSessionFormation().getIdSessionFormation());


        // 3. Appliquer les modifications aux champs autorisés
        sessionFormateur.setCommentaireSessionFormateur(dto.getCommentaireSessionFormateur());
        sessionFormateur.setStatutSessionFormateur(dto.getStatutSessionFormateur());
        sessionFormateur.setFormateur(sessionFormateur.getFormateur());
        sessionFormateur.setSessionFormation(sessionFormateur.getSessionFormation());

        // 4. Sauvegarde finale
        return sessionFormateurRepository.save(sessionFormateur);
    }

    /**
     * Fonctionnalité qui permet de supprimer une session d'un formateur
     *
     * @param idSessionFormateur
     */
    @Override
    public void supprimerSessionFormateur(long idSessionFormateur) {

        if (idSessionFormateur <= 0) {
            throw new IllegalArgumentException("L'identifiant de la session du formateur n'existe pas.");
        }

        if (!sessionFormateurRepository.existsById(idSessionFormateur)) {
            throw new EntityNotFoundException("La session du formateur avec l'ID " + idSessionFormateur + " n'existe pas.");
        }

        try {
            sessionFormateurRepository.deleteById(idSessionFormateur);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Impossible de supprimer la sessionFormateur : il est liée à des sessions.");
        } catch (Exception e) {
            throw new RuntimeException("Impossible de supprimer la sessionFormateur (id = " + idSessionFormateur + ")" + e.getMessage());
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
    public void validerCommentaire(SessionFormateur sessionFormateur) {

        String commentaire = sessionFormateur.getCommentaireSessionFormateur();

        if (commentaire.length() > 2000) {
            throw new RuntimeException("Le commentaire ne doit pas dépasser 2000 caractères.");
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
     * Validation du formateur, il ne doit pas être nul ou vide, sinon on déclenche une exception.
     */
    private void validerFormateur(Long idFormateur) {

        if (idFormateur == null || idFormateur <= 0) {
            throw new RuntimeException("Le formateur doit être choisi.");
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