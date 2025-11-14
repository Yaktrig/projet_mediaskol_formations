package fr.mediaskol.projet.dal.apprenant;

import fr.mediaskol.projet.bo.apprenant.Apprenant;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class ApprenantSpecifications {

    /**
     * Retourne dynamiquement un apprenant selon les critères saisis par l'utilisateur
     * Le nom, l'email et la ville sont en minuscules
     */

    /**
     * @param nom
     * @return
     */
    public static Specification<Apprenant> nomContains(String nom) {
        return (root, query, builder) ->
                nom == null ? null : builder.like(builder.lower(root.get("nom")), "%" + nom.toLowerCase() + "%");
    }

    /**
     * @param dateNaissance
     * @return
     */
    public static Specification<Apprenant> dateNaissanceContains(LocalDate dateNaissance) {
        return (root, query, builder) ->
                dateNaissance == null ? null : builder.equal(root.get("dateNaissance"), dateNaissance);
    }


    /**
     * @param email
     * @return
     */
    public static Specification<Apprenant> emailContains(String email) {
        return (root, query, builder) ->
                email == null ? null : builder.like(builder.lower(root.get("email")), "%" + email.toLowerCase() + "%" );
    }

    /**
     * @param ville
     * Jointure entre Apprenant → Adresse
     * @return
     */
    public static Specification<Apprenant> villeContains(String ville) {
        return (root, query, builder) ->
                ville == null ? null : builder.like(
                        builder.lower(root.join("adresse").get("ville")), "%" + ville.toLowerCase() + "%");
    }

    /**
     * @param numDepartement
     * Jointure entre Apprenant → Adresse → Departement
     * @return
     */
    public static Specification<Apprenant> numDepartement(Long numDepartement) {
        return (root, query, builder) ->
                numDepartement == null ? null : builder.equal(
                        root.join("adresse").join("departement").get("numDepartement"), numDepartement);
    }


}
