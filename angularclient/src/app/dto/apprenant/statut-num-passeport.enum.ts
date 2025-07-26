export enum StatutNumPasseport {

  // Statut indiquant que le numéro de passeport a été créé
  NUM_PASSEPORT_CREE = 'NUM_PASSEPORT_CREE',

  // Statut indiquant que le numéro de passeport doit être créé
  // Todo couleur à définir - Police ou Fond
  NUM_PASSEPORT_A_CREER = 'NUM_PASSEPORT_A_CREER',

  // Statut indiquant que le numéro de passeport a été créé et doit être envoyé par mail à l'apprenant
  // Todo couleur à définir - Vert
  NUM_PASSEPORT_A_ENVOYER = 'NUM_PASSEPORT_A_ENVOYER',

}

export const StatutNumPasseportDetails: StatutNumPasseport = {
  label: string, color: string
} = {
  [StatutNumPasseport.NUM_PASSEPORT_CREE]: {
    label: 'Numéro passeport créé',
    color: '#FFFFFF',
  },
  [StatutNumPasseport.NUM_PASSEPORT_A_CREER]: {
    label: 'Numéro passeport à créer',
    color: 'red-300',
  },
  [StatutNumPasseport.NUM_PASSEPORT_A_ENVOYER]: {
    label: 'Numéro passeport à envoyer',
    color: 'green-300',
  },
};
