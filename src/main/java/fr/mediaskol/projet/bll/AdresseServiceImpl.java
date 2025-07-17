package fr.mediaskol.projet.bll;

import fr.mediaskol.projet.bo.adresse.Adresse;
import fr.mediaskol.projet.dal.adresse.AdresseRepository;
import fr.mediaskol.projet.dal.apprenant.ApprenantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AdresseServiceImpl implements AdresseService {
    /**
     * Injection des repository en couplage faible
     */
    private final AdresseRepository adresseRepository;

    @Override
    public static void validerAdresse(Adresse adresse) {


    }

    /**
     *     On affecte le numéro du département en fonction du code postal
     *     Ne gère pas tous les cas particuliers, comme certaines communes qui ont le code postal
     *     de la ville du département voisin.
     */

    @Override
    public String getNumDepartement(String codePostal) {

        if (codePostal == null || codePostal.isEmpty()) {
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
        } catch (NumberFormatException e) {
            // si le code postal n'est pas numérique, on passe aux autres cas
        }

        // Gestion des Dom-Tom et Monaco
        if (codePostal.length() >= 3) {
            String prefixDomTomMonaco = codePostal.substring(0, 3);
            if (prefixDomTomMonaco.startsWith("97") || prefixDomTomMonaco.startsWith("98")) {
                return prefixDomTomMonaco;
            }

        }

        // Sinon, on retourne les deux premiers chiffres du département
        return codePostal.substring(0, 2);
    }


}