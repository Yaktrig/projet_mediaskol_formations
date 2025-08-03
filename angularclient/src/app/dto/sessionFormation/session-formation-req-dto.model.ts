import {StatutSessionFormation} from './statut-session-formation.enum';

/**
 * Classe mère des sessions de formation (présentiel et distanciel)
 */
export interface SessionFormationReqDTO {

  idSessionFormation: number | null;
  noYoda: string | null;
  libelleSessionFormation: string | null;
  statutYoda: string | null;
  dateDebutSession: Date | null;
  nbHeureSession: number | null;
  statutSessionFormation: StatutSessionFormation;
  salarie: { idPersonne : number } | null;
  formation: { idFormation : number } | null;
  finSessionFormation: { idFinSessionFormation : number } | null;

}
