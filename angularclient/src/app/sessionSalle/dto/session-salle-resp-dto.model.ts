import {SalleResponseDto} from '../../salle/dto/salle-resp-dto.model';
import {StatutSessionSalle} from './statut-session-salle.enum';
import {SessionFormationRespDTO} from '../../sessionFormation/dto/session-formation-resp-dto.model';

export interface SessionSalleRespDTO {

  idSessionSalle: number | null;
  commentaireSessionSalle: string | null;
  coutSessionSalle: number | null;
  statutSessionSalle: StatutSessionSalle;
  salle: SalleResponseDto | null;
  sessionFormation: SessionFormationRespDTO;
}
