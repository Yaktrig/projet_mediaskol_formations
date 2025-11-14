import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {Apprenant} from '../models/apprenant';

@Injectable({
  providedIn: 'root'
})
export class ApprenantService {

  private apiUrl = 'http://localhost:8080/mediaskolFormation/apprenants';

  constructor(private http: HttpClient) { }

  getAll() : Observable<Apprenant[]> {
    return this.http.get<Apprenant[]>(`${this.apiUrl}`);
  }

  add(apprenant: Apprenant): Observable<Apprenant> {
    return this.http.post<Apprenant>(`${this.apiUrl}`, apprenant);
  }

}
