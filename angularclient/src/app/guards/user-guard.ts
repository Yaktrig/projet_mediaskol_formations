import {
  CanActivate,
  Router
} from '@angular/router';
import {Injectable} from '@angular/core';
import {UserService} from '../services/user/user.service';

@Injectable({
  providedIn: 'root'
})
export class UserGuard implements CanActivate {

  constructor(private auth: UserService, private router: Router) {
  }

  canActivate(): boolean {
    if (!this.auth.isLoggedIn()) {
      this.router.navigate(['']);
      return false;
    }
    // this.router.navigate(['/login']); // redirige vers une page de login si non authentifié
    return true;
  }
}
