package fr.mediaskol.projet.bll.salle;

import fr.mediaskol.projet.bo.salle.SessionSalle;
import fr.mediaskol.projet.dal.salle.SessionSalleRepository;
import fr.mediaskol.projet.dto.salle.SessionSalleInputDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.ReflectionHelper.typeOf;

@AllArgsConstructor
@Service
public class SessionSalleServiceImpl implements SessionSalleService{

    /**
     * Injection des repository en couplage faible.
     */
    private final SessionSalleRepository sessionSalleRepository;

    /***
     * Fonctionnalité qui permet de charger toutes les sessions des salles
     */
    @Override
    public List<SessionSalle> chargerToutesSessionsSalle() {
        return sessionSalleRepository.findAll();
    }

    /**
     * Fonctionnalité qui permet de charger une sessionSalle par son id
     *
     * @param idSessionSalle
     */
    @Override
    public SessionSalle chargerSessionSalleParId(long idSessionSalle) {

        // Validation de l'identifiant
        if (idSessionSalle <= 0) {
            throw new RuntimeException("L'identifiant n'existe pas");
        }

        // On retourne une session d'une salle
        final Optional<SessionSalle> opt = sessionSalleRepository.findById(idSessionSalle);
        if (opt.isPresent()) {
            return opt.get();
        }

        // Identifiant correspond à aucun enregistrement en base
        throw new RuntimeException("Aucune session de salle ne correspond");
    }

    /**
     * Fonctionnalité qui permet d'ajouter une session d'une salle
     *
     * @param sessionSalle
     */
    @Override
    @Transactional
    public SessionSalle ajouterSessionSalle(SessionSalle sessionSalle) {

        if (sessionSalle == null) {
            throw new RuntimeException("La session de la salle n'est pas renseignée.");
        }


        if (sessionSalle.getCommentaireSessionSalle() != null) {
            validerCommentaire(sessionSalle);
        }
        validerEnumNonNulle(sessionSalle.getStatutSessionSalle(), "Le statut ne peut pas être nul.");
        validerSalle(sessionSalle.getSalle().getIdSalle());
        validerSessionFormation(sessionSalle.getSessionFormationPresentiel().getIdSessionFormation());

        try {
            return sessionSalleRepository.save(sessionSalle);
        } catch (RuntimeException e) {
            throw new RuntimeException("Impossible de sauvegarder - " + sessionSalle.toString());
        }
    }

    /**
     * Fonctionnalité qui permet de modifier une sessionSalle
     *
     * @param dto
     * @return
     */
    @Override
    @Transactional
    public SessionSalle modifierSessionSalle(SessionSalleInputDTO dto) {

        // 1. Vérifier que la session de salle à modifier existe
        SessionSalle sessionSalle = sessionSalleRepository.findById(dto.getIdSessionSalle())
                .orElseThrow(() -> new EntityNotFoundException("Session de la salle introuvable"));


        // 2. Vérification des champs
        if (sessionSalle.getCommentaireSessionSalle() != null) {
            validerCommentaire(sessionSalle);
        }
        validerEnumNonNulle(sessionSalle.getStatutSessionSalle(), "Le statut ne peut pas être nul.");
        validerSalle(sessionSalle.getSalle().getIdSalle());
        validerSessionFormation(sessionSalle.getSessionFormationPresentiel().getIdSessionFormation());


        // 3. Appliquer les modifications aux champs autorisés
        sessionSalle.setCommentaireSessionSalle(dto.getCommentaireSessionSalle());
        sessionSalle.setStatutSessionSalle(dto.getStatutSessionSalle());
        sessionSalle.setSalle(sessionSalle.getSalle());
        sessionSalle.setSessionFormationPresentiel(sessionSalle.getSessionFormationPresentiel());

        // 4. Sauvegarde finale
        return sessionSalleRepository.save(sessionSalle);
    }

    /**
     * Fonctionnalité qui permet de supprimer une session d'une salle
     *
     * @param idSessionSalle
     */
    @Override
    public void supprimerSessionSalle(long idSessionSalle) {

        if (idSessionSalle <= 0) {
            throw new IllegalArgumentException("L'identifiant de la session de la salle n'existe pas.");
        }

        if (!sessionSalleRepository.existsById(idSessionSalle)) {
            throw new EntityNotFoundException("La session de la salle avec l'ID " + idSessionSalle + " n'existe pas.");
        }

        try {
            sessionSalleRepository.deleteById(idSessionSalle);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Impossible de supprimer la sessionSalle : il est liée à des sessions.");
        } catch (Exception e) {
            throw new RuntimeException("Impossible de supprimer la sessionSalle (id = " + idSessionSalle + ")" + e.getMessage());
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
    public void validerCommentaire(SessionSalle sessionSalle) {

        String commentaire = sessionSalle.getCommentaireSessionSalle();

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
     * Validation de la salle, elle ne doit pas être nulle ou vide, sinon on déclenche une exception.
     */
    private void validerSalle(Long idSalle) {

        if (idSalle == null || idSalle <= 0) {
            throw new RuntimeException("La salle doit être choisie.");
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
