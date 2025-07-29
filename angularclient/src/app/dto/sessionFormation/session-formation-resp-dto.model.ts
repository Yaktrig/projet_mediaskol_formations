import {StatutSessionFormation} from './statut-session-formation.enum';
import {FormationResponseDTO} from '../formation/formation-resp-dto.model';
import {DepartementDTO} from '../adresse/departement-resp-dto.model';
import {SessionFoadResponseDTO} from '../sessionFormationDistanciel/session-formation-distanciel-resp-dto.model';
import {FinSessionFormationRespDTO} from './fin-session-formation-resp-dto.model';
import {SalarieRespDto} from '../salarie/salarie-resp-dto.model';


export interface SessionFormationRespDTO {
  idSessionFormation: number | null;
  noYoda: string | null;
  libelleSessionFormation: string | null;
  statutYoda: string | null;
  lieuSessionFormation: string |null;
  commanditaire: string | null;
  confirmationRPE: string | null;
  dateDebutSession: string | null;
  nbHeureSession: number | null;
  statutSessionFormation: StatutSessionFormation;
  salarie: SalarieRespDto | null;
  formation: FormationResponseDTO | null;
  departement: DepartementDTO | null;
  finSessionFormation: FinSessionFormationRespDTO | null;
  sessionFormationDistanciel: SessionFoadResponseDTO | null;
}
