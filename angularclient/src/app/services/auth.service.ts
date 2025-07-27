import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://localhost:8080/mediaskolFormation/auth';
  private tokenKey = 'jwt-token';

  constructor(private http: HttpClient) {}

  login(pseudo: string, password: string) {
    return this.http.post<{token: string}>(this.baseUrl, { pseudo, password })
      .pipe(
        tap(response => {
          localStorage.setItem(this.tokenKey, response.token);
        })
      );
  }

  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  isLoggedIn(): boolean {
    return this.getToken() != null;
  }

  logout() {
    localStorage.removeItem(this.tokenKey);
  }
}
