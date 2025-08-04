import {StatutSessionDate} from './statut-session-date.enum';
import {SessionFormateurRespDTO} from '../../formateur/dto/session-formateur-resp-dto.model';
import {SessionFormationRespDTO} from '../../sessionFormation/dto/session-formation-resp-dto.model';
import {SessionSalleRespDTO} from '../../sessionSalle/dto/session-salle-resp-dto.model';


export interface SessionDateRespDto {

  idSessionDate: number | null;
  dateSession: string | null;
  duree: number | null;
  heureVisio: string | null;
  statutSessionDate: StatutSessionDate;
  sessionFormateur: SessionFormateurRespDTO;
  sessionFormation: SessionFormationRespDTO;
  sessionSalle: SessionSalleRespDTO;
}
