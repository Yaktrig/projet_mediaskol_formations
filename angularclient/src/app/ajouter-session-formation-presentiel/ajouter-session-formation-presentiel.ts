import { Component } from '@angular/core';
import {Header} from "../header/header";
import {Footer} from '../footer/footer';
import {RouterLink} from '@angular/router';
import {SessionFormationServicePresentiel} from '../services/session-formation-presentiel.service';
import {MessageService} from '../services/message.service';
import {SessionFormationRespDTO} from '../dto/sessionFormation/session-formation-resp-dto.model';

@Component({
  selector: 'app-ajouter-session-formation-presentiel',
  imports: [
    Header,
    Footer,
    RouterLink
  ],
  templateUrl: './ajouter-session-formation-presentiel.html',
  styleUrl: './ajouter-session-formation-presentiel.css'
})
export class AjouterSessionFormationPresentiel {

  constructor(
    private sessionService: SessionFormationServicePresentiel,
    private messageService: MessageService
  ) {}

  ajouterSession(sessionDto: SessionFormationRespDTO) {
    this.sessionService.ajoutSessionFOP(sessionDto).subscribe({
      next: (resp) => {
        this.messageService.showSuccess(resp.message || 'Session ajoutée avec succès.');
      },
      error: (err) => {
        const errMsg = err.error?.message || 'Erreur lors de l\'ajout de la session.';
        this.messageService.showError(errMsg);
      }
    });
  }
}
