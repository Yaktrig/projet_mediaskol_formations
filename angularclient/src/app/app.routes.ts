import {RouterModule, Routes} from '@angular/router';
import {ListeApprenantsComponent} from './components/liste-apprenants/liste-apprenants';
import {NgModule} from '@angular/core';

export const routes: Routes = [


  { path: '', redirectTo: '/', pathMatch: 'full' },
  { path: 'apprenants', component: ListeApprenantsComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})

export class AppRoutingModule { }
