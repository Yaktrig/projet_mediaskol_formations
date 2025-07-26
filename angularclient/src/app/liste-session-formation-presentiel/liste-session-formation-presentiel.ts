import {Component} from '@angular/core';
import {Footer} from "../footer/footer";
import {Header} from "../header/header";
import {FormsModule} from '@angular/forms';
import {SessionFormationServicePresentiel} from '../services/session-formation-presentiel.service';
import {SessionFormationRespDTO} from '../dto/sessionFormation/session-formation-resp-dto.model';
import {StatutSessionFormationDetails} from '../dto/sessionFormation/statut-session-formation.enum';
import {StatutFinSessionFormationDetails} from '../dto/sessionFormation/statut-fin-session-formation-resp-dto.model';
import {DatePipe} from '@angular/common';
import {RouterLink} from '@angular/router';


@Component({
  selector: 'app-liste-session-formation-presentiel',
  imports: [
    FormsModule,
    Footer,
    Header,
    DatePipe,
    RouterLink,

  ],
  templateUrl: './liste-session-formation-presentiel.html',
  styleUrl: './liste-session-formation-presentiel.css'
})
export class ListeSessionFormationPresentiel {

  searchTerm = '';
  sessions: SessionFormationRespDTO[] = [];


  // Expose la map pour y accéder dans le template
  statutSessionFormationDetails = StatutSessionFormationDetails;
  statutFinSessionFormationDetails =  StatutFinSessionFormationDetails;

  constructor(private sessionService: SessionFormationServicePresentiel,) {
  }

  ngOnInit() {
    this.sessionService.getSessions().subscribe({
      next: data => this.sessions = data,
      error: err => this.sessions = []
    });
  }

  onSearch(): void {
    if (!this.searchTerm.trim()) {
      this.sessionService.getSessions().subscribe({
        next: data => this.sessions = data,
        error: () => this.sessions = []
      });
    } else {
      this.sessionService.searchSessions(this.searchTerm).subscribe({
        next: data => this.sessions = data,
        error: () => this.sessions = []
      });
    }
  }



}
