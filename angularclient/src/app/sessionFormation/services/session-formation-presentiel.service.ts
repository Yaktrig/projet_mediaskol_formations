// session-formation-presentiel.service.ts

/**
 * Service qui permet d'interroger l'endpoint principal (GET toutes les sessions)
 */

import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {SessionFopRespDTO} from '../dto/session-formation-presentiel-resp-dto.model';

@Injectable({
  providedIn: 'root'
})
export class SessionFormationPresentielService {
  private apiUrl = 'http://localhost:8080/mediaskolFormation';


  constructor(private http: HttpClient) {
  }


  /**
   * Méthode qui appelle l'api pour retourner la liste des sessions de formation en présentiel
   */
  getSessionsPresentiel(): Observable<SessionFopRespDTO[]> {
    const url = `${this.apiUrl}/sessionsFormationsPresentiels`; // ou /list selon
    return this.http.get<SessionFopRespDTO[]>(url);
  }

  /**
   * Méthode qui appel l'api pour retourner la liste des sessions de formation en présentiel qui contiennent
   * moins de 6 sessions apprenants
   */
  getSessionsWithLessSixSa() :  Observable<SessionFopRespDTO[]> {
    const url = `${this.apiUrl}/sessionsFormationsPresentiels/moinsSixSessionsApprenants`;
    return this.http.get<SessionFopRespDTO[]>(url);
  }

  /**
   * Méthode qui appelle l'api pour rechercher une session de formation en présentiel selon certains champs définis
   * @param termeRecherche
   */
  searchSessions(termeRecherche: string): Observable<SessionFopRespDTO[]> {
    const url = `${this.apiUrl}/sessionsFormationsPresentiels/recherche?termeRecherche=${encodeURIComponent(termeRecherche)}`;
    return this.http.get<SessionFopRespDTO[]>(url);
  }


}
