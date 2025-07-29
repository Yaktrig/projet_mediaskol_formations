import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {SalarieRespDto} from '../../dto/salarie/salarie-resp-dto.model';

@Injectable({providedIn: 'root'})
export class SalarieService {
  private url = 'http://localhost:8080/mediaskolFormation/salaries';

  constructor(private http: HttpClient) {
  }

  getSalaries(): Observable<SalarieRespDto[]> {
    return this.http.get<SalarieRespDto[]>(this.url);
  }

}
