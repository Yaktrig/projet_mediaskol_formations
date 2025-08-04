import { ApprenantResponseDTO} from './apprenant-resp-dto.model';
import {SessionFormationRespDTO} from '../../sessionFormation/dto/session-formation-resp-dto.model';
import { StatutSessionApprenant} from './statut-session-apprenant.enum';

export interface SessionApprenantRespDtoModel {

  idSessionApprenant: number | null;
  commentaireSessionApprenant: string | null;
  modeReceptionInscription: string | null;
  StatutSessionApprenant: StatutSessionApprenant;
  apprenant: ApprenantResponseDTO | null;
  sessionFormation: SessionFormationRespDTO | null;
}
