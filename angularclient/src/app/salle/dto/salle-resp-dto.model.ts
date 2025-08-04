import {AdresseResponseDTO} from '../../adresse/adresse-resp-dto.model';

export interface SalleResponseDto {

  idSalle: number | null;
  nomSalle:string | null;
  nomContact:string | null;
  portableContact:string | null;
  mailContact:string | null;
  cleSalle:boolean | null;
  digicodeSalle:string | null;
  commentaireSalle:string | null;
  adresse: AdresseResponseDTO;
}
