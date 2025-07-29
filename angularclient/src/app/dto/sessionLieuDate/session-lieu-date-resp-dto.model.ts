import {StatutSessionLieuDate} from './statut-session-lieu-date.enum';
import {SessionFormateurRespDTO} from '../formateur/session-formateur-resp-dto.model';
import {SessionFormationRespDTO} from '../sessionFormation/session-formation-presentiel-resp-dto.model';
import {SessionSalleRespDTO} from '../salle/session-salle-resp-dto.model';


export interface SessionLieuDateRespDto {

  idSessionLieuDate: number | null;
  dateSession: string | null;
  lieuSession: string | null;
  duree: number | null;
  heureVisio: string | null;
  statutSessionLieuDate: StatutSessionLieuDate;
  sessionFormateur: SessionFormateurRespDTO;
  sessionFormation: SessionFormationRespDTO;
  sessionSalle: SessionSalleRespDTO;
}
