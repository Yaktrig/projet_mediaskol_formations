import {DepartementDTO} from '../../departement/dto/departement-resp-dto.model';
import {SessionFormationRespDTO} from './session-formation-resp-dto.model';
import {SessionSalleRespDTO} from '../../sessionSalle/dto/session-salle-resp-dto.model';
import {SessionFormateurRespDTO} from '../../formateur/dto/session-formateur-resp-dto.model';
import {StatutSessionFormation} from './statut-session-formation.enum';
import {SessionDateRespDto} from '../../sessionDate/dto/session-date-resp-dto.model';


export interface SessionFopRespDTO extends SessionFormationRespDTO {

  lieuSessionFormation: string | null;
  commanditaire: string | null;
  confirmationRPE: string | null;
  departement: DepartementDTO | null;
  sessionsSalle : SessionSalleRespDTO[];
  sessionsFormateur : SessionFormateurRespDTO[];
  statutSessionFormation: StatutSessionFormation ;
  sessionsDate : SessionDateRespDto[];

}
