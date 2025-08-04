import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';
import {ListeSessionFormationPresentiel} from './sessionFormation/components/liste-session-formation-presentiel/liste-session-formation-presentiel';
import {
  AjouterSessionFormationPresentiel
} from './sessionFormation/components/ajouter-session-formation-presentiel/ajouter-session-formation-presentiel';
import {UserGuard} from './guards/user-guard';
import {LoginComponent} from './login/components/login';
import {LoginRedirectGuard} from './guards/login-redirect.guard';
import {ListeFormation} from './formation/components/liste-formation/liste-formation';
import {AjouterFormation} from './formation/components/ajouter-formation/ajouter-formation';

export const routes: Routes = [
  {
    path: '',
    component: LoginComponent,
    canActivate: [LoginRedirectGuard]
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

   { path: 'listeFormations',
     component: ListeFormation,
   canActivate: [UserGuard]
   },

  { path: 'ajouterFormation',
    component: AjouterFormation,
    canActivate: [UserGuard]
  },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})

export class AppRoutingModule {
}
