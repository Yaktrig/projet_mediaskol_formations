import {StatutSessionFormateur} from './statut-session-formateur.enum';
import {SessionFormationRespDTO} from '../sessionFormation/session-formation-resp-dto.model';
import {FormateurResponseDTO} from './formateur-resp-dto.model';

export interface SessionFormateurRespDTO {

  idSessionFormateur: number | null;
  commentaireSessionFormateur: string | null;
  statutSessionFormateur: StatutSessionFormateur;
  coutSessionFormateur: number | null;
  formateur: FormateurResponseDTO;
  sessionFormation: SessionFormationRespDTO;
}
