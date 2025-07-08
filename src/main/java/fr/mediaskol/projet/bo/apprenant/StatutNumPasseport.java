package fr.mediaskol.projet.bo.apprenant;

import lombok.Getter;

/**
 * Enumération représentant les différents statuts possibles pour le numéro de passeport d’un apprenant.
 * <p>
 * Chaque statut indique l’étape de gestion du passeport : créé, à créer, ou à envoyer.
 * todo Les couleurs associées sont à définir ultérieurement.
 * </p>
 */

@Getter // Génère les getters pour les champs
public enum StatutNumPasseport {

    // Statut indiquant que le numéro de passeport a été créé
    NUM_PASSEPORT_CREE("Numéro passeport créé", "#FFFFFF"),

    // Statut indiquant que le numéro de passeport doit être créé
    // Todo couleur à définir - Police ou Fond
    NUM_PASSEPORT_A_CREER("Numéro passeport à créer", "Police rouge A CREER"),

    // Statut indiquant que le numéro de passeport a été créé et doit être envoyé par mail à l'apprenant
    // Todo couleur à définir - Vert
    NUM_PASSEPORT_A_ENVOYER("Numéro passeport à envoyer", "Vert");

    // Libellé du statut du numéro du passeport
    private final String libelleStatutNumPasseport;

    // Couleur associée au statut du numéro du passeport, utilisée pour l'affichage dans l'UI
    private final String couleurStatutNumPasseport;


    /**
     * Constructeur de l'énumération
     *
     * @param libelleStatutNumPasseport Libellé du statut
     * @param couleurStatutNumPasseport Couleur du statut
     */
    StatutNumPasseport(String libelleStatutNumPasseport, String couleurStatutNumPasseport) {
        this.libelleStatutNumPasseport = libelleStatutNumPasseport;
        this.couleurStatutNumPasseport = couleurStatutNumPasseport;
    }
}
