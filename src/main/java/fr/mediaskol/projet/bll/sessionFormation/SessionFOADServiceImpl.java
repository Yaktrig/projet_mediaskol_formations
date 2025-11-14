package fr.mediaskol.projet.bll.sessionFormation;


import fr.mediaskol.projet.bo.sessionFormationDistanciel.SessionFormationDistanciel;
import fr.mediaskol.projet.dal.sessionFormation.SessionFOADRepository;
import fr.mediaskol.projet.dto.sessionFormation.SessionFOADInputDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Service
public class SessionFOADServiceImpl implements SessionFOADService {

    /**
     * Injection des repository en couplage faible.
     */
    private final SessionFOADRepository sessionFoadRepository;

    /***
     * Fonctionnalité qui permet de charger toutes les Sessions de formations à distance
     */
    @Override
    public List<SessionFormationDistanciel> chargerToutesSessionsFoads() {
        return sessionFoadRepository.findAll();
    }

    /**
     * Fonctionnalité qui permet de charger une session de formation à distance par son id
     *
     * @param idSessionFormationDistanciel
     */
    @Override
    public SessionFormationDistanciel chargerSessionFoadParId(long idSessionFormationDistanciel) {

        // Validation de l'identifiant
        if (idSessionFormationDistanciel <= 0) {
            throw new RuntimeException("L'identifiant n'existe pas");
        }

        // On retourne une session de formation à distance
        final Optional<SessionFormationDistanciel> opt = sessionFoadRepository.findById(idSessionFormationDistanciel);
        if (opt.isPresent()) {
            return opt.get();
        }

        // Identifiant correspond à aucun enregistrement en base
        throw new RuntimeException("Aucune session de formation à distance ne correspond");
    }

    /**
     * Fonctionnalité qui permet d'ajouter une session de formation à distance
     *
     * @param sessionFoad
     */
    @Override
    @Transactional
    public SessionFormationDistanciel ajouterSessionFoad(SessionFormationDistanciel sessionFoad) {

        if (sessionFoad == null) {
            throw new RuntimeException("La session de formation à distance n'est pas renseignée.");
        }

        if (sessionFoad.getContratSessionFormationDistanciel() != null) {
            validerContrat(sessionFoad);
        }
        validerDate(sessionFoad.getDateDebutSessionFormationDistanciel());
        validerDate(sessionFoad.getDateFinSessionFormationDistanciel());

        if (sessionFoad.getDateRelanceSessionFormationDistanciel() != null) {
            validerDate(sessionFoad.getDateRelanceSessionFormationDistanciel());
        }

        if (sessionFoad.getCommentaireSessionFormationDistanciel() != null) {
            validerCommentaire(sessionFoad);
        }

        try {
            return sessionFoadRepository.save(sessionFoad);
        } catch (RuntimeException e) {
            throw new RuntimeException("Impossible de sauvegarder - " + sessionFoad.getIdSessionFormationDistanciel());
        }
    }


    /**
     * Fonctionnalité qui permet de modifier une session de formation à distance
     *
     * @param dto
     * @return
     */
    @Override
    @Transactional
    public SessionFormationDistanciel modifierSessionFoad(SessionFOADInputDTO dto) {

        // 1. Vérifier que la sessionFormationDistanciel à modifier existe
        SessionFormationDistanciel sessionFoad = sessionFoadRepository.findById(dto.getIdSessionFormation())
                .orElseThrow(() -> new EntityNotFoundException("SessionFormationDistanciel introuvable"));


        // 2. Valider
        if (sessionFoad.getContratSessionFormationDistanciel() != null) {
            validerContrat(sessionFoad);
        }
        validerDate(sessionFoad.getDateDebutSessionFormationDistanciel());
        validerDate(sessionFoad.getDateFinSessionFormationDistanciel());

        if (sessionFoad.getDateRelanceSessionFormationDistanciel() != null) {
            validerDate(sessionFoad.getDateRelanceSessionFormationDistanciel());
        }

        if (sessionFoad.getCommentaireSessionFormationDistanciel() != null) {
            validerCommentaire(sessionFoad);
        }

        // 3. Appliquer les modifications aux champs autorisés
        sessionFoad.setContratSessionFormationDistanciel(dto.getContratSessionFormationDistanciel());
        sessionFoad.setNbBlocSessionFormationDistanciel(dto.getNbBlocSessionFormationDistanciel());
        sessionFoad.setDateDebutSessionFormationDistanciel(dto.getDateDebutSession());
        sessionFoad.setDateFinSessionFormationDistanciel(dto.getDateFinSessionFormationDistanciel());
        sessionFoad.setDateRelanceSessionFormationDistanciel(dto.getDateRelanceSessionFormationDistanciel());
        sessionFoad.setCommentaireSessionFormationDistanciel(dto.getCommentaireSessionFormationDistanciel());

        // 5. Sauvegarde finale
        return sessionFoadRepository.save(sessionFoad);
    }

    /**
     * Fonctionnalité qui permet de supprimer une session de formation à distance
     *
     * @param idSessionFoad
     */
    @Override
    public void supprimerSessionFoad(long idSessionFoad) {

        if (idSessionFoad <= 0) {
            throw new IllegalArgumentException("L'identifiant de la session de la formation à distance n'existe pas.");
        }

        if (!sessionFoadRepository.existsById(idSessionFoad)) {
            throw new EntityNotFoundException("La session de la formation à distance avec l'ID " + idSessionFoad + " n'existe pas.");
        }

        try {
            sessionFoadRepository.deleteById(idSessionFoad);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Impossible de supprimer la sessionFormationDistanciel.");
        } catch (Exception e) {
            throw new RuntimeException("Impossible de supprimer la sessionFormationDistanciel (id = " + idSessionFoad + ")" + e.getMessage());
        }
    }


    // Méthodes de contrôle de contraintes


    /**
     * Valider date non nulle et format date
     */
    private void validerDate(LocalDate dateASaisir) {


        if (dateASaisir == null) {
            throw new RuntimeException("La date est obligatoire.");
        }

        if (dateASaisir.isBefore(LocalDate.of(2010, 1, 1))) {
            throw new RuntimeException("Le format de la date n'est pas respecté.");
        }

    }

    /**
     * Validation de la taille du champs du contrat d'une session de formation à distance - max 300 caractères
     */
    private void validerContrat(SessionFormationDistanciel sessionFormationDistanciel) {

        String contratFoad = sessionFormationDistanciel.getContratSessionFormationDistanciel();

        if (contratFoad.length() > 300) {
            throw new RuntimeException("Le champ du commentaire ne doit pas dépasser 2000 caractères.");
        }
    }

    /**
     * Validation de la taille du libellé d'une session de formation - max 50 caractères
     */
    private void validerCommentaire(SessionFormationDistanciel sessionFormationDistanciel) {

        String commentaireFoad = sessionFormationDistanciel.getCommentaireSessionFormationDistanciel();

        if (commentaireFoad.length() > 2000) {
            throw new RuntimeException("Le champ du commentaire ne doit pas dépasser 2000 caractères.");
        }
    }


}
