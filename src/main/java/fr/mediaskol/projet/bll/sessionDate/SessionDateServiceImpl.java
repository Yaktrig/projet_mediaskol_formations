package fr.mediaskol.projet.bll.sessionDate;

import fr.mediaskol.projet.bo.sessionDate.SessionDate;
import fr.mediaskol.projet.dal.sessionDate.SessionDateRepository;
import fr.mediaskol.projet.dto.sessionDate.SessionDateInputDTO;
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
public class SessionDateServiceImpl implements SessionDateService {

    /**
     * Injection des repository en couplage faible.
     */
    private final SessionDateRepository sessionDateRepository;

    /***
     * Fonctionnalité qui permet de charger tous les dates de sessions
     */
    @Override
    public List<SessionDate> chargerToutesSessionsDate() {

        return sessionDateRepository.findAll();
    }

    /**
     * Fonctionnalité qui permet de charger une date d'une session de formation par son id
     *
     * @param idSessionDate
     */
    @Override
    public SessionDate chargerSessionDateParId(long idSessionDate) {

        // Validation de l'identifiant
        if (idSessionDate <= 0) {
            throw new RuntimeException("L'identifiant n'existe pas");
        }

        // On retourne la date d'une session
        final Optional<SessionDate> opt = sessionDateRepository.findById(idSessionDate);
        if (opt.isPresent()) {
            return opt.get();
        }

        // Identifiant correspond à aucun enregistrement en base
        throw new RuntimeException("Aucune date ne correspond.");
    }

    /**
     * Fonctionnalité qui permet d'ajouter une date à une session de formation
     *
     * @param sessionDate
     */
    @Override
    @Transactional
    public SessionDate ajouterSessionDate(SessionDate sessionDate) {

        if (sessionDate == null) {
            throw new RuntimeException("La date de la session n'est pas renseignée.");
        }


        validerSessionFormation(sessionDate.getSessionFormation().getIdSessionFormation());

        try {
            return sessionDateRepository.save(sessionDate);
        } catch (RuntimeException e) {
            throw new RuntimeException("Impossible de sauvegarder - " + sessionDate.toString());
        }
    }

    /**
     * Fonctionnalité qui permet de modifier une date d'une session
     *
     * @param dto
     * @return
     */
    @Override
    @Transactional
    public SessionDate modifierSessionDate(SessionDateInputDTO dto) {

        // 1. Vérifier qu'une date existent
        SessionDate sessionDate = sessionDateRepository.findById(dto.getIdSessionDate())
                .orElseThrow(() -> new EntityNotFoundException("SessionDate de la salle introuvable"));


        // 2. Vérification des champs
        validerSessionFormation(sessionDate.getSessionFormation().getIdSessionFormation());


        // 3. Appliquer les modifications aux champs autorisés
        sessionDate.setDateSession(dto.getDateSession());
        sessionDate.setDuree(dto.getDuree());
        sessionDate.setHeureVisio(dto.getHeureVisio());
        sessionDate.setStatutSessionDate(dto.getStatutSessionDate());

        if(dto.getSessionFormateurId() != null){
            sessionDate.setSessionFormateur(sessionDate.getSessionFormateur());
        }

        if(dto.getSessionFormationId() !=null){
            sessionDate.setSessionFormation(sessionDate.getSessionFormation());
        }

        if(dto.getSessionSalleId() !=null){
            sessionDate.setSessionSalle(sessionDate.getSessionSalle());
        }

        // 4. Sauvegarde finale
        return sessionDateRepository.save(sessionDate);
    }

    /**
     * Fonctionnalité qui permet de supprimer une session d'une salle
     *
     * @param idSessionDate
     */
    @Override
    public void supprimerSessionDate(long idSessionDate) {

        if (idSessionDate <= 0) {
            throw new IllegalArgumentException("L'identifiant de la date de la session n'existe pas.");
        }

        if (!sessionDateRepository.existsById(idSessionDate)) {
            throw new EntityNotFoundException("La date de la session de formation avec l'ID idSessionDate" +
                    " n'existe pas.");
        }

        try {
            sessionDateRepository.deleteById(idSessionDate);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Impossible de supprimer la date : il est liée à des sessions.");
        } catch (Exception e) {
            throw new RuntimeException("Impossible de supprimer la date de la session de formation" +
                    " (id = idSessionDate) " + e.getMessage());
        }

    }


    // Méthodes de contraintes de validation

    /**
     * Validation si les chaines de caractères ne sont pas nulles ou vides, sinon on déclenche une exception.
     */
    private void validerDateNonNulle(SessionDate sessionDate) {

        LocalDate dateSession = sessionDate.getDateSession();

        if (dateSession == null) {
            throw new RuntimeException("La date est obligatoire.");
        }


        if (dateSession.isBefore(LocalDate.now())) {
            throw new RuntimeException("La date ne doit pas se situer dans le passé.");
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
