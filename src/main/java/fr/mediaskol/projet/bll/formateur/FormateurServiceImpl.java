package fr.mediaskol.projet.bll.formateur;

import fr.mediaskol.projet.bll.adresse.AdresseService;
import fr.mediaskol.projet.bo.adresse.Adresse;
import fr.mediaskol.projet.bo.formateur.Formateur;
import fr.mediaskol.projet.bo.formation.Formation;
import fr.mediaskol.projet.bo.formation.TypeFormation;
import fr.mediaskol.projet.bo.salle.Salle;
import fr.mediaskol.projet.dal.adresse.AdresseRepository;
import fr.mediaskol.projet.dal.formateur.FormateurRepository;
import fr.mediaskol.projet.dal.formation.FormationRepository;
import fr.mediaskol.projet.dal.formation.TypeFormationRepository;
import fr.mediaskol.projet.dto.formateur.FormateurInputDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;


import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class FormateurServiceImpl implements FormateurService {

    /**
     * Injection des repository en couplage faible
     */
    private final FormateurRepository formateurRepository;
    private final AdresseRepository adresseRepository;
    private final TypeFormationRepository typeFormationRepository;
    private final FormationRepository formationRepository;

    private final AdresseService adresseService;


    /**
     * Fonctionnalité qui permet de charger tous les formateurs
     */
    @Override
    public List<Formateur> chargerTousFormateurs() {

        return formateurRepository.findAll();
    }

    /**
     * Fonctionnalité qui permet de charger un formateur par son id
     *
     * @param idFormateur
     */
    @Override
    public Formateur chargerFormateurParId(long idFormateur) {

        // Validation de l'identifiant
        if (idFormateur <= 0) {
            throw new RuntimeException("L'identifiant n'existe pas");
        }

        // On retourne un formateur
        final Optional<Formateur> opt = formateurRepository.findById(idFormateur);
        if (opt.isPresent()) {
            return opt.get();
        }

        // Identifiant correspond à aucun enregistrement en base
        throw new RuntimeException("Aucune formateur ne correspond");


    }

    /**
     * Fonctionnalité qui retourne un ou des formateurs selon des critères
     *
     * @param termeRecherche
     * @return formateurRepository.findFormateursBySearchText(termeRecherche)
     */
    @Override
    public List<Formateur> rechercheFormateur(String termeRecherche) {

        String recherche = termeRecherche != null ? termeRecherche.trim().toLowerCase() : "";

        return formateurRepository.findFormateursBySearchText(recherche);
    }

    /**
     * Fonctionnalité qui va ajouter un formateur
     * <ul>
     *     <li>On vérifie si les données du formateur sont existantes, sinon on déclenche une exception.</li>
     *     <li>Vérification de l'ensemble des contraintes avant de créer un formateur.</li>
     *     <li>Si toutes les contraintes sont respectées, on peut créer le formateur.</li>
     *     <li>Si l'adresse existe, elle est créée et associée au formateur</li>
     *     <li>Si le type de formation existe, il est créé et associé au formateur</li>
     * </ul>
     *
     * @param formateur
     * @param adresse
     * @param typesFormationDispensees
     * @param formationDispensees
     */

    @Override
    @Transactional
    public void ajouterFormateur(Formateur formateur, Adresse adresse, Set<TypeFormation> typesFormationDispensees, List<Formation> formationDispensees) {

        if (formateur == null) {
            throw new RuntimeException("Le formateur n'est pas renseigné.");
        }


        validerChaineNonNulle(formateur.getNom(), "Vous devez renseigner le nom du formateur.");
        validerChaineNonNulle(formateur.getPrenom(), "Vous devez renseigner le prénom du formateur.");
        validerEmail(formateur.getEmail(), "Le email doit correspondre au format mail.");


        if (adresse != null) {
            adresseService.validerAdresse(adresse);
            Adresse adresseDB = adresseRepository.save(adresse);
            formateur.setAdresse(adresseDB);
        }

        if (typesFormationDispensees != null && !typesFormationDispensees.isEmpty()) {
            Set<TypeFormation> formationsDB = typesFormationDispensees.stream()
                    .map(tf -> {
                        Long id = tf.getIdTypeFormation();
                        if (id == null) {
                            throw new RuntimeException("Un type de formation transmis n'a pas d'identifiant.");
                        }
                        return typeFormationRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Type de formation inexistant : " + id));
                    })
                    .collect(Collectors.toSet());

            formateur.setTypesFormationDispensees(formationsDB);
        } else {
            formateur.setTypesFormationDispensees(Collections.emptySet());
        }


        if (formationDispensees != null && !formationDispensees.isEmpty()) {
            List<Formation> formationsDB = formationDispensees.stream()
                    .map(tf -> {
                        Long id = tf.getIdFormation();
                        if (id == null) {
                            throw new RuntimeException("Une formation transmise n'a pas d'identifiant.");
                        }
                        return formationRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Formation inexistante : " + id));
                    })
                    .collect(Collectors.toList());

            formateur.setFormationsDispensees(formationsDB);
        } else {
            formateur.setFormationsDispensees(Collections.emptyList());
        }


        try {
            formateurRepository.save(formateur);
        } catch (RuntimeException e) {
            throw new RuntimeException("Impossible de sauvegarder - " + formateur.toString());
        }
    }

    /**
     * Fonctionnalité qui va modifier un formateur
     *
     * @param dto
     * @return
     */
    @Transactional
    @Override
    public Formateur modifierFormateur(FormateurInputDTO dto) {

        // Vérifier si le formateur existe dans la base
        Formateur formateur = formateurRepository.findById(dto.getIdFormateur())
                .orElseThrow(() -> new EntityNotFoundException("Formateur introuvable (id= " + dto.getIdFormateur() + ")"));

        // Valider les champs
        validerChaineNonNulle(dto.getNom(), "Le nom est obligatoire.");
        validerChaineNonNulle(dto.getPrenom(), "Le prénom est obligatoire");
        validerChaineNonNulle(dto.getStatutFormateur(), "Le statut du formateur est obligatoire.");
        validerEmail(dto.getEmail(), "L'adresse mail doit correspondre au format email.");

        // Associer l'adresse
        if (dto.getAdresseId() != null) {
            Adresse adresse = adresseRepository.findById(dto.getAdresseId())
                    .orElseThrow(() -> new EntityNotFoundException("Adresse introuvable (id = " + dto.getAdresseId() + ")"));
            formateur.setAdresse(adresse);
        } else {
            formateur.setAdresse(null); // ou garder l'existante
        }


        // 4. Conversion des IDs de type formations en objets réels
        if (dto.getTypeFormationIds() != null) {
            Set<TypeFormation> typesFormation = dto.getTypeFormationIds().stream()
                    .map(id -> typeFormationRepository.findById(id)
                            .orElseThrow(() -> new EntityNotFoundException("TypeFormation introuvable (id = " + id + ")")))
                    .collect(Collectors.toSet());

            formateur.setTypesFormationDispensees(typesFormation);
        }

        // 5. Conversion des IDs des formations en objets réels
        if (dto.getFormationsIds() != null) {
            List<Formation> formationsDB = dto.getFormationsIds().stream()
                    .map(id -> formationRepository.findById(id)
                            .orElseThrow(() -> new EntityNotFoundException("Formation introuvable (id = " + id + ")")))
                    .collect(Collectors.toList());

            formateur.setFormationsDispensees(formationsDB);
        } else {
            formateur.setFormationsDispensees(Collections.emptyList());
        }

        return formateurRepository.save(formateur);
    }

    /**
     * Fonctionnalité qui supprimer un formateur
     *
     * @param idFormateur
     */
    @Override
    public void supprimerFormateur(long idFormateur) {

        if (idFormateur <= 0) {
            throw new IllegalArgumentException("L'identifiant du formateur n'existe pas.");
        }

        if (!formateurRepository.existsById(idFormateur)) {
            throw new EntityNotFoundException("Le formateur avec l'ID " + idFormateur + " n'existe pas.");
        }

        try {
            formateurRepository.deleteById(idFormateur);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Impossible de supprimer le formateur : il est lié à des sessions formateurs.");
        } catch (Exception e) {
            throw new RuntimeException("Impossible de supprimer le formateur (id = " + idFormateur + ")" + e.getMessage());
        }
    }


    /**
     * Validation si les chaines de caractères ne sont pas nulles ou vides, sinon on déclenche une exception.
     */
    private void validerChaineNonNulle(String chaine, String msgErreur) {

        if (chaine == null || chaine.isBlank()) {
            throw new RuntimeException(msgErreur);
        }
    }

    /**
     * Validation si l'email n'est pas nul et s'il respecte le format attendu
     */
    private void validerEmail(String email, String msgErreur) {

        String regexEmail = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        if (email == null || !email.matches(regexEmail)) {
            throw new RuntimeException(msgErreur);
        }
    }


}
