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
     * Fonctionnalité qui permet de charger tous les lieux et dates de sessions
     */
    @Override
    public List<SessionDate> chargerToutesSessionsLieuDate() {

        return sessionDateRepository.findAll();
    }

    /**
     * Fonctionnalité qui permet de charger un lieu et date d'une session de formation par son id
     *
     * @param idSessionLieuDate
     */
    @Override
    public SessionDate chargerSessionLieuDateParId(long idSessionLieuDate) {

        // Validation de l'identifiant
        if (idSessionLieuDate <= 0) {
            throw new RuntimeException("L'identifiant n'existe pas");
        }

        // On retourne la date et le lieu d'une session
        final Optional<SessionDate> opt = sessionDateRepository.findById(idSessionLieuDate);
        if (opt.isPresent()) {
            return opt.get();
        }

        // Identifiant correspond à aucun enregistrement en base
        throw new RuntimeException("Aucun lieu et date ne correspondent.");
    }

    /**
     * Fonctionnalité qui permet d'ajouter un lieu et une date à une session de formation
     *
     * @param sessionDate
     */
    @Override
    @Transactional
    public SessionDate ajouterSessionLieuDate(SessionDate sessionDate) {

        if (sessionDate == null) {
            throw new RuntimeException("La date et le lieu ne sont de la session ne sont pas renseignés.");
        }


        validerSessionFormation(sessionDate.getSessionFormation().getIdSessionFormation());

        try {
            return sessionDateRepository.save(sessionDate);
        } catch (RuntimeException e) {
            throw new RuntimeException("Impossible de sauvegarder - " + sessionDate.toString());
        }
    }

    /**
     * Fonctionnalité qui permet de modifier une date ou un lieu d'une session
     *
     * @param dto
     * @return
     */
    @Override
    @Transactional
    public SessionDate modifierSessionLieuDate(SessionDateInputDTO dto) {

        // 1. Vérifier qu'un lieu et une date existent
        SessionDate sessionDate = sessionDateRepository.findById(dto.getIdSessionDate())
                .orElseThrow(() -> new EntityNotFoundException("SessionLieuDate de la salle introuvable"));


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
     * @param idSessionLieuDate
     */
    @Override
    public void supprimerSessionLieuDate(long idSessionLieuDate) {

        if (idSessionLieuDate <= 0) {
            throw new IllegalArgumentException("L'identifiant du lieu et de la date de la session n'existent pas.");
        }

        if (!sessionDateRepository.existsById(idSessionLieuDate)) {
            throw new EntityNotFoundException("Le lieu et la date de la session de formation avec l'ID idSessionLieuDate" +
                    " n'existent pas.");
        }

        try {
            sessionDateRepository.deleteById(idSessionLieuDate);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Impossible de supprimer le lieu et la date : il est liée à des sessions.");
        } catch (Exception e) {
            throw new RuntimeException("Impossible de supprimer le lieu et la date de la session de formation" +
                    " (id = idSessionLieuDate) " + e.getMessage());
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
