package fr.mediaskol.projet.bll.sessionFormation;

import fr.mediaskol.projet.bo.formateur.SessionFormateur;
import fr.mediaskol.projet.bo.salle.SessionSalle;
import fr.mediaskol.projet.bo.sessionFormation.FinSessionFormation;
import fr.mediaskol.projet.bo.sessionFormation.SessionFormationPresentiel;
import fr.mediaskol.projet.bo.sessionLieuDate.SessionLieuDate;
import fr.mediaskol.projet.dal.formateur.SessionFormateurRepository;
import fr.mediaskol.projet.dal.salle.SessionSalleRepository;
import fr.mediaskol.projet.dal.sessionFormation.FinSessionFormationRepository;
import fr.mediaskol.projet.dal.sessionFormation.SessionFOPRepository;
import fr.mediaskol.projet.dal.sessionFormation.SessionFormationRepository;
import fr.mediaskol.projet.dal.sessionLieuDate.SessionLieuDateRepository;
import fr.mediaskol.projet.dto.formateur.SessionFormateurRespDTO;
import fr.mediaskol.projet.dto.salle.SessionSalleRespDTO;
import fr.mediaskol.projet.dto.sessionFormation.SessionFOPInputDTO;
import fr.mediaskol.projet.dto.sessionFormation.SessionFOPResponseDTO;
import fr.mediaskol.projet.dto.sessionLieuDate.SessionLieuDateRespDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


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
    private final SessionSalleRepository sessionSalleRepository;
    private final SessionFormateurRepository sessionFormateurRepository;


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
                return sessionFOPRepository.findByDateDebutSession(dateDebutSession);
            } catch (DateTimeParseException e) {

            }
        }
        return sessionFOPRepository.findSessionFormationsBySearchText(termeRecherche);
    }


    /**
     * Fonctionnalité qui permet d'ajouter une session de formation
     *
     * @param sessionFop
     */
    @Override
    @Transactional
    public SessionFormationPresentiel ajouterSessionFop(SessionFormationPresentiel sessionFop) {

        if (sessionFop == null) {
            throw new RuntimeException("La session de formation n'est pas renseignée.");
        }

        if (sessionFop.getNoYoda() != null) {
            validerUniciteNoYoda(sessionFop);
        }
        validerLibelle(sessionFop.getLibelleSessionFormation());
        validerChaineNonNulle(sessionFop.getStatutYoda(), "Le statut Yoda est obligatoire.");
        validerDate(sessionFop);
        validerNombreNonNul(sessionFop.getNbHeureSession(), "Le nombre d'heure pour la session de formation est obligatoire.");
        validerEnumNonNulle(sessionFop.getStatutSessionFormation());
        validerFormationNonNulle(sessionFop);

        // Associer la formation
        sessionFop.setFormation(sessionFop.getFormation());

        // Associer le département
        sessionFop.setDepartement(sessionFop.getDepartement());

        // Associer le salarié qui traite la session de formation
        sessionFop.setSalarie(sessionFop.getSalarie());


        // valider si la fin de session existe
        if (sessionFop.getFinSessionFormation() != null) {
            FinSessionFormation finSessionFopDB = finSessionFormationRepository.save(sessionFop.getFinSessionFormation());
            sessionFop.setFinSessionFormation(finSessionFopDB);
        }


        try {
            return sessionFormationRepository.save(sessionFop);
        } catch (RuntimeException e) {
            throw new RuntimeException("Impossible de sauvegarder - " + sessionFop.getLibelleSessionFormation());
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
    public SessionFormationPresentiel modifierSessionFop(SessionFOPInputDTO dto) {

        // Vérifier que la sessionFormation à modifier existe
        SessionFormationPresentiel sessionFop = sessionFOPRepository.findById(dto.getIdSessionFormationPresentiel())
                .orElseThrow(() -> new EntityNotFoundException("Session de formation en présentiel introuvable"));


        // Appliquer les modifications aux champs autorisés
        sessionFop.setNoYoda(dto.getNoYoda());
        sessionFop.setStatutYoda(dto.getStatutYoda());
        sessionFop.setLibelleSessionFormation(dto.getLibelleSessionFormation());
        sessionFop.setDateDebutSession(dto.getDateDebutSession());
        sessionFop.setNbHeureSession(dto.getNbHeureSession());
        sessionFop.setStatutSessionFormation(dto.getStatutSessionFormation());
        validerFormationNonNulle(sessionFop);

        // Associer la formation
        sessionFop.setFormation(sessionFop.getFormation());

        // Associer le salarié
        sessionFop.setSalarie(sessionFop.getSalarie());

        // Associer le département
        if (dto.getDepartement() != null) {
            sessionFop.setDepartement(sessionFop.getDepartement());
        } else {
            sessionFop.setDepartement(null); // ou garder l'existante
        }

        // Associer la fin de session de formation
        if (dto.getFinSessionFormation() != null) {
            FinSessionFormation finSessionFormation = finSessionFormationRepository.findById(dto.getFinSessionFormation().getIdFinSessionFormation())
                    .orElseThrow(() -> new EntityNotFoundException("La fin de la session de formation présentiel introuvable (id = " + dto.getFinSessionFormation().getIdFinSessionFormation() + ")"));
            sessionFop.setFinSessionFormation(finSessionFormation);
        } else {
            sessionFop.setFinSessionFormation(null); // ou garder l'existante
        }


        // Valider
        validerUniciteNoYoda(sessionFop);
        validerLibelle(sessionFop.getLibelleSessionFormation());
        validerChaineNonNulle(sessionFop.getStatutYoda(), "Le statut Yoda est obligatoire.");
        validerDate(sessionFop);
        validerNombreNonNul(sessionFop.getNbHeureSession(), "Le nombre d'heure pour la session de formation est obligatoire.");
        validerEnumNonNulle(sessionFop.getStatutSessionFormation());
        validerFormationNonNulle(sessionFop);


        // Sauvegarde finale
        return sessionFOPRepository.save(sessionFop);
    }

    /**
     * Fonctionnalité qui récupère les sessions salle liées à une session formation en présentiel.
     * Les convertit en DTO
     *
     * @param idSessionFormation
     */
    @Override
    public List<SessionSalleRespDTO> getSessionsSalleBySessionId(Long idSessionFormation) {
        List<SessionSalle> sessionsSalle = sessionSalleRepository.findBySessionFormationPresentielIdSessionFormation(idSessionFormation);

        return sessionsSalle.stream()
                .map(SessionSalleRespDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Fonctionnalité qui récupère les sessions formateurs liées à une session formation en présentiel.
     * Les convertit en DTO
     *
     * @param idSessionFormation
     */
    @Override
    public List<SessionFormateurRespDTO> getSessionsFormateurBySessionId(Long idSessionFormation) {

        List<SessionFormateur> sessionFormateurs = sessionFormateurRepository.findBySessionFormationIdSessionFormation(idSessionFormation);

        return sessionFormateurs.stream()
                .map(SessionFormateurRespDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Fonctionnalité qui récupère les sessions lieu date liées à une session formation en présentiel.
     * Les convertit en DTO
     *
     * @param idSessionFormation
     */
    @Override
    public List<SessionLieuDateRespDTO> getSessionsLieuDateBySessionId(Long idSessionFormation) {

        List<SessionLieuDate> sessionLieuDates = sessionLieuDateRepository.findBySessionFormationIdSessionFormation(idSessionFormation);

        return sessionLieuDates.stream()
                .map(SessionLieuDateRespDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Fonctionnalité qui récupère les sessions de formation qui ont moins de 6 apprenants d'inscrits
     */
    @Override
    public List<SessionFormationPresentiel> getSessionFormationsAvecMoinsDe6Apprenants() {

        return sessionFOPRepository.findSessionFormationsAvecMoinsDe6Apprenants();

    }


    public SessionFOPResponseDTO getSessionFOPResponseDTO(Long idSessionFormation) throws ChangeSetPersister.NotFoundException {
        SessionFormationPresentiel session = sessionFOPRepository.findById(idSessionFormation)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        SessionFOPResponseDTO dto = new SessionFOPResponseDTO(session);

        List<SessionSalleRespDTO> salles = getSessionsSalleBySessionId(idSessionFormation);
        dto.setSessionsSalle(salles);

        return dto;
    }


    /**
     * Fonctionnalité qui permet de supprimer une session de formation en présentiel
     *
     * @param idSessionFop
     */
    @Override
    public void supprimerSessionFop(long idSessionFop) {

        if (idSessionFop <= 0) {
            throw new IllegalArgumentException("L'identifiant de la session de la formation n'existe pas.");
        }

        if (!sessionFormationRepository.existsById(idSessionFop)) {
            throw new EntityNotFoundException("La session de la formation avec l'ID " + idSessionFop + " n'existe pas.");
        }

        try {
            sessionFormationRepository.deleteById(idSessionFop);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Impossible de supprimer la sessionFormation : il est liée à des sessions de sessionFormations.");
        } catch (Exception e) {
            throw new RuntimeException("Impossible de supprimer la sessionFormation (id = " + idSessionFop + ")" + e.getMessage());
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
    private void validerUniciteNoYoda(SessionFormationPresentiel sessionFop) {

        String noYoda = sessionFop.getNoYoda();

        if (noYoda.length() > 30) {
            throw new RuntimeException("Le numéro Yoda est trop long.");
        }

        if (!noYoda.isBlank()) {
            Optional<SessionFormationPresentiel> existant = sessionFOPRepository.findByNoYoda(noYoda);

            if (existant.isPresent()) {
                // Si on est en création (idSessionFop null) OU
                // Si le numéro appartient à une autre session de formation => erreur
                if (sessionFop.getIdSessionFormation() == null ||
                        !existant.get().getIdSessionFormation().equals(sessionFop.getIdSessionFormation())) {
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
