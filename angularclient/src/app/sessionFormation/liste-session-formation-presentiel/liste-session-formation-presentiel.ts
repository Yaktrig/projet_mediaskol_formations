import {Component} from '@angular/core';
import {Footer} from "../../footer/footer";
import {Header} from "../../header/header";
import {FormsModule} from '@angular/forms';
import {SessionFormationPresentielService} from '../../services/sessionFormation/session-formation-presentiel.service';
import {SessionFopRespDTO} from '../../dto/sessionFormation/session-formation-presentiel-resp-dto.model';
import {StatutSessionFormationDetails} from '../../dto/sessionFormation/statut-session-formation.enum';
import {StatutFinSessionFormationDetails} from '../../dto/sessionFormation/statut-fin-session-formation-resp-dto.model';
import {DatePipe} from '@angular/common';
import {RouterLink} from '@angular/router';
import {FirstLetterUpperPipe} from '../../pipe/first-letter-upper.pipe';
import {StatutSessionFormateurDetails} from '../../dto/formateur/statut-session-formateur.enum';
import {StatutSessionSalleDetails} from '../../dto/salle/statut-session-salle.enum';



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
  sessions: SessionFopRespDTO[] = [];

  // Expose la map pour y accéder dans le template
  statutSessionFormationDetails = StatutSessionFormationDetails;
  statutFinSessionFormationDetails =  StatutFinSessionFormationDetails;
  statutSessionFormateurDetails = StatutSessionFormateurDetails;
  statutSessionSalleDetails = StatutSessionSalleDetails;

  constructor(private sessionFopService: SessionFormationPresentielService,) {
  }

  ngOnInit() {
    this.loadSessions();
  }

  loadSessions() {
    this.sessionFopService.getSessions().subscribe({
      next: (data: SessionFopRespDTO[]) => this.sessions = data,
      error: err => this.sessions = []
    });
  }
  onSearch(): void {
    if (!this.searchTerm.trim()) {
      this.loadSessions();
    } else {
      this.sessionFopService.searchSessions(this.searchTerm).subscribe({
        next: (data: SessionFopRespDTO[]) => this.sessions = data,
        error: () => this.sessions = []
      });
    }
  }

  // Optionnel : meilleure performance pour *ngFor des sessions
  trackBySessionId(index: number, session: SessionFopRespDTO): number {
    return <number>session.idSessionFormation;
  }

}
