package fr.mediaskol.projet.bo.salle;


import lombok.Getter;

/**
 * Enumération représentant les différents statuts possibles de la salle durant la formation
 * <p>
 * Chaque statut indique aux salariés si la salle est réservée, s'il y a besoin d'une clé
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
public enum StatutSessionSalle {

    // Statut indiquant la salle est validée
    SESSION_SALLE_VALIDEE("Salle durant la session validée", "#vert"),

    // Statut indiquant la salle est validée et qu'il faut une clé ou un digicode
    SESSION_SALLE_VALIDE_CLE_OU_DIGICODE("Salle durant la session validé avec clé ou digicode", "#rose"),

    // Statut indiquant qu'on est en attente de devis pour la salle
    SESSION_SALLE_ATTENTE_DEVIS("Salle en attente de devis", "#jaune");


    // Libelle du statut de la session salle
    private final String libelleSessionSalle;

    // Couleur du statut de la session salle
    private final String couleurSessionSalle;

    /**
     * Constructeur de l'énumération
     *
     * @param libelleSessionSalle Libellé du statut
     * @param couleurSessionSalle Couleur du statut
     */
    StatutSessionSalle(String libelleSessionSalle, String couleurSessionSalle) {
        this.libelleSessionSalle = libelleSessionSalle;
        this.couleurSessionSalle = couleurSessionSalle;
    }
}