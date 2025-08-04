import {TypeFormationDTO} from '../../formation/dto/type-formation-resp-dto.model';
import {AdresseResponseDTO} from '../../adresse/adresse-resp-dto.model';
import {StatutNumPasseport} from './statut-num-passeport.enum';

export interface ApprenantResponseDTO {
  idApprenant: number | null;
  nom: string | null;
  prenom: string | null;
  email: string | null;
  numPortable: string | null;
  dateNaissance: string | null;
  numPasseport: string | null;
  statutNumPasseport: StatutNumPasseport;
  commentaireApprenant: string | null;
  apprenantActif: boolean | null;
  adresse: AdresseResponseDTO | null;
  typesFormations: TypeFormationDTO[] | null;
}


