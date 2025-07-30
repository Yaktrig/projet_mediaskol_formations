import {DepartementDTO} from '../adresse/departement-resp-dto.model';
import {SessionFormationRespDTO} from './session-formation-resp-dto.model';
import {SessionSalleRespDTO} from '../salle/session-salle-resp-dto.model';


export interface SessionFopRespDTO extends SessionFormationRespDTO {

  lieuSessionFormation: string | null;
  commanditaire: string | null;
  confirmationRPE: string | null;
  departement: DepartementDTO | null;
  sessionsSalle : SessionSalleRespDTO[];

}
