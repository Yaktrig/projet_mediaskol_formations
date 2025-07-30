import {SessionFormationRespDTO} from './session-formation-resp-dto.model';

export interface SessionFoadResponseDTO extends SessionFormationRespDTO{


  contratSessionFormationDistanciel: string | null;
  nbBlocSessionFormationDistanciel: number | null;
  dateFinSessionFormationDistanciel: string | null;
  dateRelanceSessionFormationDistanciel: string | null;
  commentaireSessionFormationDistanciel: string | null;
}
