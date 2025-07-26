import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';
import {ListeSessionFormationPresentiel} from './liste-session-formation-presentiel/liste-session-formation-presentiel';
import {AjouterSessionFormationPresentiel} from './ajouter-session-formation-presentiel/ajouter-session-formation-presentiel';

export const routes: Routes = [


  {path: '', component: ListeSessionFormationPresentiel},
  {path: 'ajouterSessionFormationPresentiel', component: AjouterSessionFormationPresentiel},
  //{ path: 'apprenants', component: ListeApprenants},
  // { path: 'formations', component: ListeFormations},
  // { path: 'sessionsFormation', component: SessionFormations}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})

export class AppRoutingModule { }
