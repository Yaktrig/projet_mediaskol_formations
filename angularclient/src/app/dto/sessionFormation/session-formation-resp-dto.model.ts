import {StatutSessionFormation} from './statut-session-formation.enum';
import {SalarieRespDto} from '../salarie/salarie-resp-dto.model';
import {FormationResponseDTO} from '../formation/formation-resp-dto.model';
import {FinSessionFormationRespDTO} from './fin-session-formation-resp-dto.model';

/**
 * Classe mère des sessions de formation (présentiel et distanciel)
 */
export interface SessionFormationRespDTO {

  idSessionFormation: number | null;
  noYoda: string | null;
  libelleSessionFormation: string | null;
  statutYoda: string | null;
  dateDebutSession: Date | null;
  nbHeureSession: number | null;
  statutSessionFormation: StatutSessionFormation;
  salarie: { idSalarie:number };
  formation: { idFormation : number };
  finSessionFormation: { idFinSessionFormation : number } ;

}
