export enum StatutSessionDate {


  // Statut indiquant que la salle où a lieu la formation est gratuite
  SESSION_DATE_SALLE_GRATUITE = 'SESSION_DATE_SALLE_GRATUITE',

  // Statut indiquant que la facture n'a pas été reçue
  SESSION_DATE_FACTURE_NON_RECUE = 'SESSION_DATE_FACTURE_NON_RECUE',

  // Statut indiquant que la facture a été réglée
  SESSION_DATE_FACTURE_REGLEE = 'SESSION_DATE_FACTURE_REGLEE',


}

export const StatutSessionDateDetails:{
  [key in StatutSessionDate]: {label: string, color: string}
} = {
  [StatutSessionDate.SESSION_DATE_SALLE_GRATUITE]: {
    label: 'Salle de formation gratuite',
    color: 'orange',
  },
  [StatutSessionDate.SESSION_DATE_FACTURE_NON_RECUE]: {
    label: 'Facture de la salle non reçue',
    color: '#FFFFFF',
  },
  [StatutSessionDate.SESSION_DATE_FACTURE_REGLEE]: {
    label: 'Facture de la salle réglée',
    color: 'gris',
  },
};
