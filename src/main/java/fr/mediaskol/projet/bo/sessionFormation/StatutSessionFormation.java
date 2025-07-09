package fr.mediaskol.projet.bo.sessionFormation;


import lombok.Getter;

/**
 * Enumération représentant les différents statuts possibles pour le traitement de la session de formation
 * <p>
 * Chaque statut indique l’étape d'une session de formation (session de formation non commencée,
 * dossier en cours de traitement, traitement du dossier terminé, session terminée.
 * </p>
 * <p>
 * Permet à chaque salarié de voir si un.e collègue traite ou a traité le dossier de la session, ainsi que
 * de vérifier si la session de formation a débuté, est en cours, est terminée.
 * </p>
 * <p>
 * Utilisation de Lombok (@Getter) pour générer automatiquement les méthodes getters
 * </p>
 * todo Les couleurs associées sont à définir ultérieurement.
 */

@Getter
public enum StatutSessionFormation {

    // Statut indiquant que la session de formation n'a pas encore commencée (Date du 1er jour)
    SESSION_FORMATION_NON_COMMENCEE("Session non commencée", "#FFFFFF"),

    // Statut indiquant qu'un.e salarié.e est en train de traiter le dossier
    DOSSIER_EN_COURS("Dossier en cours", "Rose1"),

    // Statut indiquant qu'un.e salarié.e a terminé le traitement du dossier
    DOSSIER_TERMINE("Dossier terminé", "Rose2"),

    // Statut indiquant que la session de formation est terminée (Date du dernier jour passé)
    SESSION_FORMATION_TERMINEE("Formation terminée", "Bleu");

    // Libelle du statut de la session de formation
    private final String libelleSessionFormation;

    // Couleur du statut de la session de formation
    private final String couleurSessionFormation;

    /**
     * Constructeur de l'énumération
     *
     * @param libelleSessionFormation Libellé du statut
     * @param couleurSessionFormation Couleur du statut
     */
    StatutSessionFormation(String libelleSessionFormation, String couleurSessionFormation) {
        this.libelleSessionFormation = libelleSessionFormation;
        this.couleurSessionFormation = couleurSessionFormation;
    }

}
