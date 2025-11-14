import {SessionFormationRespDTO} from './session-formation-resp-dto.model';
import {StatutSessionFormation} from './statut-session-formation.enum';

export interface SessionFoadResponseDTO extends SessionFormationRespDTO{


  contratSessionFormationDistanciel: string | null;
  nbBlocSessionFormationDistanciel: number | null;
  dateFinSessionFormationDistanciel: string | null;
  dateRelanceSessionFormationDistanciel: string | null;
  commentaireSessionFormationDistanciel: string | null;
  statutSessionFormation: StatutSessionFormation;
}
