export enum StatutSessionFormateur {

  // Statut indiquant que le formateur n'a pas encore confirmé sa présence
  SESSION_FORMATEUR_ATTENTE_PRESENCE = 'SESSION_FORMATEUR_ATTENTE_PRESENCE',

  // Statut indiquant que le formateur a confirmé sa présence par mail
  SESSION_FORMATEUR_PRESENCE_CONFIRMEE = 'SESSION_FORMATEUR_PRESENCE_CONFIRMEE',

  // Statut indiquant que le formateur a annulé sa présence
  SESSION_FORMATEUR_PRESENCE_ANNULEE = 'SESSION_FORMATEUR_PRESENTE_ANNULEE',

  // Statut indiquant que le formateur est en attente de paiement - Quand la session de formation est terminée
  SESSION_FORMATEUR_EN_ATTENTE_PAIEMENT = 'SESSION_FORMATEUR_EN_ATTENTE_PAIEMENT',

  // Statut indiquant que le formateur est en attente de paiement - Quand la session de formation est terminée
  SESSION_FORMATEUR_SALARIE = 'SESSION_FORMATEUR_SALARIE',

  // Statut indiquant que la facture envoyée par le formateur auto-entrepreneur a été réglée - Quand la session de formation est terminée
  SESSION_FORMATEUR_AE_FACTURE_REGLEE = 'SESSION_FORMATEUR_AE_FACTURE_REGLEE',
}

export const StatutSessionFormateurDetails: {
  [key in StatutSessionFormateur]: { label: string, color: string }
} = {
  [StatutSessionFormateur.SESSION_FORMATEUR_ATTENTE_PRESENCE]: {
    label: 'En attente de confirmation de présence',
    color: '#FFBC9A',
  },
  [StatutSessionFormateur.SESSION_FORMATEUR_PRESENCE_CONFIRMEE]: {
    label: 'Présence du formateur confirmée',
    color: '#B7E4C7',
  },
  [StatutSessionFormateur.SESSION_FORMATEUR_PRESENCE_ANNULEE]: {
    label: 'Annulation par le formateur',
    color: '#F4A6A6',
  },
  [StatutSessionFormateur.SESSION_FORMATEUR_EN_ATTENTE_PAIEMENT]: {
    label: '"Formateur en attente de paiement',
    color: '#FFFFFF',
  },
  [StatutSessionFormateur.SESSION_FORMATEUR_SALARIE]: {
    label: 'Formateur salarié',
    color: '#FADADD',
  },
  [StatutSessionFormateur.SESSION_FORMATEUR_AE_FACTURE_REGLEE]: {
    label: 'Formateur auto-entrepreneur qui a envoyé sa facture et qui a été réglée.',
    color: '#D3D3D3',
  },
};
