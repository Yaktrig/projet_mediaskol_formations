package fr.mediaskol.projet.bll.sessionFormation;

import fr.mediaskol.projet.bo.sessionFormation.SessionFormation;
import fr.mediaskol.projet.bo.sessionFormation.SessionFormationPresentiel;
import fr.mediaskol.projet.dal.sessionFormation.FinSessionFormationRepository;
import fr.mediaskol.projet.dal.sessionFormation.SessionFOPRepository;
import fr.mediaskol.projet.dal.sessionFormation.SessionFormationRepository;
import fr.mediaskol.projet.dal.sessionLieuDate.SessionLieuDateRepository;
import fr.mediaskol.projet.dto.sessionFormation.SessionFOPInputDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Service
public class SessionFOPServiceImpl implements SessionFOPService {

    /**
     * Injection des repository en couplage faible.
     */
    private final SessionFOPRepository sessionFOPRepository;
    private final FinSessionFormationRepository finSessionFormationRepository;
    private final SessionLieuDateRepository sessionLieuDateRepository;
    private final SessionFormationRepository sessionFormationRepository;


    /***
     * Fonctionnalité qui permet de charger toutes les Sessions de formations en présentiel
     */
    @Override
    public List<SessionFormationPresentiel> chargerToutesSessionsFop() {
        return sessionFOPRepository.findAll();
    }

    /**
     * Fonctionnalité qui permet de charger une session de formation en présentiel par son id
     *
     * @param idSessionFop
     */
    @Override
    public SessionFormationPresentiel chargerSessionFopParId(long idSessionFop) {

        // Validation de l'identifiant
        if (idSessionFop <= 0) {
            throw new RuntimeException("L'identifiant n'existe pas");
        }

        // On retourne une session de formation en présentiel
        final Optional<SessionFormationPresentiel> opt = sessionFOPRepository.findById(idSessionFop);
        if (opt.isPresent()) {
            return opt.get();
        }

        // Identifiant correspond à aucun enregistrement en base
        throw new RuntimeException("Aucune session de formation en présentiel ne correspond");
    }


