import {Component} from '@angular/core';
import {Footer} from "../footer/footer";
import {Header} from "../header/header";
import {FormsModule} from '@angular/forms';
import {SessionFormationServicePresentiel} from '../services/session-formation-presentiel.service';


@Component({
  selector: 'app-liste-session-formation-presentiel',
  imports: [
    FormsModule,
    Footer,
    Header,

  ],
  templateUrl: './liste-session-formation-presentiel.html',
  styleUrl: './liste-session-formation-presentiel.css'
})
export class ListeSessionFormationPresentiel {

  searchTerm = '';
  sessions: any[] = [];

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
