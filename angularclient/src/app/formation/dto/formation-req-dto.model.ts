import {TypeFormationDTO} from './type-formation-resp-dto.model';

export interface FormationReqDTO{

  idFormation: number |null;
  themeFormation: string |null;
  libelleFormation: string |null;
  typeFormation: {idTypeFormation : number} | null;
}
