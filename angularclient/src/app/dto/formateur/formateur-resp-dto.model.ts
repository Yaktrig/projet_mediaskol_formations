import {AdresseResponseDTO} from '../adresse/adresse-resp-dto.model';
import {FormationResponseDTO} from '../formation/formation-resp-dto.model';
import {TypeFormationDTO} from '../formation/type-formation-resp-dto.model';


export interface FormateurResponseDTO {

  idFormateur: number | null;
  nom: string | null;
  prenom: string | null;
  email: string | null;
  numPortable: string | null;
  statutFormateur: string;
  zoneIntervention: string | null;
  commentaireFormateur: string | null;
  adresse: AdresseResponseDTO | null;
  formations: FormationResponseDTO[] | null;
  typesFormations: TypeFormationDTO[] | null;
}
