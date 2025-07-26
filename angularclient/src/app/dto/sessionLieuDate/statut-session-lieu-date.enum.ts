export enum StatutSessionLieuDate {


  // Statut indiquant que la salle où à eu lieu la formation est gratuite
  SESSION_LIEU_DATE_SALLE_GRATUITE = 'SESSION_LIEU_DATE_SALLE_GRATUITE',

  // Statut indiquant que la facture n'a pas été reçue
  SESSION_LIEU_DATE_FACTURE_NON_RECUE = 'SESSION_LIEU_DATE_FACTURE_NON_RECUE',

  // Statut indiquant que la facture a été réglée
  SESSION_LIEU_DATE_FACTURE_REGLEE = 'SESSION_LIEU_DATE_FACTURE_REGLEE',


}

export const StatutSessionLieuDateDetails: StatutSessionLieuDate = {
  label: string, color: string,
} = {
  [StatutSessionLieuDate.SESSION_LIEU_DATE_SALLE_GRATUITE]: {
    label: 'Salle de formation gratuite',
    color: 'orange',
  },
  [StatutSessionLieuDate.SESSION_LIEU_DATE_FACTURE_NON_RECUE]: {
    label: 'Facture de la salle non reçue',
    color: '#FFFFFF',
  },
  [StatutSessionLieuDate.SESSION_LIEU_DATE_FACTURE_REGLEE]: {
    label: 'Facture de la salle réglée',
    color: 'gris',
  },
};
