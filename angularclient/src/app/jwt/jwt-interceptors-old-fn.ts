import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { UserService } from '../services/user/user.service';  // adapte le chemin ici

export const jwtInterceptorFn: HttpInterceptorFn = (req, next) => {

  const userService = inject(UserService); // injection à la volée
  const token = userService.getToken();

  console.log('[JwtInterceptorFn] Interception requête :', req.url, 'Token présent :', !!token);

  if (token) {
    const cloned = req.clone({
      headers: req.headers.set('Authorization', `Bearer ${token}`)
    });
    return next(cloned);
  }

  return next(req);
};
