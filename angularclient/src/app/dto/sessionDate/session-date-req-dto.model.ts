import {StatutSessionDate} from './statut-session-date.enum';


export interface SessionDateReqDto {

  idSessionDate: number | null;
  dateSession: string | null;
  duree: number | null;
  heureVisio: string | null;
  statutSessionDate: StatutSessionDate;
  sessionFormateur: { idPersonne : number } | null;
  sessionFormation: { idFormation : number } | null;
  sessionSalle: { idSessionSalle : number } | null;
}
