package fr.mediaskol.projet.dal;


import fr.mediaskol.projet.bo.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, String> {

    // Rechercher un utilisateur par son pseudo
    Optional<Utilisateur> findByPseudo(String pseudo);



}
