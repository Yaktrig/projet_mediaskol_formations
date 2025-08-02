import { bootstrapApplication } from '@angular/platform-browser';
import { provideRouter } from '@angular/router';
import { provideHttpClient, withInterceptors } from '@angular/common/http';


import { routes } from './app/app.routes';
import {App} from './app/app';
import {jwtInterceptorFn} from './app/jwt/jwt-interceptors-old-fn';

bootstrapApplication(App, {
  providers: [
    provideRouter(routes),
    provideHttpClient(withInterceptors([jwtInterceptorFn])),
    // autres providers ici (ex : services)
  ]
});


