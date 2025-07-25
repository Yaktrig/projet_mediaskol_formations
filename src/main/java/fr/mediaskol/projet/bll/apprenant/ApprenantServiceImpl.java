package fr.mediaskol.projet.bll.apprenant;

import fr.mediaskol.projet.bll.adresse.AdresseService;
import fr.mediaskol.projet.bo.adresse.Adresse;
import fr.mediaskol.projet.bo.apprenant.Apprenant;
import fr.mediaskol.projet.bo.apprenant.SessionApprenant;
import fr.mediaskol.projet.bo.formation.TypeFormation;
import fr.mediaskol.projet.dal.adresse.AdresseRepository;
import fr.mediaskol.projet.dal.apprenant.ApprenantRepository;
import fr.mediaskol.projet.dal.formation.TypeFormationRepository;
import fr.mediaskol.projet.dto.apprenant.ApprenantInputDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ApprenantServiceImpl implements ApprenantService {


    /**
     * Injection des repository en couplage faible
     */
    private final ApprenantRepository apprenantRepository;
    private final AdresseRepository adresseRepository;
    private final TypeFormationRepository typeFormationRepository;

    private final AdresseService adresseService;

    /**
     * Appel de la méthode findAll() du repository qui retourne tous les apprenants
     *
     * @return apprenantRepository.findAll()
     */
    @Override
    public List<Apprenant> chargerTousApprenants() {
        return apprenantRepository.findAll();
    }



    /**
     * Fonctionnalité qui permet de charger un apprenant par son id
     *
     * @param idApprenant
     */
    @Override
    public Apprenant chargerApprenantParId(long idApprenant) {

        // Validation de l'identifiant
        if (idApprenant <= 0) {
            throw new RuntimeException("L'identifiant n'existe pas");
        }

        // On retourne un apprenant
        final Optional<Apprenant> opt = apprenantRepository.findById(idApprenant);
        if (opt.isPresent()) {
            return opt.get();
        }

        // Identifiant correspond à aucun enregistrement en base
        throw new RuntimeException("Aucun apprenant ne correspond");
    }

    /**
     * Retourne une liste d'apprenants en fonction de la saisie utilisateur.
     *
     * @param termeRecherche
     * @return apprenantRepository.findApprenantsBySearchText(termeRecherche)
     */
    @Override
    public List<Apprenant> rechercheApprenants(String termeRecherche) {

        String recherche = termeRecherche != null ? termeRecherche.trim().toLowerCase() : "";

        // Vérification si le terme est une date au format jjmmaaaa
        LocalDate dateNaissance = null;
        if (recherche.matches("^[0-3][0-9][0-1][0-9][0-9]{4}$")) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
                dateNaissance = LocalDate.parse(recherche, formatter);

                // si c'est le cas, on cherche en priorité sur la date
                return apprenantRepository.findByDateNaissance(dateNaissance);
            } catch (DateTimeParseException e) {

            }
        }
        return apprenantRepository.findApprenantsBySearchText(termeRecherche);
    }


    /**
     * Création d'un nouvel apprenant
     * <ul>
     *     <li>On vérifie si les données de l'apprenant sont existantes, sinon on déclenche une exception.</li>
     *     <li>Vérification de l'ensemble des contraintes avant de créer un apprenant.</li>
     *     <li>Si toutes les contraintes sont respectées, on peut créer l'apprenant.</li>
     *     <li>Si l'adresse existe, elle est créée et associée à l'apprenant</li>
     *     <li>Si le type de formation existe, il est créé et associé à l'apprenant</li>
     * </ul>
     *
     * @param apprenant
     * @param adresse
     * @param typesFormationSuivies
     */
    @Override
    @Transactional
    public void ajouterApprenant(Apprenant apprenant, Adresse adresse, Set<TypeFormation> typesFormationSuivies) {

        if (apprenant == null) {
            throw new RuntimeException("L'apprenant n'est pas renseigné");
        }

        validerChaineNonNulle(apprenant.getNom(), "Vous devez renseigner le nom de l'apprennant.");
        validerChaineNonNulle(apprenant.getPrenom(), "Vous devez renseigner le prénom de l'apprenant.");
        validerEmail(apprenant.getEmail(), "Vous devez renseigner l'email de l'apprenant.");
        validerDateNaissance(apprenant.getDateNaissance(), "Vous devez renseigner la date de naissance de l'apprenant.");
        validerUnicitePasseport(apprenant, "Le numéro du passeport existe déjà.");
        validerEnumNonNulle(apprenant.getStatutNumPasseport(), "Vous devez indiquer un statut pour le passeport de l'apprenant.");

        if (adresse != null) {
            adresseService.validerAdresse(adresse);
            Adresse adresseDB = adresseRepository.save(adresse);
            apprenant.setAdresse(adresseDB);
        }

        if (typesFormationSuivies != null && !typesFormationSuivies.isEmpty()) {
            Set<TypeFormation> formationsDB = typesFormationSuivies.stream()
                    .map(tf -> {
                        Long id = tf.getIdTypeFormation();
                        if (id == null) {
                            throw new RuntimeException("Un type de formation transmis n'a pas d'identifiant.");
                        }
                        return typeFormationRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Type de formation inexistant : " + id));
                    })
                    .collect(Collectors.toSet());

            apprenant.setTypesFormationSuivies(formationsDB);
        } else {
            apprenant.setTypesFormationSuivies(Collections.emptySet());
        }


        try {
            apprenantRepository.save(apprenant);
        } catch (RuntimeException e) {
            throw new RuntimeException("Impossible de sauvegarder - " + apprenant.toString());
        }
    }

    /**
     * Modification d'un apprenant
     *
     * @param dto
     * @return
     */
    @Transactional
    @Override
    public Apprenant modifierApprenant(ApprenantInputDTO dto) {

        // 1. Vérifier que l'apprenant à modifier existe
        Apprenant apprenant = apprenantRepository.findById(dto.getIdApprenant())
                .orElseThrow(() -> new EntityNotFoundException("Apprenant introuvable (id= " + dto.getIdApprenant() + ")"));


        // 2. Valider les champs (chaînes, email, unicité...)
        validerChaineNonNulle(dto.getNom(), "Le nom est obligatoire.");
        validerEmail(dto.getEmail(), "L'adresse mail doit correspondre au format email.");

        // 3. Appliquer les modifications sur les champs autorisés
        apprenant.setNom(dto.getNom());
        apprenant.setEmail(dto.getEmail());
        apprenant.setNumPortable(dto.getNumPortable());
        apprenant.setApprenantActif(dto.isApprenantActif());
        apprenant.setNumPasseport(dto.getNumPasseport());
        apprenant.setStatutNumPasseport(dto.getStatutNumPasseport());
        apprenant.setCommentaireApprenant(dto.getCommentaireApprenant());

        // Associer l'adresse
        if (dto.getAdresseId() != null) {
            Adresse adresse = adresseRepository.findById(dto.getAdresseId())
                    .orElseThrow(() -> new EntityNotFoundException("Adresse introuvable (id = " + dto.getAdresseId() + ")"));
            apprenant.setAdresse(adresse);
        } else {
            apprenant.setAdresse(null); // ou garder l'existante
        }


        // 4. Conversion des IDs de type formations en objets réels
        if (dto.getTypeFormationIds() != null) {
            Set<TypeFormation> typesFormation = dto.getTypeFormationIds().stream()
                    .map(id -> typeFormationRepository.findById(id)
                            .orElseThrow(() -> new EntityNotFoundException("TypeFormation introuvable (id = " + id + ")")))
                    .collect(Collectors.toSet());

            apprenant.setTypesFormationSuivies(typesFormation);
        }

        // 5. Validation d'unicité du numéro de passeport (à l'exclusion de l'apprenant actuel)
        validerUnicitePasseport(apprenant, "Le numéro de passeport doit être unique.");

        // 6. Sauvegarde finale
        return apprenantRepository.save(apprenant);
    }


    /**
     * Suppression d'un apprenant de la base de données
     *
     * <p>Cette méthode effectue les validations suivantes avant la suppression :
     * <ul>
     *   <li>Vérifie que l'identifiant est valide (positif)</li>
     *   <li>Contrôle l'existence de l'apprenant en base</li>
     * </ul>
     *
     * <p><strong>Attention :</strong> Cette méthode peut échouer si l'apprenant est lié à d'autres entités,
     * notamment à l'entité {@code SessionApprenant}. Dans ce cas, une exception sera levée pour préserver
     * l'intégrité référentielle de la base de données.</p>
     *
     * <p>Pour supprimer un apprenant lié à des sessions, il est recommandé de :
     * <ul>
     *   <li>Supprimer d'abord les relations dans {@code SessionApprenant}</li>
     *   <li>Ou configurer une suppression en cascade appropriée</li>
     *   <li>Ou implémenter une suppression logique (soft delete)</li>
     * </ul>
     *
     * @param idApprenant l'identifiant de l'apprenant à supprimer, doit être positif
     * @throws RuntimeException si l'identifiant est invalide (négatif ou zéro)
     * @throws RuntimeException si l'apprenant n'existe pas en base de données
     * @throws RuntimeException si la suppression échoue en raison de contraintes d'intégrité
     *                          référentielle (notamment avec l'entité SessionApprenant)
     * @see SessionApprenant
     * @since 1.0
     */
    @Override
    public void supprimerApprenant(long idApprenant) {

        if (idApprenant <= 0) {
            throw new IllegalArgumentException("L'identifiant de l'apprenant n'existe pas.");
        }

        if (!apprenantRepository.existsById(idApprenant)) {
            throw new EntityNotFoundException("L'apprenant avec l'ID " + idApprenant + " n'existe pas.");
        }

        try {
            apprenantRepository.deleteById(idApprenant);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Impossible de supprimer l'apprenant : il est lié à des sessions apprenants.");
        } catch (Exception e) {
            throw new RuntimeException("Impossible de supprimer l'apprenant (id = " + idApprenant + ")" + e.getMessage());
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
     * Validation si l'email n'est pas nul et s'il respecte le format attendu
     */
    private void validerEmail(String email, String msgErreur) {

        String regexEmail = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        if (email == null || !email.matches(regexEmail)) {
            throw new RuntimeException(msgErreur);
        }
    }

    /**
     * Validation si les dates ne sont pas nulles ou non valides, sinon on déclenche une exception.
     * On interdit une date de naissance dans le futur et avant 1900.
     */
    private void validerDateNaissance(LocalDate date, String msgErreur) {

        if (date == null) {
            throw new RuntimeException(msgErreur);
        }

        if (date.isAfter(LocalDate.now())) {
            throw new RuntimeException("La date de naissance ne peut pas être dans le futur");
        }

        if (date.isBefore(LocalDate.of(1900, 1, 1))) {
            throw new RuntimeException("La date de naissance est trop ancienne");
        }


    }

    /**
     * Valide l'unicité du numéro de passeport dans la base de données, s'il est renseigné.
     * Exclut l'apprenant actuel lors de la vérification si celui-ci existe déjà (cas modification).
     *
     * @param apprenant l'apprenant à valider (contient le numéro de passeport et l'ID)
     * @param msgErreur le message d'erreur à afficher en cas de doublon
     * @throws RuntimeException si le numéro de passeport existe déjà pour un autre apprenant
     */
    public void validerUnicitePasseport(Apprenant apprenant, String msgErreur) {
        String numPasseport = apprenant.getNumPasseport();

        if (numPasseport != null && !numPasseport.isBlank()) {
            // Recherche d'un apprenant ayant déjà ce même numéro
            Optional<Apprenant> existant = apprenantRepository.findByNumPasseport(numPasseport);

            if (existant.isPresent()) {
                // Si on est en création (idPersonne null) OU
                // Si le numéro appartient à un autre apprenant => erreur
                if (apprenant.getIdPersonne() == null ||
                        !existant.get().getIdPersonne().equals(apprenant.getIdPersonne())) {
                    throw new RuntimeException(msgErreur);
                }
            }
        }
    }


    /**
     * Validation si les énumérations ne sont pas nulles ou vides, sinon on déclenche une exception.
     */
    private void validerEnumNonNulle(Enum<?> statut, String msgErreur) {
        if (statut == null) {
            throw new RuntimeException(msgErreur);
        }
    }
}
