package fr.mediaskol.projet.bll.sessionLieuDate;

import fr.mediaskol.projet.bo.salle.SessionSalle;
import fr.mediaskol.projet.bo.sessionLieuDate.SessionLieuDate;
import fr.mediaskol.projet.dal.salle.SessionSalleRepository;
import fr.mediaskol.projet.dal.sessionLieuDate.SessionLieuDateRepository;
import fr.mediaskol.projet.dto.salle.SessionSalleInputDTO;
import fr.mediaskol.projet.dto.sessionLieuDate.SessionLieuDateInputDTO;
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
public class SessionLieuDateServiceImpl implements SessionLieuDateService {

    /**
     * Injection des repository en couplage faible.
     */
    private final SessionLieuDateRepository sessionLieuDateRepository;

    /***
     * Fonctionnalité qui permet de charger tous les lieux et dates de sessions
     */
    @Override
    public List<SessionLieuDate> chargerToutesSessionsLieuDate() {

        return sessionLieuDateRepository.findAll();
    }

    /**
     * Fonctionnalité qui permet de charger un lieu et date d'une session de formation par son id
     *
     * @param idSessionLieuDate
     */
    @Override
    public SessionLieuDate chargerSessionLieuDateParId(long idSessionLieuDate) {

        // Validation de l'identifiant
        if (idSessionLieuDate <= 0) {
            throw new RuntimeException("L'identifiant n'existe pas");
        }

        // On retourne la date et le lieu d'une session
        final Optional<SessionLieuDate> opt = sessionLieuDateRepository.findById(idSessionLieuDate);
        if (opt.isPresent()) {
            return opt.get();
        }

        // Identifiant correspond à aucun enregistrement en base
        throw new RuntimeException("Aucun lieu et date ne correspondent.");
    }

    /**
     * Fonctionnalité qui permet d'ajouter un lieu et une date à une session de formation
     *
     * @param sessionLieuDate
     */
    @Override
    @Transactional
    public SessionLieuDate ajouterSessionLieuDate(SessionLieuDate sessionLieuDate) {

        if (sessionLieuDate == null) {
            throw new RuntimeException("La date et le lieu ne sont de la session ne sont pas renseignés.");
        }

        validerDateNonNulle(sessionLieuDate);
        if (sessionLieuDate.getLieuSession() != null) {
            validerLieu(sessionLieuDate);
        }
        validerSessionFormation(sessionLieuDate.getSessionFormation().getIdSessionFormation());

        try {
            return sessionLieuDateRepository.save(sessionLieuDate);
        } catch (RuntimeException e) {
            throw new RuntimeException("Impossible de sauvegarder - " + sessionLieuDate.toString());
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
    public SessionLieuDate modifierSessionLieuDate(SessionLieuDateInputDTO dto) {

        // 1. Vérifier qu'un lieu et une date existent
        SessionLieuDate sessionLieuDate = sessionLieuDateRepository.findById(dto.getIdSessionLieuDate())
                .orElseThrow(() -> new EntityNotFoundException("SessionLieuDate de la salle introuvable"));


        // 2. Vérification des champs
        validerDateNonNulle(sessionLieuDate);
        if (sessionLieuDate.getLieuSession() != null) {
            validerLieu(sessionLieuDate);
        }
        validerSessionFormation(sessionLieuDate.getSessionFormation().getIdSessionFormation());


        // 3. Appliquer les modifications aux champs autorisés
        sessionLieuDate.setDateSession(dto.getDateSession());
        sessionLieuDate.setLieuSession(dto.getLieuSession());
        sessionLieuDate.setDuree(dto.getDuree());
        sessionLieuDate.setHeureVisio(dto.getHeureVisio());
        sessionLieuDate.setStatutSessionLieuDate(dto.getStatutSessionLieuDate());
        sessionLieuDate.setSessionFormateur(sessionLieuDate.getSessionFormateur());
        sessionLieuDate.setSessionFormation(sessionLieuDate.getSessionFormation());
        sessionLieuDate.setSessionSalle(sessionLieuDate.getSessionSalle());

        // 4. Sauvegarde finale
        return sessionLieuDateRepository.save(sessionLieuDate);
    }

    /**
     * Fonctionnalité qui permet de supprimer une session d'une salle
     *
     * @param idSessionLieuDate
     */
    @Override
    public void supprimerSessionLieuDate(long idSessionLieuDate) {

        if (idSessionLieuDate <= 0) {
            throw new IllegalArgumentException("L'identifiant du lieu et de la date de la session n'existen pas.");
        }

        if (!sessionLieuDateRepository.existsById(idSessionLieuDate)) {
            throw new EntityNotFoundException(STR."Le lieu et la date de la session de formation avec l'ID \{idSessionLieuDate} n'existent pas.");
        }

        try {
            sessionLieuDateRepository.deleteById(idSessionLieuDate);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Impossible de supprimer le lieu et la date : il est liée à des sessions.");
        } catch (Exception e) {
            throw new RuntimeException(STR."Impossible de supprimer le lieu et la date de la session de formation (id = \{idSessionLieuDate})\{e.getMessage()}");
        }

    }


    // Méthodes de contraintes de validation

    /**
     * Validation si les chaines de caractères ne sont pas nulles ou vides, sinon on déclenche une exception.
     */
    private void validerDateNonNulle(SessionLieuDate sessionLieuDate) {

        LocalDate dateSession = sessionLieuDate.getDateSession();

        if (dateSession == null) {
            throw new RuntimeException("La date est obligatoire.");
        }


        if (dateSession.isBefore(LocalDate.now())) {
            throw new RuntimeException("La date ne doit pas se situer dans le passé.");
        }
    }

    /**
     * Valider commentaire, longueur max 2000
     */
    public void validerLieu(SessionLieuDate sessionLieuDate) {

        String lieuSession = sessionLieuDate.getLieuSession();

        if (lieuSession.length() > 100) {
            throw new RuntimeException("Le nom du lieu ne doit pas dépasser 100 caractères.");
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
