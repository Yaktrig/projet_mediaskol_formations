import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {FormationReqDTO} from '../dto/formation-req-dto.model';

@Injectable({
  providedIn: 'root'
})
export class AjouterFormationService {

  private apiUrl ='http://localhost:8080/mediaskolFormation';

  constructor(private http: HttpClient) {
  }

  /**
   * Méthode qui appelle l'api pour créer une nouvelle session de formation en présentiel
   * @param formationReq Les données de la session à créer
   * @returns Un observable contenant la session créée
   * */
  ajoutFormation(formationReq : FormationReqDTO): Observable<any> {

    const url = `${this.apiUrl}/formations`;
    return this.http.post(url, formationReq);

  }
}
