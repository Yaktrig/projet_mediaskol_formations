package fr.mediaskol.projet.bo.sessionDate;

import lombok.Getter;

/**
 * Enumération représentant les différents statuts possibles pour le traitement de la facturation de la session lieu date
 * <p>
 * Chaque statut indique l’étape de la facturation pour une journée de formation en présentiel.
 * </p>
 * <p>
 * Permet au salarié qui gère les factures de voir si la facturation est à jour pour chaque jour d'une session de
 * formation.
 * </p>
 * <p>
 * Utilisation de Lombok (@Getter) pour générer automatiquement les méthodes getters
 * </p>
 * todo Les couleurs sont à définir ultérieurement.
 */
@Getter
public enum StatutSessionDate {

    // Statut indiquant que la salle où à eu lieu la formation est gratuite
    SESSION_LIEU_DATE_SALLE_GRATUITE("Salle de formation gratuite", "#orange"),

    // Statut indiquant que la facture n'a pas été reçue
    SESSION_LIEU_DATE_FACTURE_NON_RECUE("Facture de la salle non reçue", "#FFFFFF"),

    // Statut indiquant que la facture a été réglée
    SESSION_LIEU_DATE_FACTURE_REGLEE("Facture de la salle réglée", "#gris");


    // Libelle du statut de la session lieu date pour la salle
    private final String libelleSessionLieuDate;

    // Couleur du statut de la session lieu date pour la salle
    private final String couleurSessionLieuDate;

    /**
     * Constructeur de l'énumération
     *
     * @param libelleSessionLieuDate Libellé du statut
     * @param couleurSessionLieuDate Couleur du statut
     */
    StatutSessionDate(String libelleSessionLieuDate, String couleurSessionLieuDate) {
        this.libelleSessionLieuDate = libelleSessionLieuDate;
        this.couleurSessionLieuDate = couleurSessionLieuDate;
    }
}
