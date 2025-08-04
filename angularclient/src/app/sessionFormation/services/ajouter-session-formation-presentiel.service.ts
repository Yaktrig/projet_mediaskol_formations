import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {SessionFopReqDTO} from '../dto/session-formation-presentiel-req-dto.model';


/**
 * Service qui permet de créer une session de formation en présentiel via l'API
 */

@Injectable({
  providedIn: 'root'
})
export class AjouterSessionFormationPresentielService {
  private apiUrl = 'http://localhost:8080/mediaskolFormation';


  constructor(private http: HttpClient) {}

  /**
   * Méthode qui appelle l'api pour créer une nouvelle session de formation en présentiel
   * @param sessionReq Les données de la session à créer
   * @returns Un observable contenant la session créée
   * */
  ajoutSessionFOP(sessionReq : SessionFopReqDTO): Observable<any> {

    const url = `${this.apiUrl}/sessionsFormationsPresentiels`;
    return this.http.post(url, sessionReq);

  }

}
