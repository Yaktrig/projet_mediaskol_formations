import {SessionFormation} from './session-formation-resp-dto.model';

export interface SessionFoadResponseDTO extends SessionFormation{


  contratSessionFormationDistanciel: string | null;
  nbBlocSessionFormationDistanciel: number | null;
  dateFinSessionFormationDistanciel: string | null;
  dateRelanceSessionFormationDistanciel: string | null;
  commentaireSessionFormationDistanciel: string | null;
}
