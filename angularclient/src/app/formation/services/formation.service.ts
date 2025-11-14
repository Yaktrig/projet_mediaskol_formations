import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { FormationResponseDTO } from '../dto/formation-resp-dto.model';
import {FormationReqDTO} from '../dto/formation-req-dto.model';
import {FormationUpdateDTO} from '../dto/formation-update-dto.model';
import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class FormationService {
  private apiUrl = `${environment.apiUrl}/mediaskolFormation/formations`;

  constructor(private http: HttpClient) {}

  getFormations(): Observable<FormationResponseDTO[]> {
    return this.http.get<FormationResponseDTO[]>(this.apiUrl);
  }

  /**
   * Méthode qui appelle l'api pour créer une nouvelle formation
   * @param formationReq Les données de la formation à créer
   * @returns Un observable contenant la formation créée
   * */
  ajoutFormation(formationReq : FormationReqDTO): Observable<any> {

    return this.http.post(this.apiUrl, formationReq);
  }


  /**
   * Méthode qui appelle l'api pour modifier une formation
   * @returns Un observable contenant la formation modifiée
   * @param formationUpdate
   * */
  updateFormation(formationUpdate : FormationUpdateDTO): Observable<any> {

    return this.http.put(this.apiUrl, formationUpdate);
  }

  /**
   * Méthode qui appelle l'api pour supprimer une formation
   * @param idFormation
   */
  deleteFormation(idFormation: number | null | undefined): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${idFormation}`);
  }

}
