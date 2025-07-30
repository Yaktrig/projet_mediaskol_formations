// session-formation-presentiel.service.ts

/**
 * Service qui permet d'interroger l'endpoint principal (GET toutes les sessions)
 */

import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Observable, Subject, tap} from 'rxjs';
import {SessionFopRespDTO} from '../../dto/sessionFormation/session-formation-presentiel-resp-dto.model';

@Injectable({
  providedIn: 'root'
})
export class SessionFormationPresentielService {
  private apiUrl = 'http://localhost:8080/mediaskolFormation/sessionsFormationsPresentiels';


  constructor(private http: HttpClient) {
  }


  /**
   * Méthode qui appelle l'api pour retourner la liste des sessions de formation en présentiel
   */
  getSessions(): Observable<SessionFopRespDTO[]> {
    return this.http.get<SessionFopRespDTO[]>(this.apiUrl);
  }


  /**
   * Méthode qui appelle l'api pour rechercher une session de formation en présentiel selon certains champs définis
   * @param termeRecherche
   */
  searchSessions(termeRecherche: string): Observable<SessionFopRespDTO[]> {
    const url = `${this.apiUrl}/recherche?termeRecherche=${encodeURIComponent(termeRecherche)}`;
    return this.http.get<SessionFopRespDTO[]>(url);
  }

  /**
   * Méthode qui appelle l'api pour créer une nouvelle session de formation en présentiel
   */
  ajoutSessionFOP(sessionFormation: SessionFopRespDTO): Observable<any> {
    return this.http.post<SessionFopRespDTO>(this.apiUrl, sessionFormation);
  }

}
