package fr.mediaskol.projet.bll;

import fr.mediaskol.projet.bo.adresse.Adresse;
import fr.mediaskol.projet.bo.departement.Departement;
import fr.mediaskol.projet.dal.departement.DepartementRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class AdresseServiceImpl implements AdresseService {

    /**
     * Injection des repository en couplage faible
     */
    private final DepartementRepository departementRepository;


    /**
     * Valide et enrichit une {@link Adresse} :
     * <ul>
     *   <li>Vérifie que l’objet n’est pas {@code null}.</li>
     *   <li>Contrôle la longueur du nom de rue, du code postal et de la ville.</li>
     *   <li>Extrait le numéro de département depuis le code postal
     *       via {@link #getNumDepartement(String)}.</li>
     *   <li>Recherche en base le {@link Departement} correspondant
     *       (méthode {@code DepartementRepository#findByNumDepartement(String)}).</li>
     *   <li>Associe ce département à l’adresse si trouvé.</li>
     * </ul>
     *
     * <p>Les validations lèvent une {@link RuntimeException} lorsqu’elles
     * échouent ; la méthode est annotée {@link Transactional},
     * garantissant son exécution dans un contexte transactionnel.</p>
     *
     * @param adresse l’adresse à contrôler ; ne doit pas être {@code null}
     * @throws RuntimeException si l’adresse est nulle ou si un des champs
     *                          (rue, code postal, ville) ne respecte pas les contraintes métier
     * @see #getNumDepartement(String)
     * @see DepartementRepository#findByNumDepartement(String)
     */
    @Override
    @Transactional
    public void validerAdresse(Adresse adresse) {

        if (adresse == null) {
            throw new RuntimeException("L'adresse n'est pas renseignée.");
        }

        validerNomRue(adresse.getRue(), "Le nom de la rue est trop long.");
        validerCodePostal(adresse.getCodePostal(), "Le code postal doit contenir 5 caractères.");
        validerVille(adresse.getVille(), "Le nom de la ville est trop long.");

        if (adresse.getCodePostal() != null && !adresse.getCodePostal().isBlank()) {
            String numDepartement = getNumDepartement(adresse.getCodePostal());
            Departement departement = departementRepository
                    .findByNumDepartement(numDepartement)
                    .orElseThrow(()-> new RuntimeException("Le département est inconnu : " + numDepartement));
            adresse.setDepartement(departement);
        }
    }


    /**
     * On affecte le numéro du département en fonction du code postal.
     * Ne gère pas tous les cas particuliers, comme certaines communes qui ont le code postal
     * de la ville du département voisin.
     *
     * <p>Le département est automatiquement déterminé à partir du code postal en utilisant
     * les règles suivantes :
     *  <ul>
     *   <li>Départements métropolitains : 2 premiers chiffres du code postal</li>
     *   <li>Corse : 2A (20000-20199) ou 2B (20200-20620)</li>
     *   <li>DOM-TOM : 3 premiers chiffres du code postal (97xxx, 98xxx)</li>
     *  </ul>
     * </p>
     */

    @Override
    public String getNumDepartement(String codePostal) {

        if (codePostal == null || codePostal.length() != 5) {
            return "";
        }

        try {
            // Gestion du code postal de la Corse
            int codePostalInt = Integer.parseInt(codePostal);
            if (codePostalInt >= 20000 && codePostalInt <= 20199) {
                return "2A";
            } else if (codePostalInt >= 20200 && codePostalInt <= 20620) {
                return "2B";
            }

            // Gestion des Dom-Tom et Monaco
            String prefixDomTomMonaco = codePostal.substring(0, 3);
            if (prefixDomTomMonaco.startsWith("97") || prefixDomTomMonaco.startsWith("98")) {
                return prefixDomTomMonaco;
            }

            // Sinon, on retourne les deux premiers chiffres du département
            return codePostal.substring(0, 2);

        } catch (NumberFormatException e) {
            throw new NumberFormatException(e.getMessage());
        }
    }

    /**
     * Méthode qui permet de récupérer
     */
    private Departement getOrCreateDepartement(String numDepartement) {
        return departementRepository.findByNumDepartement(numDepartement)
                .orElseGet(() -> {
                    Departement departement = Departement.builder()
                            .numDepartement(numDepartement)
                            .nomDepartement("Non défini") // ou utilisez une logique personnalisée
                            .region("Inconnue")
                            .couleurDepartement("#FFFFFF")
                            .build();
                    return departementRepository.save(departement);
                });
    }


    // Méthodes de contraintes

    /**
     * Validation si le nom de la rue ne dépasse pas 250 caractères.
     */
    private void validerNomRue(String chaine, String msgErreur) {

        if (chaine != null) {
            if (chaine.length() > 250) {
                throw new RuntimeException(msgErreur);
            }
        }
    }

    /**
     * Validation si le code postal est égal à 5 caractères.
     */
    private void validerCodePostal(String chaine, String msgErreur) {

        if (chaine != null) {
            if (chaine.length() != 5) {
                throw new RuntimeException(msgErreur);
            }
        }
    }

    /**
     * Validation si le nom de la ville ne dépasse pas 200 caractères.
     */
    private void validerVille(String chaine, String msgErreur) {

        if (chaine != null) {
            if (chaine.length() > 250) {
                throw new RuntimeException(msgErreur);
            }
        }
    }


}