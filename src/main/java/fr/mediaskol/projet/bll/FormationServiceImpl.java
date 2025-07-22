package fr.mediaskol.projet.bll;

import fr.mediaskol.projet.bo.formation.Formation;
import fr.mediaskol.projet.bo.formation.TypeFormation;
import fr.mediaskol.projet.dal.adresse.AdresseRepository;
import fr.mediaskol.projet.dal.formation.FormationRepository;
import fr.mediaskol.projet.dal.formation.TypeFormationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class FormationServiceImpl implements FormationService {

    /**
     * Injection des repository en couplage faible
     */
    private final FormationRepository formationRepository;



    /***
     * Fonctionnalité qui permet de charger toutes les formations
     */
    @Override
    public List<Formation> chargerToutesFormations() {
        return formationRepository.findAll();
    }

    /**
     * Fonctionnalité qui permet d'ajouter une formation
     *
     * @param formation
     * @param typeFormation Validation des données de la formation
     */
    @Override
    public void ajouterFormation(Formation formation, TypeFormation typeFormation) {

        if (formation == null) {
            throw new RuntimeException("La formation n'est pas renseignée.");
        }

        validerTheme(formation.getThemeFormation());
        validerLibelle(formation.getLibelleFormation());
        validerTypeFormation(formation.getTypeFormation().getIdTypeFormation());

      try{
          formationRepository.save(formation);
      } catch (RuntimeException e) {
          throw new RuntimeException("Impossible de sauvegarder - " + formation.toString());
      }

    }

    /**
     * Fonctionnalité qui permet de supprimer une formation
     *
     * @param idFormation
     */
    @Override
    public void supprimerFormation(long idFormation) {

        if(idFormation <= 0){
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
     * Validation le libellé de la formation n'est pas nul ou vide, sinon on déclenche une exception.
     * Validation du thème en fonction de sa longueur comprise entre 3 et 300 caractères.
     */
    private void validerTypeFormation(Long idTypeFormation) {

        if (idTypeFormation == null || idTypeFormation <= 0) {
            throw new RuntimeException("Le type de formation doit être saisi.");
        }
    }


}
