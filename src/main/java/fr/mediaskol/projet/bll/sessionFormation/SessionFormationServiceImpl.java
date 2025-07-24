package fr.mediaskol.projet.bll.sessionFormation;

import fr.mediaskol.projet.bo.adresse.Adresse;
import fr.mediaskol.projet.bo.departement.Departement;
import fr.mediaskol.projet.bo.formation.Formation;
import fr.mediaskol.projet.bo.formation.TypeFormation;
import fr.mediaskol.projet.bo.sessionFormation.FinSessionFormation;
import fr.mediaskol.projet.bo.sessionFormation.SessionFormation;
import fr.mediaskol.projet.bo.sessionFormationDistanciel.SessionFormationDistanciel;
import fr.mediaskol.projet.dal.departement.DepartementRepository;
import fr.mediaskol.projet.dal.sessionFormation.FinSessionFormationRepository;
import fr.mediaskol.projet.dal.sessionFormation.SessionFormationRepository;
import fr.mediaskol.projet.dal.sessionFormationDistanciel.SessionFormationDistancielRepository;
import fr.mediaskol.projet.dto.sessionFormation.SessionFormationInputDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class SessionFormationServiceImpl implements SessionFormationService {

    /**
     * Injection des repository en couplage faible.
     */
    private final SessionFormationRepository sessionFormationRepository;
    private final DepartementRepository departementRepository;
    private final FinSessionFormationRepository finSessionFormationRepository;
    private final SessionFormationDistancielRepository sessionFormationDistancielRepository;

    /***
     * Fonctionnalité qui permet de charger toutes les Sessions de formations
     */
    @Override
    public List<SessionFormation> chargerToutesSessionsFormations() {
        return sessionFormationRepository.findAll();
    }

    /**
     * Fonctionnalité qui permet de charger une session de formation par son id
     *
     * @param idSessionFormation
     */
    @Override
    public SessionFormation chargerSessionFormationParId(long idSessionFormation) {

        // Validation de l'identifiant
        if (idSessionFormation <= 0) {
            throw new RuntimeException("L'identifiant n'existe pas");
        }

        // On retourne une session de formation
        final Optional<SessionFormation> opt = sessionFormationRepository.findById(idSessionFormation);
        if (opt.isPresent()) {
            return opt.get();
        }

        // Identifiant correspond à aucun enregistrement en base
        throw new RuntimeException("Aucune session de formation ne correspond");
    }

    /**
     * Fonctionnalité qui permet d'ajouter une session de formation
     *
     * @param sessionFormation
     */
    @Override
    public SessionFormation ajouterSessionFormation(SessionFormation sessionFormation) {

        if (sessionFormation == null) {
            throw new RuntimeException("La session de formation n'est pas renseignée.");
        }

        validerUniciteNoYoda(sessionFormation);
        validerLibelle(sessionFormation.getLibelleSessionFormation());
        validerChaineNonNulle(sessionFormation.getStatutYoda(), "Le statut Yoda est obligatoire.");
        validerDate(sessionFormation);
        validerNombreNonNul(sessionFormation.getNbHeureSession(), "Le nombre d'heure pour la session de formation est obligatoire.");
        validerEnumNonNulle(sessionFormation.getStatutSessionFormation());
        validerFormationNonNulle(sessionFormation);

        // Associer la formation
        sessionFormation.setFormation(sessionFormation.getFormation());

        // valider si le département existe
        if (sessionFormation.getDepartement() != null) {
            Departement departementDB = departementRepository.save(sessionFormation.getDepartement());
            sessionFormation.setDepartement(departementDB);
        }

        // valider si la fin de session existe
        if (sessionFormation.getFinSessionFormation() != null) {
            FinSessionFormation finSessionFormationDB = finSessionFormationRepository.save(sessionFormation.getFinSessionFormation());
            sessionFormation.setFinSessionFormation(finSessionFormationDB);
        }

        // valider si la session foad existe
        if (sessionFormation.getSessionFormationDistanciel() != null) {
            SessionFormationDistanciel sessionFormationDistancielDB = sessionFormationDistancielRepository.save(sessionFormation.getSessionFormationDistanciel());
            sessionFormation.setSessionFormationDistanciel(sessionFormationDistancielDB);
        }


        try {
            return sessionFormationRepository.save(sessionFormation);
        } catch (RuntimeException e) {
            throw new RuntimeException("Impossible de sauvegarder - " + sessionFormation.toString());
        }
    }


    /**
     * Fonctionnalité qui permet de modifier une session de formation
     *
     * @param dto
     * @return
     */
    @Override
    public SessionFormation modifierSessionFormation(SessionFormationInputDTO dto) {

        // 1. Vérifier que la sessionFormation à modifier existe
        SessionFormation sessionFormation = sessionFormationRepository.findById(dto.getIdSessionFormation())
                .orElseThrow(() -> new EntityNotFoundException("SessionFormation introuvable"));


        // 3. Appliquer les modifications aux champs autorisés
        sessionFormation.setNoYoda(dto.getNoYoda());
        sessionFormation.setStatutYoda(dto.getStatutYoda());
        sessionFormation.setLibelleSessionFormation(dto.getLibelleSessionFormation());
        sessionFormation.setDateDebutSession(dto.getDateDebutSession());
        sessionFormation.setNbHeureSession(dto.getNbHeureSession());
        sessionFormation.setStatutSessionFormation(dto.getStatutSessionFormation());
        validerFormationNonNulle(sessionFormation);

        // Associer la formation
        sessionFormation.setFormation(sessionFormation.getFormation());

        // Associer le département
        if (dto.getDepartementId() != null) {
            Departement departement = departementRepository.findById(dto.getDepartementId())
                    .orElseThrow(() -> new EntityNotFoundException("Departement introuvable (id = " + dto.getDepartementId() + ")"));
            sessionFormation.setDepartement(departement);
        } else {
            sessionFormation.setDepartement(null); // ou garder l'existante
        }


        // Associer la fin de session de formation
//        if (dto.getFinSessionFormationId() != null) {
//            FinSessionFormation finSessionFormation = finSessionFormationRepository.findById(dto.getFinSessionFormationId())
//                    .orElseThrow(() -> new EntityNotFoundException("FinSessionFormation introuvable (id = " + dto.getFinSessionFormationId() + ")"));
//            sessionFormation.setFinSessionFormation(departement);
//        } else {
//            sessionFormation.setFinSessionFormation(null); // ou garder l'existante
//        }

        // Associer la session de formation en distanciel
//        if (dto.setSessionFormationDistancielId() = null) {
//            SessionFormationDistanciel sessionFoad = sessionFormationDistancielRepository.findById(dto.setSessionFormationDistancielId())
//                    .orElseThrow(() -> new EntityNotFoundException("SessionFormationDistanciel introuvable (id = " + dto.getSessionFormationDistancielId() + ")"));
//            sessionFormation.setFinSessionFormation(sessionFoad);
//        } else {
//            sessionFormation.setSessionFormationDistanciel(null); // ou garder l'existante
//        }

        // 4. Valider
        validerUniciteNoYoda(sessionFormation);
        validerLibelle(sessionFormation.getLibelleSessionFormation());
        validerChaineNonNulle(sessionFormation.getStatutYoda(), "Le statut Yoda est obligatoire.");
        validerDate(sessionFormation);
        validerNombreNonNul(sessionFormation.getNbHeureSession(), "Le nombre d'heure pour la session de formation est obligatoire.");
        validerEnumNonNulle(sessionFormation.getStatutSessionFormation());
        validerFormationNonNulle(sessionFormation);


        // 5. Sauvegarde finale
        return sessionFormationRepository.save(sessionFormation);
    }

    /**
     * Fonctionnalité qui permet de supprimer une session de formation
     *
     * @param idSessionFormation
     */
    @Override
    public void supprimerSessionFormation(long idSessionFormation) {

        if (idSessionFormation <= 0) {
            throw new IllegalArgumentException("L'identifiant de la session de la formation n'existe pas.");
        }

        if (!sessionFormationRepository.existsById(idSessionFormation)) {
            throw new EntityNotFoundException("La session de la formation avec l'ID " + idSessionFormation + " n'existe pas.");
        }

        try {
            sessionFormationRepository.deleteById(idSessionFormation);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Impossible de supprimer la sessionFormation : il est liée à des sessions de sessionFormations.");
        } catch (Exception e) {
            throw new RuntimeException("Impossible de supprimer la sessionFormation (id = " + idSessionFormation + ")" + e.getMessage());
        }
    }

    // Méthodes de contrôle de contraintes


    /**
     * Validation si les chaines de caractères ne sont pas nulles ou vides, sinon on déclenche une exception.
     */
    private void validerChaineNonNulle(String chaine, String msgErreur) {

        if (chaine == null || chaine.isBlank()) {
            throw new RuntimeException(msgErreur);
        }
    }

    /**
     * Validation si les nombres ne sont pas nuls ou vides, sinon on déclenche une exception.
     */
    private void validerNombreNonNul(int nombre, String msgErreur) {

        if (nombre <= 0) {
            throw new RuntimeException(msgErreur);
        }
    }

    /**
     * Valider date non nulle et format date
     */
    private void validerDate(SessionFormation sessionFormation) {

        LocalDate dateDebutSession = sessionFormation.getDateDebutSession();

        if (dateDebutSession == null) {
            throw new RuntimeException("La date est obligatoire.");
        }

        if (dateDebutSession.isBefore(LocalDate.of(2010, 1, 1))) {
            throw new RuntimeException("Le format de la date n'est pas respecté.");
        }

    }

    /**
     * Valider formation non nulle
     */
    private void validerFormationNonNulle(SessionFormation sessionFormation) {

        long idFormation = sessionFormation.getFormation().getIdFormation();

        if (idFormation <= 0) {
            throw new RuntimeException("La formation est obligatoire.");
        }

    }

    /**
     * Validation le numéro Yoda de la session de formation, il est unique, il peut être nul.
     * Sa longueur maximale est de 30 caractères.
     */
    private void validerUniciteNoYoda(SessionFormation sessionFormation) {

        String noYoda = sessionFormation.getNoYoda();

        if (noYoda.length() > 30) {
            throw new RuntimeException("Le numéro Yoda est trop long.");
        }

        if (noYoda != null && !noYoda.isBlank()) {
            Optional<SessionFormation> existant = sessionFormationRepository.findByNoYoda(noYoda);

            if (existant.isPresent()) {
                // Si on est en création (idSessionFormation null) OU
                // Si le numéro appartient à une autre session de formation => erreur
                if (sessionFormation.getIdSessionFormation() == null ||
                        !existant.get().getIdSessionFormation().equals(sessionFormation.getIdSessionFormation())) {
                    throw new RuntimeException("Le numéro Yoda existe déjà.");
                }
            }
        }
    }


    /**
     * Validation de la taille du libellé d'une session de formation - max 50 caractères
     */
    private void validerLibelle(String libelleSessionFormation) {

        validerChaineNonNulle(libelleSessionFormation, "Le libellé de la session de formation est obligatoire.");

        if (libelleSessionFormation.length() > 50) {
            throw new RuntimeException("Le champ du libellé ne doit pas dépasser 50 caractères.");
        }
    }


    /**
     * Validation si les énumérations ne sont pas nulles ou vides, sinon on déclenche une exception.
     */
    private void validerEnumNonNulle(Enum<?> statut) {

        if (statut == null) {
            throw new RuntimeException("Le statut de la session de formation est obligatoire.");
        }
    }


}
