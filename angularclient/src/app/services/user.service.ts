import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {tap} from 'rxjs/operators';
import {firstValueFrom, Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private baseUrl = 'http://localhost:8080/mediaskolFormation/auth';
  private registerUrl = 'http://localhost:8080/enregistrerUtilisateur';
  private tokenKey = 'jwt-token';
  private usernameKey = 'pseudo';
  private passwordKey = 'password';

  constructor(private http: HttpClient) {
  }

  login(pseudo: string, password: string, rememberMe: boolean): Observable<{ token: string }> {
    return this.http.post<{ token: string }>(this.baseUrl, {pseudo, password})
      .pipe(
        tap(response => {
          localStorage.setItem(this.tokenKey, response.token);
          localStorage.setItem(this.usernameKey, pseudo);
          if (rememberMe) {
            localStorage.setItem(this.passwordKey, password);
          } else {
            localStorage.removeItem(this.passwordKey);
          }
        })
      );
  }

  getUsername(): string | null {
    return localStorage.getItem(this.usernameKey);
  }

  async register(login: string, password: string): Promise<boolean> {
    const data = {login, password};
    try {
      await firstValueFrom(this.http.post(this.registerUrl, data));
      return true;
    } catch (error: any) {
      if (error.status === 409) {
        return Promise.reject("L'utilisateur existe déjà");
      }
      return Promise.reject(error.message);
    }
  }



  async autologin(): Promise<boolean> {
    const username = localStorage.getItem(this.usernameKey);
    const password = localStorage.getItem(this.passwordKey);

    if (username && password) {
      try {
        await firstValueFrom(this.login(username, password, true));
        return true;
      } catch {
        return false;
      }
    }
    return false;
  }

  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  isLoggedIn(): boolean {
    return this.getToken() != null;
  }

  logout() {
    localStorage.removeItem(this.tokenKey);
    localStorage.removeItem(this.usernameKey);
    localStorage.removeItem(this.passwordKey);
  }
}

