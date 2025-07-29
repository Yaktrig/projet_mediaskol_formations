import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { FormationResponseDTO } from '../../dto/formation/formation-resp-dto.model';
import {SalarieRespDto} from '../../dto/salarie/salarie-resp-dto.model';

@Injectable({ providedIn: 'root' })
export class FormationService {
  private url = 'http://localhost:8080/mediaskolFormation/formations';

  constructor(private http: HttpClient) {}

  getFormations(): Observable<FormationResponseDTO[]> {
    return this.http.get<FormationResponseDTO[]>(this.url);
  }


}
