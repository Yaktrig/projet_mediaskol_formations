import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';
import {Home} from './home/home';

export const routes: Routes = [


  {path: '', component: Home}
  //{ path: 'apprenants', component: ListeApprenants},
  // { path: 'formations', component: ListeFormations},
  // { path: 'sessionsFormation', component: SessionFormations}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})

export class AppRoutingModule { }
