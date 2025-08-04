import {Component} from '@angular/core';
import {Footer} from "../../../footer/footer";
import {Header} from "../../../header/header";
import {FormsModule} from '@angular/forms';
import {SessionFormationPresentielService} from '../../services/session-formation-presentiel.service';
import {SessionFopRespDTO} from '../../dto/session-formation-presentiel-resp-dto.model';
import {StatutSessionFormationDetails} from '../../dto/statut-session-formation.enum';
import {StatutFinSessionFormationDetails} from '../../dto/statut-fin-session-formation-resp-dto.model';
import {DatePipe} from '@angular/common';
import {RouterLink} from '@angular/router';
import {FirstLetterUpperPipe} from '../../../pipe/first-letter-upper.pipe';
import {StatutSessionFormateurDetails} from '../../../formateur/dto/statut-session-formateur.enum';
import {StatutSessionSalleDetails} from '../../../sessionSalle/dto/statut-session-salle.enum';
import {MatIconModule} from '@angular/material/icon';



@Component({
  selector: 'app-liste-session-formation-presentiel',
  imports: [
    FormsModule,
    Footer,
    Header,
    DatePipe,
    RouterLink,
    FirstLetterUpperPipe,
    MatIconModule
  ],
  templateUrl: './liste-session-formation-presentiel.html',
  styleUrls: ['./liste-session-formation-presentiel.css']
})
export class ListeSessionFormationPresentiel {

  searchTerm = '';
  sessions: SessionFopRespDTO[] = [];
  // Optionnel : meilleure performance pour *ngFor des sessions
  isChecked: any;

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
    this.sessionFopService.getSessionsPresentiel().subscribe({
      next: (data: SessionFopRespDTO[]) => this.sessions = data,
      error: (err) => console.error('Erreur récupération sessions', err)

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

  /**
   * Méthode qui vérifie si la checkbox est cochée et appelle la méthode qui renvoie la liste des sessions
   * de formation qui ont moins de SIX sessions d'apprenants.
   * Sinon, on appelle la méthode qui charge la totalité des sessions de formations
   */
  handleChange() {
    if(this.isChecked){
      console.log("case cochée");
      this.sessionFopService.getSessionsWithLessSixSa().subscribe({
        next: (data: SessionFopRespDTO[]) => this.sessions = data,
        error: err => this.sessions = []
      })
    } else {
      console.log("case décochée");
      this.loadSessions();
    }
  }
}
