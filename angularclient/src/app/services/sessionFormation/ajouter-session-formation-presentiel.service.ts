
/**
 * Service qui permet d'interroger l'endpoint principal (GET toutes les sessions)
 */
// session-formation-presentiel.service.ts

/**
 * Service qui permet d'interroger l'endpoint principal (GET toutes les sessions)
 */

import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Observable, Subject, tap} from 'rxjs';
import {SessionFormationRespDTO} from '../../dto/sessionFormation/session-formation-resp-dto.model';

@Injectable({
  providedIn: 'root'
})
export class AjouterSessionFormationPresentielService {
  private apiUrl = 'http://localhost:8080/mediaskolFormation/sessionsFormations';


  constructor(private http: HttpClient) {
  }

  /**
   * Méthode qui appelle l'api pour créer une nouvelle session de formation en présentiel
   */
  ajoutSessionFOP(sessionFormation: SessionFormationRespDTO): Observable<{message?:string}> {
    return this.http.post<{ message?:string }>(this.apiUrl, sessionFormation);
  }

}
