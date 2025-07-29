import {DepartementDTO} from '../adresse/departement-resp-dto.model';
import {SessionFormation} from './session-formation-resp-dto.model';


export interface SessionFormationRespDTO extends SessionFormation {

  lieuSessionFormation: string | null;
  commanditaire: string | null;
  confirmationRPE: string | null;
  departement: DepartementDTO | null;

}
