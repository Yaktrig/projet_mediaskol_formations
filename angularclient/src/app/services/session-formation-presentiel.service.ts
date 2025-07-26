// session-formation-presentiel.service.ts

/**
 * Service qui permet d'interroger l'endpoint principal (GET toutes les sessions)
 */

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {SessionFormationRespDTO} from '../dto/sessionFormation/session-formation-resp-dto.model';

@Injectable({
  providedIn: 'root'
})
export class SessionFormationServicePresentiel {
  private apiUrl = 'http://localhost:8080/mediaskolFormation/sessionsFormations';

  constructor(private http: HttpClient) {}

  getSessions(): Observable<SessionFormationRespDTO[]> {
    return this.http.get<SessionFormationRespDTO[]>(this.apiUrl);
  }


  searchSessions(termeRecherche: string): Observable<SessionFormationRespDTO[]> {
    const url = `${this.apiUrl}/recherche?termeRecherche=${encodeURIComponent(termeRecherche)}`;
    return this.http.get<SessionFormationRespDTO[]>(url);
  }

}
