import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {SalarieRespDTO} from '../dto/salarie-resp-dto.model';

@Injectable({providedIn: 'root'})
export class SalarieService {
  private url = 'http://localhost:8080/mediaskolFormation/salaries';

  constructor(private http: HttpClient) {
  }

  getSalaries(): Observable<SalarieRespDTO[]> {
    return this.http.get<SalarieRespDTO[]>(this.url);
  }

}
