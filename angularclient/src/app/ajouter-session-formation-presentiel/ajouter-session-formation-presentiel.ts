import {Component, OnInit} from '@angular/core';
import {Header} from "../header/header";
import {Footer} from '../footer/footer';
import {Router, RouterLink} from '@angular/router';
import {
  AjouterSessionFormationPresentielService
} from '../services/sessionFormation/ajouter-session-formation-presentiel.service';
import {MessageService} from '../services/message/message.service';
import {SessionFormationRespDTO} from '../dto/sessionFormation/session-formation-resp-dto.model';
import {FormationResponseDTO} from '../dto/formation/formation-resp-dto.model';
import {FormationService} from '../services/formation/formation.service';
import {FormsModule} from '@angular/forms';
import {SalarieService} from '../services/salarie/salarie.service';
import {SalarieRespDto} from '../dto/salarie/salarie-resp-dto.model';


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
  salaries: SalarieRespDto[] = [];
  idSalarieChoisi: number | null = null;

  constructor(
    private ajouterSessionFOP: AjouterSessionFormationPresentielService,
    private formationService: FormationService,
    private salarieService: SalarieService,
    private messageService: MessageService,
    private router: Router
  ) {
  }

  /**
   * Chargement des formations
   * Chargement des salariés
   */
  ngOnInit() {
    // Récupération de la liste des salariés
    this.formationService.getFormations().subscribe({
      next: (data) => {
        this.formations = data;
        // Optionnel : initialiser sélection première formation (ex)
        if (this.formations.length) {
          this.idFormationChoisie = this.formations[0].idFormation;
          // Récupérer le thème en fonction du libellé choisi
          this.updateThemeFormation();
        }
      },
      error: () => {
        this.messageService.showError("Il n'y a pas de formation à afficher, veuillez contacter le SI.")
      }
    });

    // Récupération de la liste des salariés
    this.salarieService.getSalaries().subscribe({
      next: (data) => {
        this.salaries = data;
        if (this.salaries.length) {
          this.idSalarieChoisi = this.salaries[0].idSalarie;
        }
      },
      error: () => {
        this.messageService.showError("Il n'y a pas de salarié à afficher, veuillez contacter le SI.")
      }
    })
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
