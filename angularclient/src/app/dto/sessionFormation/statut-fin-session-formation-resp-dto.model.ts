
export enum StatutFinSessionFormation {

  // Statut indiquant que la session de formation n'est pas encore terminée.
  SESSION_FORMATION_NON_TERMINEE='SESSION_FORMATION_NON_TERMINEE',

  // Statut indiquant que la formation est terminée et traitée par Mediaskol.
  SESSION_FIN_FORMATION_TERMINEE='SESSION_FIN_FORMATION_TERMINEE',

  // Statut indiquant que la formation est terminée, traitée et facturée par Mediaskol et validée par Iperia
  // (tous les documents sont conformes).
  SESSION_FIN_FORMATION_TERMINEE_VALIDEE='SESSION_FIN_FORMATION_TERMINEE_VALIDEE',

  // Statut indiquant que la formation est terminée, traitée et facturée par Mediaskol, mais non validée par Iperia
  // (des documents ne sont pas conformes).
  SESSION_FIN_FORMATION_TERMINEE_NON_VALIDEE='SESSION_FIN_FORMATION_TERMINEE_NON_VALIDEE',
}

export const StatutFinSessionFormationDetails: {
  [key in StatutFinSessionFormation]: {label: string, color: string}
} = {
  [StatutFinSessionFormation.SESSION_FORMATION_NON_TERMINEE]:{
    label:'Session non terminée',
    color:'#FFFFFF',
  },
  [StatutFinSessionFormation.SESSION_FIN_FORMATION_TERMINEE]:{
    label:'Formation terminée - Mediaskol',
    color:'Orange',
  },
  [StatutFinSessionFormation.SESSION_FIN_FORMATION_TERMINEE_VALIDEE]:{
    label:'Formation terminée, traitée par Mediakol et validée par Iperia',
    color:'Vert',
  },
  [StatutFinSessionFormation.SESSION_FIN_FORMATION_TERMINEE_NON_VALIDEE]:{
    label:'Formation terminée, traitée par Mediakol mais non valide par Iperia',
    color:'Rouge',
  },
};
