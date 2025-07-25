// session-formation-presentiel.service.ts

/**
 * Service qui permet d'interroger l'endpoint principal (GET toutes les sessions)
 */

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SessionFormationServicePresentiel {
  private apiUrl = 'http://localhost:8080/mediaskolFormation/sessionsFormations';

  constructor(private http: HttpClient) {}

  getSessions(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }


  searchSessions(termeRecherche: string): Observable<any[]> {
    const url = `${this.apiUrl}/recherche?termeRecherche=${encodeURIComponent(termeRecherche)}`;
    return this.http.get<any[]>(url);
  }

}
