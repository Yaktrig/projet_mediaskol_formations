import {StatutFinSessionFormation} from './statut-fin-session-formation-resp-dto.model';

export interface FinSessionFormationRespDTO {

  idFinSessionFormation: number | null;
  statutYodaFinSessionFormation: string | null;
  dateLimiteYodaFinSessionFormation: string | null;
  commentaireFinSessionFormation: string | null;
  statutFinSessionFormation: StatutFinSessionFormation;
}
