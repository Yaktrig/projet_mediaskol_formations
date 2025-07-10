package fr.mediaskol.projet.bo.SessionFormation;

import lombok.Getter;

/**
 * Enumération représentant les différents statuts possibles pour le traitement d'une session de formation terminée.
 * <p>
 * Chaque statut indique l’étape d'une fin de session de formation (traité par Mediaskol, traité par Médiaskol et
 * validé par Ipéria, facturé mais non valide pour Iperia).
 * </p>
 * <p>
 * Permet à chaque salarié de voir l'avancement du traitement de fin de formation.
 * </p>
 * <p>
 * Utilisation de Lombok (@Getter) pour générer automatiquement les méthodes getters
 * </p>
 * todo Les couleurs associées sont à définir ultérieurement.
 */
@Getter
public enum StatutSessionFinFormation {


    // Statut indiquant que la session de formation n'est pas encore terminée.
    SESSION_FORMATION_NON_TERMINEE("Session non terminée", "#FFFFFF"),

    // Statut indiquant que la formation est terminée et traitée par Mediaskol.
    SESSION_FIN_FORMATION_TERMINEE("Formation terminée - Mediaskol",
            "Orange"),

    // Statut indiquant que la formation est terminée, traitée et facturée par Mediaskol et validée par Iperia
    // (tous les documents sont conformes).
    SESSION_FIN_FORMATION_TERMINEE_VALIDEE("Formation terminée, traitée par Mediakol et validée " +
            "par Iperia", "Vert"),

    // Statut indiquant que la formation est terminée, traitée et facturée par Mediaskol, mais non validée par Iperia
    // (des documents ne sont pas conformes).
    SESSION_FIN_FORMATION_TERMINEE_NON_VALIDEE("Formation terminée, traitée par Mediakol mais " +
            "non valide par Iperia", "Rouge");


    // Libelle du statut de la fin d'une session de formation
    private final String libelleSessionFinFormation;

    // Couleur du statut de la fin d'une session de formation
    private final String couleurSessionFinFormation;

    /**
     * Constructeur de l'énumération
     *
     * @param libelleSessionFinFormation Libellé du statut
     * @param couleurSessionFinFormation Couleur du statut
     */
    StatutSessionFinFormation(String libelleSessionFinFormation, String couleurSessionFinFormation) {
        this.libelleSessionFinFormation = libelleSessionFinFormation;
        this.couleurSessionFinFormation = couleurSessionFinFormation;
    }
}
