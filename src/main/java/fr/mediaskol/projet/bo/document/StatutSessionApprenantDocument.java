package fr.mediaskol.projet.bo.document;

import lombok.Getter;

/**
 * Enumération représentant les différents statuts possibles d'un document nécessaire pour que l'apprenant
 * participe à une session de formation.
 * <p>
 * Chaque statut indique aux salariés si le document est non reçu, reçu et non conforme, reçu et conforme.
 * </p>
 * <p>
 * Utilisation de Lombok (@Getter) pour générer automatiquement les méthodes getters
 * </p>
 * todo Les couleurs sont à définir ultérieurement.
 */
@Getter
public enum StatutSessionApprenantDocument {


    // Statut indiquant que le document n'a pas été reçu
    DOCUMENT_NON_RECU("NR", "#vert"),

    // Statut indiquant que le document est reçu, mais non conforme (dates ou formulaire incorrect, document trop flou...)
    DOCUMENT_NON_CONFORME("NC", "#rose"),

    // Statut indiquant que le document reçu est conforme, permet de valider le dossier de l'apprenant sur une session.
    DOCUMENT_CONFORME("C", "#jaune");


    // Libelle du statut du document
    private final String libelleSessionApprenantDocument;

    // Couleur du statut du document - Todo vérifier si nécessaire - On laisse pour le moment en attente retour des filles
    private final String couleurSessionApprenantDocument;

    /**
     * Constructeur de l'énumération
     *
     * @param libelleSessionApprenantDocument Libellé du statut
     * @param couleurSessionApprenantDocument Couleur du statut
     */
    StatutSessionApprenantDocument(String libelleSessionApprenantDocument, String couleurSessionApprenantDocument) {
        this.libelleSessionApprenantDocument = libelleSessionApprenantDocument;
        this.couleurSessionApprenantDocument = couleurSessionApprenantDocument;
    }
}
