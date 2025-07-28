import {Component, OnInit} from '@angular/core';
import {Header} from "../header/header";
import {Footer} from '../footer/footer';
import {Router, RouterLink} from '@angular/router';
import {AjouterSessionFormationPresentielService} from '../services/ajouter-session-formation-presentiel.service';
import {MessageService} from '../services/message.service';
import {SessionFormationRespDTO} from '../dto/sessionFormation/session-formation-resp-dto.model';
import {FormationResponseDTO} from '../dto/formation/formation-resp-dto.model';
import {FormationService} from '../services/formation.service';
import {FormsModule} from '@angular/forms';


@Component({
  selector: 'app-ajouter-session-formation-presentiel',
  imports: [
    Header,
    Footer,
    RouterLink,
    FormsModule
  ],
  templateUrl: './ajouter-session-formation-presentiel.html',
  styleUrls: ['./ajouter-session-formation-presentiel.css']
})
export class AjouterSessionFormationPresentiel implements OnInit {

  formations: FormationResponseDTO[] = [];
  idFormationChoisie: number | null = null;
  themeFormationSelectionnee: string | null | undefined;

  constructor(
    private ajouterSessionFOP: AjouterSessionFormationPresentielService,
    private formationService: FormationService,
    private messageService: MessageService,
    private router: Router
  ) {
  }

  ngOnInit() {
    this.formationService.getFormations().subscribe({
      next: (data) => {
        this.formations = data;
        // Optionnel : initialiser sélection première formation (ex)
        if (this.formations.length) {
          this.idFormationChoisie = this.formations[0].idFormation;
          this.updateThemeFormation();
        }
      },
      error: () => {
        // message d'erreur
      }
    });
  }

  ajouterSession(sessionDto: SessionFormationRespDTO) {
    this.ajouterSessionFOP.ajoutSessionFOP(sessionDto).subscribe({
      next: (resp) => {
        this.messageService.showSuccess(resp.message || 'Session ajoutée avec succès.');
        this.router.navigate(["listeSessionFormationPresentiel"]);
      },
      error: (err) => {
        const errMsg = err.error?.message || 'Erreur lors de l\'ajout de la session.';
        this.messageService.showError(errMsg);
      }
    });
  }

  // Méthode appelée à chaque changement dans la sélection
  updateThemeFormation() {
    if (this.idFormationChoisie != null) {
      const formationTrouvee = this.formations.find(f => f.idFormation === this.idFormationChoisie);
      this.themeFormationSelectionnee = formationTrouvee ? formationTrouvee.themeFormation : '';
    } else {
      this.themeFormationSelectionnee = '';
    }
  }

}