    /**
     * Retourne une liste de sessions de formation en présentiel en fonction de la saisie utilisateur.
     *
     * @param termeRecherche
     * @return sessionFormationRepository.findSessionFormationPresentielsBySearchText(termeRecherche)
     */
    @Override
    public List<SessionFormationPresentiel> rechercheSessionFop(String termeRecherche) {

        String recherche = termeRecherche != null ? termeRecherche.trim().toLowerCase() : "";

        // Vérification si le terme est une date au format jjmmaaaa
        LocalDate dateDebutSession = null;
        if (recherche.matches("^[0-3][0-9][0-1][0-9][0-9]{4}$")) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
                dateDebutSession = LocalDate.parse(recherche, formatter);

                // si c'est le cas, on cherche en priorité sur la date
                return sessionFormationRepository.findByDateDebutSession(dateDebutSession);
            } catch (DateTimeParseException e) {

            }
        }
        return sessionFormationRepository.findSessionFormationsBySearchText(termeRecherche);
    }


    /**
     * Fonctionnalité qui permet d'ajouter une session de formation
     *
     * @param sessionFormation
     */
    @Override
    @Transactional
    public SessionFormationPresentiel ajouterSessionFormationPresentiel(SessionFormationPresentiel sessionFormation) {

        if (sessionFormation == null) {
            throw new RuntimeException("La session de formation n'est pas renseignée.");
        }

        if(sessionFormation.getNoYoda() != null){
            validerUniciteNoYoda(sessionFormation);
        }
        validerLibelle(sessionFormation.getLibelleSessionFormationPresentiel());
        validerChaineNonNulle(sessionFormation.getStatutYoda(), "Le statut Yoda est obligatoire.");
        validerDate(sessionFormation);
        validerNombreNonNul(sessionFormation.getNbHeureSession(), "Le nombre d'heure pour la session de formation est obligatoire.");
        validerEnumNonNulle(sessionFormation.getStatutSessionFormationPresentiel());
        validerFormationNonNulle(sessionFormation);

        // Associer la formation
        sessionFormation.setFormation(sessionFormation.getFormation());

        // Associer le département
        sessionFormation.setDepartement(sessionFormation.getDepartement());

        // Associer le salarié qui traite la session de formation
        sessionFormation.setSalarie(sessionFormation.getSalarie());


        // valider si la fin de session existe
        if (sessionFormation.getFinSessionFormationPresentiel() != null) {
            FinSessionFormationPresentiel finSessionFormationPresentielDB = finSessionFormationPresentielRepository.save(sessionFormation.getFinSessionFormationPresentiel());
            sessionFormation.setFinSessionFormationPresentiel(finSessionFormationPresentielDB);
        }

        // valider si la session foad existe
        if (sessionFormation.getSessionFormationPresentielDistanciel() != null) {
            SessionFormationPresentielDistanciel sessionFormationDistancielDB = sessionFormationDistancielRepository.save(sessionFormation.getSessionFormationPresentielDistanciel());
            sessionFormation.setSessionFormationPresentielDistanciel(sessionFormationDistancielDB);
        }


        try {
            return sessionFormationRepository.save(sessionFormation);
        } catch (RuntimeException e) {
            throw new RuntimeException("Impossible de sauvegarder - " + sessionFormation.getLibelleSessionFormationPresentiel());
        }
    }


    /**
     * Fonctionnalité qui permet de modifier une session de formation
     *
     * @param dto
     * @return
     */
    @Override
    @Transactional
    public SessionFormationPresentiel modifierSessionFormationPresentiel(SessionFOPInputDTO dto) {

        // 1. Vérifier que la sessionFormation à modifier existe
        SessionFormationPresentiel sessionFormation = sessionFormationRepository.findById(dto.getIdSessionFormationPresentiel())
                .orElseThrow(() -> new EntityNotFoundException("SessionFormationPresentiel introuvable"));


        // 3. Appliquer les modifications aux champs autorisés
        sessionFormation.setNoYoda(dto.getNoYoda());
        sessionFormation.setStatutYoda(dto.getStatutYoda());
        sessionFormation.setLibelleSessionFormationPresentiel(dto.getLibelleSessionFormationPresentiel());
        sessionFormation.setDateDebutSession(dto.getDateDebutSession());
        sessionFormation.setNbHeureSession(dto.getNbHeureSession());
        sessionFormation.setStatutSessionFormationPresentiel(dto.getStatutSessionFormationPresentiel());
        validerFormationNonNulle(sessionFormation);

        // Associer la formation
        sessionFormation.setFormation(sessionFormation.getFormation());

        // Associer le salarié
        sessionFormation.setSalarie(sessionFormation.getSalarie());

        // Associer le département
        if (dto.getDepartement() != null) {
            sessionFormation.setDepartement(sessionFormation.getDepartement());
        } else {
            sessionFormation.setDepartement(null); // ou garder l'existante
        }

        // Associer la fin de session de formation
        if (dto.getFinSessionFormationPresentielId() != null) {
            FinSessionFormationPresentiel finSessionFormationPresentiel = finSessionFormationPresentielRepository.findById(dto.getFinSessionFormationPresentielId())
                    .orElseThrow(() -> new EntityNotFoundException("FinSessionFormationPresentiel introuvable (id = " + dto.getFinSessionFormationPresentielId() + ")"));
            sessionFormation.setFinSessionFormationPresentiel(finSessionFormationPresentiel);
        } else {
            sessionFormation.setFinSessionFormationPresentiel(null); // ou garder l'existante
        }

        // Associer la session de formation en distanciel
        if (dto.getSessionFormationPresentielDistancielId() != null) {
            SessionFormationPresentielDistanciel sessionFoad = sessionFormationDistancielRepository.findById(dto.getSessionFormationPresentielDistancielId())
                    .orElseThrow(() -> new EntityNotFoundException("SessionFormationPresentielDistanciel introuvable (id = " + dto.getSessionFormationPresentielDistancielId() + ")"));
            sessionFormation.setSessionFormationPresentielDistanciel(sessionFoad);
        } else {
            sessionFormation.setSessionFormationPresentielDistanciel(null); // ou garder l'existante
        }

        // 4. Valider
        validerUniciteNoYoda(sessionFormation);
        validerLibelle(sessionFormation.getLibelleSessionFormationPresentiel());
        validerChaineNonNulle(sessionFormation.getStatutYoda(), "Le statut Yoda est obligatoire.");
        validerDate(sessionFormation);
        validerNombreNonNul(sessionFormation.getNbHeureSession(), "Le nombre d'heure pour la session de formation est obligatoire.");
        validerEnumNonNulle(sessionFormation.getStatutSessionFormationPresentiel());
        validerFormationNonNulle(sessionFormation);


        // 5. Sauvegarde finale
        return sessionFormationRepository.save(sessionFormation);
    }

    /**
     * Fonctionnalité qui permet de supprimer une session de formation
     *
     * @param idSessionFormationPresentiel
     */
    @Override
    public void supprimerSessionFormationPresentiel(long idSessionFormationPresentiel) {

        if (idSessionFormationPresentiel <= 0) {
            throw new IllegalArgumentException("L'identifiant de la session de la formation n'existe pas.");
        }

        if (!sessionFormationRepository.existsById(idSessionFormationPresentiel)) {
            throw new EntityNotFoundException("La session de la formation avec l'ID " + idSessionFormationPresentiel + " n'existe pas.");
        }

        try {
            sessionFormationRepository.deleteById(idSessionFormationPresentiel);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Impossible de supprimer la sessionFormation : il est liée à des sessions de sessionFormations.");
        } catch (Exception e) {
            throw new RuntimeException("Impossible de supprimer la sessionFormation (id = " + idSessionFormationPresentiel + ")" + e.getMessage());
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
    private void validerDate(SessionFormationPresentiel sessionFormation) {

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
    private void validerFormationNonNulle(SessionFormationPresentiel sessionFormation) {

        long idFormation = sessionFormation.getFormation().getIdFormation();

        if (idFormation <= 0) {
            throw new RuntimeException("La formation est obligatoire.");
        }

    }

    /**
     * Validation le numéro Yoda de la session de formation, il est unique, il peut être nul.
     * Sa longueur maximale est de 30 caractères.
     */
    private void validerUniciteNoYoda(SessionFormationPresentiel sessionFormation) {

        String noYoda = sessionFormation.getNoYoda();

        if (noYoda.length() > 30) {
            throw new RuntimeException("Le numéro Yoda est trop long.");
        }

        if (noYoda != null && !noYoda.isBlank()) {
            Optional<SessionFormationPresentiel> existant = sessionFormationRepository.findByNoYoda(noYoda);

            if (existant.isPresent()) {
                // Si on est en création (idSessionFormationPresentiel null) OU
                // Si le numéro appartient à une autre session de formation => erreur
                if (sessionFormation.getIdSessionFormationPresentiel() == null ||
                        !existant.get().getIdSessionFormationPresentiel().equals(sessionFormation.getIdSessionFormationPresentiel())) {
                    throw new RuntimeException("Le numéro Yoda existe déjà.");
                }
            }
        }
    }


    /**
     * Validation de la taille du libellé d'une session de formation - max 50 caractères
     */
    private void validerLibelle(String libelleSessionFormationPresentiel) {

        validerChaineNonNulle(libelleSessionFormationPresentiel, "Le libellé de la session de formation est obligatoire.");

        if (libelleSessionFormationPresentiel.length() > 50) {
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
