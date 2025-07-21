package fr.mediaskol.projet.controller.apprenant;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mediaskolFormation")
public class MediaskolController {

    /**
     * WebService qui retourne une chaîne de caractères
     * <p>
     *     Toutes les url qui s'appellent mediaskolFormation vont être redirigées vers la classe et la méthode
     * </p>
     */
    @GetMapping
    public String welcomeAPI(){

        return "Bienvenue sur l'API Mediaskol";
    }
}
