import {Component, OnInit} from '@angular/core';
import {Apprenant} from '../../models/apprenant';
import {ApprenantService} from '../../services/apprenant';



@Component({
  selector: 'app-liste-apprenants',
  imports: [

  ],
  templateUrl: './liste-apprenants.html',
  styleUrl: './liste-apprenants.css'
})
export class ListeApprenantsComponent implements OnInit {

  apprenants: Apprenant[] = [];

  constructor(private apprenantService: ApprenantService) {
  }

  ngOnInit() {
    this.apprenantService.getAll().subscribe({
      next: (data) => this.apprenants = data,
      error: (error) => console.error(error)
    })
  }




}
