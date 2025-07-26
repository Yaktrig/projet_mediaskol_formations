export enum StatutSessionSalle {

  // Statut indiquant la salle est validée
  SESSION_SALLE_VALIDEE = 'SESSION_SALLE_VALIDEE',
  // Statut indiquant la salle est validée et qu'il faut une clé ou un digicode
  SESSION_SALLE_VALIDE_CLE_OU_DIGICODE = 'SESSION_SALLE_VALIDE_CLE_OU_DIGICODE',
  // Statut indiquant qu'on est en attente de devis pour la salle
  SESSION_SALLE_ATTENTE_DEVIS = 'SESSION_SALLE_ATTENTE_DEVIS',

}

export const StatutSessionSalleDetails: StatutSessionSalle = {
  label: string, color: string
} = {
  [StatutSessionSalle.SESSION_SALLE_VALIDEE]: {
    label: 'Salle durant la session validée',
    color: 'green',
  },
  [StatutSessionSalle.SESSION_SALLE_VALIDE_CLE_OU_DIGICODE]: {
    label: 'Salle durant la session validé avec clé ou digicode',
    color: 'pink',
  },
  [StatutSessionSalle.SESSION_SALLE_ATTENTE_DEVIS]: {
    label: 'Salle en attente de devis',
    color: 'yellow',
  },
};
