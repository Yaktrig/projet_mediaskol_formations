import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';
import {ListeSessionFormationPresentiel} from './sessionFormation/liste-session-formation-presentiel/liste-session-formation-presentiel';
import {
  AjouterSessionFormationPresentiel
} from './sessionFormation/ajouter-session-formation-presentiel/ajouter-session-formation-presentiel';
import {HTTP_INTERCEPTORS} from '@angular/common/http';
import {JwtInterceptor} from './jwt/jwt-interceptor';
import {UserGuard} from './guards/user-guard';
import {LoginComponent} from './login/login';

export const routes: Routes = [
  {
    path: '',
    component: LoginComponent
  },
  {
    path: 'listeSessionFormationPresentiel',
    component: ListeSessionFormationPresentiel,
    canActivate: [UserGuard]
  },
  {
    path: 'ajouterSessionFormationPresentiel',
    component: AjouterSessionFormationPresentiel,
    canActivate: [UserGuard]
  },

  //{ path: 'apprenants', component: ListeApprenants},
  // { path: 'formations', component: ListeFormations},
  // { path: 'sessionsFormation', component: SessionFormations}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true},
  ]
})

export class AppRoutingModule {
}
