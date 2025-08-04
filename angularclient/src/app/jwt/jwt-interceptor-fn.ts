import { HttpInterceptorFn, HttpRequest, HttpHandlerFn, HttpEvent } from '@angular/common/http';
import { inject } from '@angular/core';
import { UserService } from '../user/services/user.service';
import { Router } from '@angular/router';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

export const jwtInterceptorFn: HttpInterceptorFn = (
  req: HttpRequest<any>,
  next: HttpHandlerFn
): Observable<HttpEvent<any>> => {
  const userService = inject(UserService);
  const router = inject(Router);
  const token = userService.getToken();

  console.log('[JwtInterceptorFn] Interception requête :', req.url, 'Token présent :', !!token);

  const authReq = token ? req.clone({
    headers: req.headers.set('Authorization', `Bearer ${token}`)
  }) : req;

  return next(authReq).pipe(
    catchError(err => {
      if (err.status === 401 || err.status === 403) {
        userService.logout();       // méthode hypothétique pour clear token et état user
        router.navigate(['/login']); // redirection vers login
      }
      return throwError(() => err);
    })
  );
};
