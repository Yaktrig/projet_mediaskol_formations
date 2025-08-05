import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';
import {ListeSessionFormationPresentiel} from './sessionFormation/components/liste-session-formation-presentiel/liste-session-formation-presentiel';
import {
  AjouterSessionFormationPresentiel
} from './sessionFormation/components/ajouter-session-formation-presentiel/ajouter-session-formation-presentiel';
import {UserGuard} from './guards/user-guard';
import {LoginComponent} from './login/components/login';
import {LoginRedirectGuard} from './guards/login-redirect.guard';
import {FormationListe} from './formation/components/formation-liste/formation-liste';
import {FormationAjouter} from './formation/components/formation-ajouter/formation-ajouter';

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
     component: FormationListe,
   canActivate: [UserGuard]
   },

  { path: 'ajouterFormation',
    component: FormationAjouter,
    canActivate: [UserGuard]
  },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})

export class AppRoutingModule {
}
