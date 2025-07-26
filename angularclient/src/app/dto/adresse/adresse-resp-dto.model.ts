import {DepartementDTO} from './departement-resp-dto.model';

export interface AdresseResponseDTO {

  idAdresse: number | null;
  rue: string | null;
  codePostal: string | null;
  ville: string | null;
  departement: DepartementDTO | null
}
