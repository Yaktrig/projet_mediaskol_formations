package fr.mediaskol.projet.bll.sessionFormation;

import fr.mediaskol.projet.bo.sessionFormation.FinSessionFormation;
import fr.mediaskol.projet.bo.sessionFormationDistanciel.SessionFormationDistanciel;
import fr.mediaskol.projet.dal.sessionFormation.FinSessionFormationRepository;
import fr.mediaskol.projet.dto.sessionFormation.FinSessionFormationInputDTO;
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
public class FinSessionFormationServiceImpl implements FinSessionFormationService{

    /**
     * Injection des repository en couplage faible.
     */
    private final FinSessionFormationRepository finSessionFormationRepository;

    /***
     * Fonctionnalité qui permet de charger toutes les fins de sessions de formations
     */
    @Override
    public List<FinSessionFormation> chargerToutesFinSessionFormations() {

        return finSessionFormationRepository.findAll();
    }

    /**
     * Fonctionnalité qui permet de charger une fin de session de formation par son id
     *
     * @param idFinSessionFormation
     */
    @Override
    public FinSessionFormation chargerFinSessionFormationParId(long idFinSessionFormation) {

        // Validation de l'identifiant
        if (idFinSessionFormation <= 0) {
            throw new RuntimeException("L'identifiant n'existe pas");
        }

        // On retourne une session de formation à distance
        final Optional<FinSessionFormation> opt = finSessionFormationRepository.findById(idFinSessionFormation);
        if (opt.isPresent()) {
            return opt.get();
        }

        // Identifiant correspond à aucun enregistrement en base
        throw new RuntimeException("Aucune fin de session de formation ne correspond");
    }

    /**
     * Fonctionnalité qui permet d'ajouter une fin de session de formation
     *
     * @param finSessionFormation
     */
    @Override
    @Transactional
    public FinSessionFormation ajouterFinSessionFormation(FinSessionFormation finSessionFormation) {

        if (finSessionFormation == null) {
            throw new RuntimeException("La session de formation à distance n'est pas renseignée.");
        }


        if(finSessionFormation.getStatutYodaFinSessionFormation() != null){
            validerStatutYoda(finSessionFormation);
        }

        if(finSessionFormation.getDateLimiteYodaFinSessionFormation() != null){
            validerDate(finSessionFormation.getDateLimiteYodaFinSessionFormation());
        }

        if(finSessionFormation.getCommentaireFinSessionFormation() != null){
            validerCommentaire(finSessionFormation);
        }


        try {
            return finSessionFormationRepository.save(finSessionFormation);
        } catch (RuntimeException e) {
            throw new RuntimeException("Impossible de sauvegarder - " + finSessionFormation.getIdFinSessionFormation());
        }
    }

    /**
     * Fonctionnalité qui permet de modifier une fin de session de formation
     *
     * @param dto
     * @return
     */
    @Override
    @Transactional
    public FinSessionFormation modifierFinSessionFormation(FinSessionFormationInputDTO dto) {

        // 1. Vérifier que la fin de session de formation à modifier existe
        FinSessionFormation finSessionFormation = finSessionFormationRepository.findById(dto.getIdFinSessionFormation())
                .orElseThrow(() -> new EntityNotFoundException("Fin de session de formation introuvable"));


        // 2. Valider
        if(finSessionFormation.getStatutYodaFinSessionFormation() != null){
            validerStatutYoda(finSessionFormation);
        }

        if(finSessionFormation.getDateLimiteYodaFinSessionFormation() != null){
            validerDate(finSessionFormation.getDateLimiteYodaFinSessionFormation());
        }

        if(finSessionFormation.getCommentaireFinSessionFormation() != null){
            validerCommentaire(finSessionFormation);
        }

        // 3. Appliquer les modifications aux champs autorisés
        finSessionFormation.setStatutYodaFinSessionFormation(dto.getStatutYodaFinSessionFormation());
        finSessionFormation.setDateLimiteYodaFinSessionFormation(dto.getDateLimiteYodaFinSessionFormation());
        finSessionFormation.setCommentaireFinSessionFormation(dto.getCommentaireFinSessionFormation());
        finSessionFormation.setStatutFinSessionFormation(dto.getStatutFinSessionFormation());

        // 4. Sauvegarde finale
        return finSessionFormationRepository.save(finSessionFormation);
    }

    /**
     * Fonctionnalité qui permet de supprimer une fin de session de formation
     *
     * @param idFinSessionFormation
     */
    @Override
    public void supprimerFinSessionFormation(long idFinSessionFormation) {

        if (idFinSessionFormation <= 0) {
            throw new IllegalArgumentException("L'identifiant de la fin de session de la formation n'existe pas.");
        }

        if (!finSessionFormationRepository.existsById(idFinSessionFormation)) {
            throw new EntityNotFoundException("La fin de la session de la formation avec l'ID " + idFinSessionFormation
                    + " n'existe pas.");
        }

        try {
            finSessionFormationRepository.deleteById(idFinSessionFormation);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Impossible de supprimer la fin de la session de formation.");
        } catch (Exception e) {
            throw new RuntimeException("Impossible de supprimer la fin de la session de formation" +
                    " (id = " + idFinSessionFormation + ")" + e.getMessage());
        }

    }

    // Méthodes de contrôle de contraintes


    /**
     * Validation de la taille du statut yoda d'une fin de session de formation - max 5 caractères
     */
    private void validerStatutYoda(FinSessionFormation finSessionFormation) {

        String statutYoda = finSessionFormation.getStatutYodaFinSessionFormation();

        if (statutYoda.length() > 5) {
            throw new RuntimeException("Le champ du statut Yoda ne doit pas dépasser 5 caractères.");
        }
    }


    /**
     * Validation de la taille du comment d'une fin de session de formation - max 2000 caractères
     */
    private void validerCommentaire(FinSessionFormation finSessionFormation) {

        String commentaireFinSession = finSessionFormation.getCommentaireFinSessionFormation();

        if (commentaireFinSession.length() > 2000) {
            throw new RuntimeException("Le champ du commentaire ne doit pas dépasser 2000 caractères.");
        }
    }

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

}
