import {SalleResponseDto} from './salle-resp-dto.model';
import {StatutSessionSalle} from './statut-session-salle.enum';

export interface SessionSalleRespDTO {

  idSessionSalle: number | null;
  commentaireSessionSalle: string | null;
  coutSessionSalle: number | null;
  statutSessionSalle: StatutSessionSalle;
  salle: SalleResponseDto | null;
  sessionFormation: SessionFormationRespDTO;
}
