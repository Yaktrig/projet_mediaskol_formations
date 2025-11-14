export enum StatutSessionApprenant {

  // Statut de l'apprenant à la session de formation - Présent et inscrit sur Yoda
  INSCRIT_SUR_YODA = 'INSCRIT_SUR_YODA',

  // Statut de l'apprenant - Présent, mais non inscrit sur Yoda
  PRESENT_SESSION = 'PRESENT_SESSION',

  // Statut de l'apprenant - Absent à la session - (sera barré en front également ?)
  ABSENT_SESSION = 'ABSENT_SESSION',
}

export const StatutSessionApprenantDetails: {
  [key in StatutSessionApprenant]: {label: string, color: string}
} = {
  [StatutSessionApprenant.INSCRIT_SUR_YODA]: {
    label: 'Inscrit sur Yoda',
    color: 'blue-500',
  },
  [StatutSessionApprenant.PRESENT_SESSION]: {
    label: 'Présent durant la session',
    color: 'blue-50',
  },
  [StatutSessionApprenant.ABSENT_SESSION]: {
    label: 'Absent durant la session',
    color: '#FFFFFF',
  },
};
