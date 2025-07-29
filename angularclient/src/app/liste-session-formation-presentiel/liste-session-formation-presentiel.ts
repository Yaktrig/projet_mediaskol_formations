import {Component} from '@angular/core';
import {Footer} from "../footer/footer";
import {Header} from "../header/header";
import {FormsModule} from '@angular/forms';
import {SessionFormationPresentielService} from '../services/sessionFormation/session-formation-presentiel.service';
import {SessionFormationRespDTO} from '../dto/sessionFormation/session-formation-resp-dto.model';
import {StatutSessionFormationDetails} from '../dto/sessionFormation/statut-session-formation.enum';
import {StatutFinSessionFormationDetails} from '../dto/sessionFormation/statut-fin-session-formation-resp-dto.model';
import {DatePipe, JsonPipe} from '@angular/common';
import {RouterLink} from '@angular/router';
import {FirstLetterUpperPipe} from '../pipe/first-letter-upper.pipe';


@Component({
  selector: 'app-liste-session-formation-presentiel',
  imports: [
    FormsModule,
    Footer,
    Header,
    DatePipe,
    RouterLink,
    FirstLetterUpperPipe,

  ],
  templateUrl: './liste-session-formation-presentiel.html',
  styleUrls: ['./liste-session-formation-presentiel.css']
})
export class ListeSessionFormationPresentiel {

  searchTerm = '';
  sessions: SessionFormationRespDTO[] = [];


  // Expose la map pour y accéder dans le template
  statutSessionFormationDetails = StatutSessionFormationDetails;
  statutFinSessionFormationDetails =  StatutFinSessionFormationDetails;

  constructor(private sessionFopService: SessionFormationPresentielService,) {
  }

  ngOnInit() {
    this.sessionFopService.getSessions().subscribe({
      next: data => this.sessions = data,
      error: err => this.sessions = []
    });
  }

  onSearch(): void {
    if (!this.searchTerm.trim()) {
      this.sessionFopService.getSessions().subscribe({
        next: data => this.sessions = data,
        error: () => this.sessions = []
      });
    } else {
      this.sessionFopService.searchSessions(this.searchTerm).subscribe({
        next: data => this.sessions = data,
        error: () => this.sessions = []
      });
    }
  }



}
