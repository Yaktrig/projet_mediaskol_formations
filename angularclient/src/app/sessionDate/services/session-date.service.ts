import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {SessionDateReqDto} from '../dto/session-date-req-dto.model';

/**
 * Service qui permet de créer une session salle via l'API
 */
@Injectable({
  providedIn: 'root'
})
export class SessionDateService {
  private apiUrl = 'http://localhost:8080/mediaskolFormation';

  constructor(private http: HttpClient) {}


  /**
   * Méthode qui appelle l'api pour créer une nouvelle date pour une sesison de formation
   * @param sessionDate Les données de la session à créer
   * @returns Un observable contenant la session créée
   * */
 ajouterSessionsDate(sessionDateReq : SessionDateReqDto): Observable<any> {

  const url = `${this.apiUrl}/sessionsDates`;
  return this.http.post(url, sessionDateReq);
}
}
