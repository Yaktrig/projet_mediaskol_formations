import {StatutSessionSalle} from './statut-session-salle.enum';

export interface SessionSalleReqDTO {

  idSessionSalle: number | null;
  commentaireSessionSalle: string | null;
  coutSessionSalle: number | null;
  statutSessionSalle: StatutSessionSalle;
  salle: { idSalle : number } | null;
  sessionFormation: { idSessionFormation : number } | null;
}
