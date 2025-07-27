// session-formation-presentiel.service.ts

/**
 * Service qui permet d'interroger l'endpoint principal (GET toutes les sessions)
 */

import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Observable, Subject, tap} from 'rxjs';
import {SessionFormationRespDTO} from '../dto/sessionFormation/session-formation-resp-dto.model';

@Injectable({
  providedIn: 'root'
})
export class SessionFormationServicePresentiel {
  private apiUrl = 'http://localhost:8080/mediaskolFormation/sessionsFormations';


  constructor(private http: HttpClient) {
  }


  /**
   * Méthode qui appelle l'api pour retourner la liste des sessions de formation
   */
  getSessions(): Observable<SessionFormationRespDTO[]> {
    return this.http.get<SessionFormationRespDTO[]>(this.apiUrl);
  }


  /**
   * Méthode qui appelle l'api pour rechercher une session de formation selon certains champs définis
   * @param termeRecherche
   */
  searchSessions(termeRecherche: string): Observable<SessionFormationRespDTO[]> {
    const url = `${this.apiUrl}/recherche?termeRecherche=${encodeURIComponent(termeRecherche)}`;
    return this.http.get<SessionFormationRespDTO[]>(url);
  }

  /**
   * Méthode qui appelle l'api pour créer une nouvelle session de formation en présentiel
   */
  ajoutSessionFOP(sessionFormation: SessionFormationRespDTO): Observable<any> {
    return this.http.post<SessionFormationRespDTO>(this.apiUrl, sessionFormation);
  }

}
