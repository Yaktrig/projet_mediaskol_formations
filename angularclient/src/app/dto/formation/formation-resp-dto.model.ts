import {TypeFormationDTO} from './type-formation-resp-dto.model';

export interface FormationResponseDTO{

  idFormation: number |null;
  themeFormation: string |null;
  libelleFormation: string |null;
  typeFormation: TypeFormationDTO | null;
}
