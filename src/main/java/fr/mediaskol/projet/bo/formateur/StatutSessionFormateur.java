package fr.mediaskol.projet.bo.formateur;


import lombok.Getter;

/**
 * Enumération représentant les différents statuts possibles pour le traitement de la session formateur
 * <p>
 * Chaque statut indique l’étape de la session formateur (en attente de confirmation du formateur, si présence confirmée,
 * ou formateur qui n'est plus disponible).
 * </p>
 * <p>
 * Permet à chaque salarié de voir s'il y a un formateur confirmé qui animera la session de formation.
 * </p>
 * <p>
 * Utilisation de Lombok (@Getter) pour générer automatiquement les méthodes getters
 * </p>
 * todo Les couleurs sont à définir ultérieurement.
 */

@Getter
public enum StatutSessionFormateur {

    // Statut indiquant que le formateur n'a pas encore confirmé sa présence
    SESSION_FORMATEUR_ATTENTE_PRESENCE("En attente de confirmation de présence", "#FFBC9A"),

    // Statut indiquant que le formateur a confirmé sa présence par mail
    SESSION_FORMATEUR_PRESENCE_CONFIRMEE("Présence du formateur confirmée", "#B7E4C7"),

    // Statut indiquant que le formateur a annulé sa présence
    SESSION_FORMATEUR_PRESENCE_ANNULEE("Annulation par le formateur", "#F4A6A6"),

    // Statut indiquant que le formateur est en attente de paiement - Quand la session de formation est terminée
    SESSION_FORMATEUR_EN_ATTENTE_PAIEMENT("Formateur en attente de paiement", "#FFFFFF"),

    // Statut indiquant que le formateur est en attente de paiement - Quand la session de formation est terminée
    SESSION_FORMATEUR_SALARIE("Formateur salarié", "#FADADD"),

    // Statut indiquant que la facture envoyée par le formateur auto-entrepreneur a été réglée - Quand la session de formation est terminée
    SESSION_FORMATEUR_AE_FACTURE_REGLEE("Formateur auto-entrepreneur qui a envoyé sa facture et qui a été réglée.", "#D3D3D3");


    // Libelle du statut de la session formateur
    private final String libelleSessionFormateur;

    // Couleur du statut de la session formateur
    private final String couleurSessionFormateur;

    /**
     * Constructeur de l'énumération
     *
     * @param libelleSessionFormateur Libellé du statut
     * @param couleurSessionFormateur Couleur du statut
     */
    StatutSessionFormateur(String libelleSessionFormateur, String couleurSessionFormateur) {
        this.libelleSessionFormateur = libelleSessionFormateur;
        this.couleurSessionFormateur = couleurSessionFormateur;
    }

}
