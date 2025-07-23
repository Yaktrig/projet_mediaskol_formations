package fr.mediaskol.projet.bll.formation;

import fr.mediaskol.projet.bo.formation.Formation;
import fr.mediaskol.projet.bo.formation.TypeFormation;
import fr.mediaskol.projet.dal.formation.FormationRepository;
import fr.mediaskol.projet.dal.formation.TypeFormationRepository;
import fr.mediaskol.projet.dto.formation.FormationInputDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class FormationServiceImpl implements FormationService {

    /**
     * Injection des repository en couplage faible.
     */
    private final FormationRepository formationRepository;
    private final TypeFormationRepository typeFormationRepository;


    /**
     * Fonctionnalité qui permet de charger toutes les formations
     */
    @Override
    public List<Formation> chargerToutesFormations() {
        return formationRepository.findAll();
    }

    /**
     * Fonctionnalité qui permet d'ajouter une formation     *
     *
     * @param formation
     * @return
     */
    @Override
    @Transactional
    public Formation ajouterFormation(Formation formation) {

        if (formation == null) {
            throw new RuntimeException("La formation n'est pas renseignée.");
        }

        validerTheme(formation.getThemeFormation());
        validerUniciteThemeTypeForm(formation.getThemeFormation(), formation.getTypeFormation().getIdTypeFormation(), null);
        validerLibelle(formation.getLibelleFormation());
        validerTypeFormation(formation.getTypeFormation().getIdTypeFormation());

        try {
            return formationRepository.save(formation);
        } catch (RuntimeException e) {
            throw new RuntimeException("Impossible de sauvegarder - " + formation.toString());
        }

    }

    /**
     * Modification d'une formation
     */
    @Override
    @Transactional
    public Formation modifierFormation(FormationInputDTO dto) {

        // 1. Vérifier que la formation à modifier existe
        Formation formationExistante = formationRepository.findById(dto.getIdFormation())
                .orElseThrow(() -> new EntityNotFoundException("Formation introuvable"));

        // 2. Vérifier que le type de formation existe
        TypeFormation typeFormation = typeFormationRepository.findById(dto.getTypeFormationId())
                .orElseThrow(() -> new EntityNotFoundException("TypeFormation introuvable"));

        // 3. Appliquer les modifications aux champs autorisés
        formationExistante.setThemeFormation(dto.getThemeFormation());
        formationExistante.setLibelleFormation(dto.getLibelleFormation());
        formationExistante.setTypeFormation(typeFormation);

        // 4. Valider si nécessaire
        validerTheme(dto.getThemeFormation());
        validerTypeFormation(dto.getTypeFormationId());
        validerUniciteThemeTypeForm(dto.getThemeFormation(), dto.getTypeFormationId(), dto.getIdFormation());
        validerTheme(dto.getThemeFormation());

        // 5. Sauvegarde finale
        return formationRepository.save(formationExistante);
    }


    /**
     * Fonctionnalité qui permet de supprimer une formation
     *
     * @param idFormation
     */
    @Override
    @Transactional
    public void supprimerFormation(long idFormation) {

        if (idFormation <= 0) {
            throw new IllegalArgumentException("L'identifiant de la formation n'existe pas.");
        }

        if (!formationRepository.existsById(idFormation)) {
            throw new EntityNotFoundException("La formation avec l'ID " + idFormation + " n'existe pas.");
        }

        try {
            formationRepository.deleteById(idFormation);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Impossible de supprimer la formation : il est liée à des sessions de formations.");
        } catch (Exception e) {
            throw new RuntimeException("Impossible de supprimer la formation (id = " + idFormation + ")" + e.getMessage());
        }
    }



    // Méthodes de contrôle de contraintes


    /**
     * Validation le thème de la formation n'est pas nul ou vide, sinon on déclenche une exception.
     * Validation du thème de la formation en fonction de sa longueur comprise entre 3 et 20 caractères.
     */
    private void validerTheme(String themeFormation) {

        if (themeFormation == null || themeFormation.isBlank()) {
            throw new RuntimeException("Le champ du thème doit être saisi.");
        }

        if (themeFormation.length() < 3 || themeFormation.length() > 20) {
            throw new RuntimeException("Le champ du thème doit être compris entre 3 et 20 caractères.");
        }

    }

    /**
     * Valider l'unicité du thème de la formation et de son type de formation.
     * Il ne doit pas y avoir deux thèmes en distanciel, mais on peut avoir un thème en présentiel et le
     * même en distanciel.
     *
     * @param themeFormation
     */
    private void validerUniciteThemeTypeForm(String themeFormation, Long idTypeFormation, Long idFormationCourante) {

        if (themeFormation == null || themeFormation.isBlank()) {
            throw new RuntimeException("Le champ du thème doit être saisi.");
        }

        if (idTypeFormation == null) {
            throw new RuntimeException("le type de formation doit être renseigné.");
        }

        // Vérifier l'unicité seulement si le thème est renseigné
        Optional<Formation> formationExistante = formationRepository.findByThemeFormationAndTypeFormation_IdTypeFormation(themeFormation, idTypeFormation);

        if (formationExistante.isPresent()) {
            if (idFormationCourante == null || !formationExistante.get().getIdFormation().equals(idFormationCourante)) {
                throw new RuntimeException("Une formation avec le même thème et le même type existe déjà.");
            }
        }
    }


    /**
     * Validation le libellé de la formation n'est pas nul ou vide, sinon on déclenche une exception.
     * Validation du thème en fonction de sa longueur comprise entre 3 et 300 caractères.
     */
    private void validerLibelle(String libelleFormation) {

        if (libelleFormation == null || libelleFormation.isBlank()) {
            throw new RuntimeException("Le champ du libellé doit être saisi.");
        }

        if (libelleFormation.length() < 3 || libelleFormation.length() > 300) {
            throw new RuntimeException("Le champ du libellé doit être compris entre 3 et 300 caractères.");
        }
    }

    /**
     * Validation le type de la formation n'est pas nul ou vide, sinon on déclenche une exception.
     */
    private void validerTypeFormation(Long idTypeFormation) {

        if (idTypeFormation == null || idTypeFormation <= 0) {
            throw new RuntimeException("Le type de formation doit être saisi.");
        }
    }


}