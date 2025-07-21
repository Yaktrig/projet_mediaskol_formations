package fr.mediaskol.projet.bo.apprenant;

import lombok.Getter;

/**
 * Enumération représentant les différents statuts possibles de l'inscription d'un apprenant à une session de formation.
 * <p>
 * Chaque statut indique aux salariés si l'apprenant est inscrit sur Yoda ou non, s'il est absent à la session.
 * L'apprenant peut être absent sur une journée, mais il prévient et sera géré pour le moment en commentaire dans
 * SessionApprenant.
 * </p>
 * <p>
 * Utilisation de Lombok (@Getter) pour générer automatiquement les méthodes getters
 * </p>
 * todo Les couleurs sont à définir ultérieurement.
 */
@Getter
public enum StatutSessionApprenant {


    // Statut de l'apprenant à la session de formation - Présent et inscrit sur Yoda
    INSCRIT_SUR_YODA("Inscrit sur Yoda", "#bleu plus foncé"),

    // Statut de l'apprenant - Présent, mais non inscrit sur Yoda
    PRESENT_SESSION("Présent durant la session", "#bleu clair"),

    // Statut de l'apprenant - Absent à la session - (sera barré en front également ?)
    ABSENT_SESSION("Absent durant la session", "#FFFFFF");


    // Libellé du statut d'inscription de l'apprenant
    private final String libelleSessionApprenant;

    // Couleur du statut d'inscription de l'apprenant
    private final String couleurSessionApprenant;

    /**
     * Constructeur de l'énumération
     *
     * @param libelleSessionApprenant Libellé du statut
     * @param couleurSessionApprenant Couleur du statut
     */

    StatutSessionApprenant(String libelleSessionApprenant, String couleurSessionApprenant) {
        this.libelleSessionApprenant = libelleSessionApprenant;
        this.couleurSessionApprenant = couleurSessionApprenant;
    }
}
