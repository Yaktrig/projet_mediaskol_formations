import {SessionFormationReqDTO} from './session-formation-req-dto.model';
import {StatutSessionFormation} from './statut-session-formation.enum';


export interface SessionFopReqDTO extends SessionFormationReqDTO {

  lieuSessionFormation: string | null;
  commanditaire: string | null;
  confirmationRPE: string | null;
  departement: { idDepartement: number } | null;
  sessionsSalle: { idSessionSalle: number } | null;
  sessionsFormateur: { idSessionFormation: number } | null;
  statutSessionFormation: StatutSessionFormation;
  sessionsDate: { idSessionDate: number } | null;
}
