import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {DepartementDTO} from '../dto/departement-resp-dto.model';
import {Injectable} from '@angular/core';


@Injectable({providedIn: 'root'})
export class DepartementService {

  private apiUrl = 'http://localhost:8080/mediaskolFormation/departements';

  constructor(private http: HttpClient) {}

  getDepartementBzh(): Observable<DepartementDTO[]> {
    const url = `${this.apiUrl}/bretagne`;
    return this.http.get<DepartementDTO[]>(url);
  }
}
