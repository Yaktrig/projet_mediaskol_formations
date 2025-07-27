package fr.mediaskol.projet.dal;


import fr.mediaskol.projet.bo.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, String> {

    // Rechercher un utilisateur par son pseudo
    Utilisateur findByPseudo(@Param("pseudo") String pseudo);

    // Rechercher un utilisateur par son pseudo et son mot de passe
    Utilisateur findByPseudoAndPassword(@Param("pseudo") String pseudo, @Param("password") String password);

}
