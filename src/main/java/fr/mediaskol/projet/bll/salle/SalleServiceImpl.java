package fr.mediaskol.projet.bll.salle;


import fr.mediaskol.projet.bll.adresse.AdresseService;
import fr.mediaskol.projet.bo.adresse.Adresse;
import fr.mediaskol.projet.bo.salle.Salle;
import fr.mediaskol.projet.dal.adresse.AdresseRepository;
import fr.mediaskol.projet.dal.salle.SalleRepository;
import fr.mediaskol.projet.dto.salle.SalleInputDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class SalleServiceImpl implements SalleService{

    /**
     * Injection des repository en couplage faible
     */
    private final SalleRepository salleRepository;

    private final AdresseRepository adresseRepository;

    private final AdresseService adresseService;

    /**
     * Appel de la méthode findAll() du repository qui retourne toutes les salles
     *
     * @return salleRepository.findAll()
     */
    @Override
    public List<Salle> chargerToutesSalles() {
        return salleRepository.findAll();
    }

    /**
     * Fonctionnalité qui retourne une ou des salles selon des critères
     * Todo à compléter
     * @param termeRecherche
     */
    @Override
    public List<Salle> rechercheSalle(String termeRecherche) {
        return List.of();
    }

    /**
     * Fonctionnalité qui va ajouter une salle
     *
     * @param salle
     * @param adresse
     */
    @Override
    @Transactional
    public void ajouterSalle(Salle salle, Adresse adresse) {

        if (salle == null) {
            throw new RuntimeException("La salle n'est pas renseignée");
        }

        validerChaineNonNulle(salle.getNomSalle(),"La salle n'est pas renseignée.");

        if (adresse != null) {
            adresseService.validerAdresse(adresse);
            Adresse adresseDB = adresseRepository.save(adresse);
            salle.setAdresse(adresseDB);
        }

        try {
            salleRepository.save(salle);
        } catch (RuntimeException e) {
            throw new RuntimeException("Impossible de sauvegarder - " + salle.toString());
        }


    }

    /**
     * Fonctionnalité qui va modifier une salle
     *
     * @param dto
     * @return
     */
    @Override
    @Transactional
    public Salle modifierSalle(SalleInputDTO dto) {

        // 1. Vérifier que la salle à modifier existe
        Salle salle = salleRepository.findById(dto.getIdSalle())
                .orElseThrow(() -> new EntityNotFoundException("Salle introuvable (id= "+ dto.getIdSalle() + ")"));

        // 2. Valider les champs
        validerChaineNonNulle(dto.getNomSalle(), "Le nom de la salle est obligatoire.");

        // Associer l'adresse
        if (dto.getAdresseId() != null) {
            Adresse adresse = adresseRepository.findById(dto.getAdresseId())
                    .orElseThrow(() -> new EntityNotFoundException("Adresse introuvable (id = " + dto.getAdresseId() + ")"));
            salle.setAdresse(adresse);
        } else {
            salle.setAdresse(null); // ou garder l'existante
        }

        return salleRepository.save(salle);
    }

    /**
     * Fonctionnalité qui supprimer une salle
     *
     * @param idSalle
     */
    @Override
    public void supprimerSalle(long idSalle) {

        if (idSalle <= 0) {
            throw new IllegalArgumentException("L'identifiant de la salle n'existe pas.");
        }

        if (!salleRepository.existsById(idSalle)) {
            throw new EntityNotFoundException("La salle avec l'ID " + idSalle + " n'existe pas.");
        }

        try {
            salleRepository.deleteById(idSalle);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Impossible de supprimer la salle : il est lié à des sessions salles.");
        } catch (Exception e) {
            throw new RuntimeException("Impossible de supprimer la salle (id = " + idSalle + ")" + e.getMessage());
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

}
