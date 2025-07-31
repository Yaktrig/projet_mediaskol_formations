import {Component, OnInit} from '@angular/core';
import {Header} from "../../header/header";
import {Footer} from '../../footer/footer';
import {Router, RouterLink} from '@angular/router';
import {
  AjouterSessionFormationPresentielService
} from '../../services/sessionFormation/ajouter-session-formation-presentiel.service';
import {MessageService} from '../../services/message/message.service';
import {SessionFopRespDTO} from '../../dto/sessionFormation/session-formation-presentiel-resp-dto.model';
import {FormationResponseDTO} from '../../dto/formation/formation-resp-dto.model';
import {FormationService} from '../../services/formation/formation.service';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {SalarieService} from '../../services/salarie/salarie.service';
import {SalarieRespDto} from '../../dto/salarie/salarie-resp-dto.model';
import {DepartementService} from '../../services/departement/departement.service';
import {DepartementDTO} from '../../dto/adresse/departement-resp-dto.model';


@Component({
  selector: 'app-ajouter-session-formation-presentiel',
  imports: [
    Header,
    Footer,
    RouterLink,
    FormsModule,
    ReactiveFormsModule
  ],
  templateUrl: './ajouter-session-formation-presentiel.html',
  styleUrls: ['./ajouter-session-formation-presentiel.css']
})
export class AjouterSessionFormationPresentiel implements OnInit {

  formations: FormationResponseDTO[] = [];
  departements: DepartementDTO[] = [];
  salaries: SalarieRespDto[] = [];

  libelleFormationSelectionnee: string | null = '';
  idFormationChoisie: number | null = null;
  idDepartementChoisi: number | null = null;
  idSalarieChoisi: number | null = null;

  /**
   * Initialisation du formulaire
   */
  sessionPresentielForm!: FormGroup;

  constructor(
    private ajouterSessionFOP: AjouterSessionFormationPresentielService,
    private formationService: FormationService,
    private salarieService: SalarieService,
    private messageService: MessageService,
    private departementService: DepartementService,
    private router: Router,
    private fb: FormBuilder,
  ) {
  }

  /**
   * Instanciation du formulaire
   * Chargement des formations
   * Chargement des salariés
   */
  ngOnInit() {
    // Formulaire
    this.sessionPresentielForm = this.fb.group({
      idFormationChoisie: [null, Validators.required],
      idDepartementChoisi: [null, Validators.required],
      idSalarieChoisi: [null, Validators.required],
      libelleFormationSelectionnee: [null, Validators.required],

      noYoda: ['', Validators.required],
      intituleSessionF: ['', Validators.required],
      BI: ['', Validators.required],
      dateLimiteYoda: ['', Validators.required],
      libelleSessionF: ['', Validators.required],
      lieuFormation: ['', Validators.required],
      dureeFormation: ['', Validators.required],
      commanditaire: ['', Validators.required],
      RPE: ['', Validators.required],
      nbJournee: ['', Validators.required],
      dateJournee1: ['', Validators.required],
      formateurJourneeN: ['', Validators.required],
      salleJourneeN: ['', Validators.required],
    })


    /**
     * Récupération de la liste des formations en présentiel
     * Le thème peut être choisi et le libellé de la formation s'affiche dynamiquement
     */
    this.formationService.getFormations().subscribe({
      next: (data) => {
        this.formations = data.filter(f => f.typeFormation?.libelle === 'Présentiel');
        // Optionnel : initialiser sélection première formation (ex)
        if (this.formations.length > 0) {
          this.sessionPresentielForm.patchValue({
            idFormationChoisie: this.formations[0].idFormation
          });
          // initialisation du libellé
          this.libelleFormationSelectionnee = this.formations[0].libelleFormation
        }
      },
      error: () => {
        this.messageService.showError("Il n'y a pas de formation à afficher, veuillez contacter le SI.")
      }
    });

    /**
     *  Abonnement sur la valeur idFormationChoisie pour mettre à jour le libellé automatiquement
     */
    this.sessionPresentielForm.get('idFormationChoisie')?.valueChanges.subscribe(val => {
      const formationTrouvee = this.formations.find(f => f.idFormation === Number(val));
      this.libelleFormationSelectionnee = formationTrouvee ? formationTrouvee.libelleFormation : '';
    });

    /**
     *   Récupération de la liste des salariés
     */
    this.salarieService.getSalaries().subscribe({
      next: (data) => {
        this.salaries = data;
        if (this.salaries.length) {
          this.sessionPresentielForm.patchValue({
            idSalarieChoisi: this.salaries[0].idSalarie
          })
        }
      },
      error: () => {
        this.messageService.showError("Il n'y a pas de salarié à afficher, veuillez contacter le SI.")
      }
    })

    /**
     * Récupération de la liste des départements de Bretagne
     */
    this.departementService.getDepartementBzh().subscribe({
      next: (data) => {
        this.departements = data;
        if (this.formations.length) {
          this.sessionPresentielForm.patchValue({
            idDepartementChoisi: this.departements[0].idDepartement

          });
          console.log('Départements chargés:', this.departements);
        }
      },
      error: () => {
        this.messageService.showError("Il n'y a pas de numéro de département à afficher, veuillez contacter le SI.")
      }
    })
  }

  /**
   * Boucle sur les journées de session de formation. Affiche dynamiquement en fonction du nombre de jours choisis,
   * un template avec : une date, une liste de formateurs disponibles à cette date, une liste de salle disponiblent à
   * cette date.
   */


  /**
   * Méthode qui ajoute la session de formation lorsque l'utilisateur clique sur Enregistrer
   */
  onSubmit(): void {
    if (this.sessionPresentielForm.valid) {
      const sessionData = this.sessionPresentielForm.value;
      // Appelle ton service pour envoyer sessionData
      this.ajouterSession(sessionData);
      console.log("Données du formulaire prêtes à être envoyées :", sessionData);
    } else {
      this.sessionPresentielForm.markAllAsTouched(); // pour afficher les erreurs
    }
  }

  /**
   * Méthode qui appelle l'api pour ajouter la session de formation en présentiel
   * @param sessionFopDto
   */
  ajouterSession(sessionFopDto: SessionFopRespDTO) {
    this.ajouterSessionFOP.ajoutSessionFOP(sessionFopDto).subscribe({
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


  protected readonly onsubmit = onsubmit;
}
