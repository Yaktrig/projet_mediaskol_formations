export enum StatutSessionFormation {

  // Statut indiquant que la session de formation n'a pas encore commencée (Date du 1er jour)
  SESSION_FORMATION_NON_COMMENCEE = 'SESSION_FORMATION_NON_COMMENCEE',

  // Statut indiquant qu'un.e salarié.e est en train de traiter le dossier
  DOSSIER_EN_COURS = 'DOSSIER_EN_COURS',

  // Statut indiquant qu'un.e salarié.e a terminé le traitement du dossier
  DOSSIER_TERMINE = 'DOSSIER_TERMINE',

  // Statut indiquant que la session de formation est terminée (Date du dernier jour passé)
  SESSION_FORMATION_TERMINEE = 'SESSION_FORMATION_TERMINEE',

  // Statut indiquant que la session de formation a été annulée.
  SESSION_FORMATION_ANNULEE = 'SESSION_FORMATION_ANNULEE',

}

export const StatutSessionFormationDetails: {
  [key in StatutSessionFormation]: { label: string; color: string }
} = {
  [StatutSessionFormation.SESSION_FORMATION_NON_COMMENCEE]: {
    label: 'Session non commencée',
    color: '#FFFFFF',
  },
  [StatutSessionFormation.DOSSIER_EN_COURS]: {
    label: 'Dossier en cours',
    color: '#FADADD', // exemple Tailwind : adapte selon ta config
  },
  [StatutSessionFormation.DOSSIER_TERMINE]: {
    label: 'Dossier terminé',
    color: '#F6C1C1',
  },
  [StatutSessionFormation.SESSION_FORMATION_TERMINEE]: {
    label: 'Formation terminée',
    color: '#89CFF0',
  },
  [StatutSessionFormation.SESSION_FORMATION_ANNULEE]: {
    label: 'Formation annulée',
    color: '#F4A6A6',
  },
};


