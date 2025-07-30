import {DepartementDTO} from '../adresse/departement-resp-dto.model';
import {SessionFormationRespDTO} from './session-formation-resp-dto.model';
import {SessionSalleRespDTO} from '../salle/session-salle-resp-dto.model';
import {SessionFormateurRespDTO} from '../formateur/session-formateur-resp-dto.model';
import {StatutSessionFormation} from './statut-session-formation.enum';


export interface SessionFopRespDTO extends SessionFormationRespDTO {

  lieuSessionFormation: string | null;
  commanditaire: string | null;
  confirmationRPE: string | null;
  departement: DepartementDTO | null;
  sessionsSalle : SessionSalleRespDTO[];
  sessionsFormateur : SessionFormateurRespDTO[];
  statutSessionFormation: StatutSessionFormation;

}
