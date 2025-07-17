package fr.mediaskol.projet.bll;

import fr.mediaskol.projet.bo.adresse.Adresse;
import fr.mediaskol.projet.bo.apprenant.Apprenant;
import fr.mediaskol.projet.bo.formation.TypeFormation;
import fr.mediaskol.projet.dal.apprenant.ApprenantRepository;
import fr.mediaskol.projet.dal.apprenant.ApprenantSpecifications;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@AllArgsConstructor
@Service
public class ApprenantServiceImpl implements ApprenantService {

    // Injection des repository en couplage faible
    ApprenantRepository apprenantRepository;

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
     * Retourne une liste d'apprenants en fonction de la saisie utilisateur.
     *
     * @param nom
     * @param email
     * @param dateNaissance
     * @param numDepartement
     * @param ville
     * @return apprenantRepository.findAll(specApprenant)
     */
    @Override
    public List<Apprenant> rechercheApprenants(String nom, String email, LocalDate dateNaissance, Long numDepartement, String ville) {

        Specification<Apprenant> specApprenant = ApprenantSpecifications
                .nomContains(nom)
                .and(ApprenantSpecifications.emailContains(email))
                .and(ApprenantSpecifications.dateNaissanceContains(dateNaissance))
                .and(ApprenantSpecifications.numDepartement(numDepartement))
                .and(ApprenantSpecifications.villeContains(ville));

        return apprenantRepository.findAll(specApprenant);
    }

    /**
     * Création d'un nouvel apprenant
     * <ul>
     *     <li>On vérifie si les données de l'apprenant sont existantes, sinon on déclenche une exception.</li>
     *     <li>Vérification de l'ensemble des contraintes avant de créer un apprenant.</li>
     *     <li>Si toutes les contraintes sont respectées, on peut créer l'apprenant.</li>
     * </ul>
     *
     * @param apprenant
     */
    @Override
    public void ajouterApprenant(Apprenant apprenant) {

        if (apprenant == null) {
            throw new RuntimeException("L'apprenant n'est pas renseigné");
        }

        validerChaineNonNulle(apprenant.getNom(), "Vous devez renseigner le nom de l'apprennant.");
        validerChaineNonNulle(apprenant.getPrenom(), "Vous devez renseigner le prénom de l'apprenant.");
        validerEmail(apprenant.getEmail(), "Vous devez renseigner l'email de l'apprenant.");
        validerDateNaissance(apprenant.getDateNaissance(), "Vous devez renseigner la date de naissance de l'apprenant.");
        validerEnumNonNulle(apprenant.getStatutNumPasseport(), "Vous devez indiquer un statut pour le passeport de l'apprenant.");

        try {
            apprenantRepository.save(apprenant);
        } catch (RuntimeException e) {
            throw new RuntimeException("Impossible de sauvegarder - " + apprenant.toString());
        }


    }

    /**
     * Associe une adresse à un apprenant existant et enregistre la modification en base de données.
     * <p>
     * Si l'apprenant n'existait pas déjà, une exception est levée.
     * L'adresse précédente de l'apprenant (le cas échéant) est remplacée par la nouvelle adresse passée en paramètre.
     * </p>
     *
     * @param apprenant l'apprenant auquel on associe une nouvelle adresse (doit être non nul).
     * @param adresse   l'adresse à associer à l'apprenant (doit être non nulle).
     * @throws RuntimeException si l'apprenant ou l'adresse sont nuls
     */
    @Override
    @Transactional
    public void ajouterAdresseApprenant(Apprenant apprenant, Adresse adresse) {

        if (apprenant == null || adresse == null) {
            throw new RuntimeException("L'apprenant ou l'adresse n'est pas renseigné.");
        }

        apprenant.setAdresse(adresse);
        apprenantRepository.save(apprenant);

    }

    /**
     * Ajoute un type de formation à la collection des types de formations d'un apprenant existant, puis enregistre
     * la modification en base de données.
     * <ul>
     *     <li>On vérifie si les données de l'apprenant sont existantes, sinon on déclenche une exception.</li>
     *     <li>Vérification de l'ensemble des contraintes avant de créer un apprenant.</li>
     *     <li>Si toutes les contraintes sont respectées, on peut créer l'apprenant.</li>
     *     <li>On ajoute le(s) type(s) de formation s'il est (sont) renseigné(s).</li>
     * </ul>
     *
     * @param apprenant,     l'apprenant auquel on ajoute un type de formation (doit être non nul).
     * @param typeFormation, le type de formation à associer (doit être non nul).
     * @throws RuntimeException si l'apprenant ou le type de formation sont nuls.
     */
    @Override
    @Transactional
    public void ajouterTypeFormationApprenant(Apprenant apprenant, TypeFormation typeFormation) {

        if (apprenant == null || typeFormation == null) {
            throw new RuntimeException("L'apprenant ou le type de formation n'est pas renseigné.");
        }

        if (apprenant.getTypeFormationSuivie() == null) {
            apprenant.setTypeFormationSuivie(new HashSet<>());
        }

        apprenant.getTypeFormationSuivie().add(typeFormation);
        apprenantRepository.save(apprenant);
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
     * Validation si les énumérations ne sont pas nulles ou vides, sinon on déclenche une exception.
     */
    private void validerEnumNonNulle(Enum<?> statut, String msgErreur) {
        if (statut == null) {
            throw new RuntimeException(msgErreur);
        }
    }
}
