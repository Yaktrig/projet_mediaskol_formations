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
import {
  FormArray,
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';
import {SalarieService} from '../../services/salarie/salarie.service';
import {SalarieRespDto} from '../../dto/salarie/salarie-resp-dto.model';
import {DepartementService} from '../../services/departement/departement.service';
import {DepartementDTO} from '../../dto/adresse/departement-resp-dto.model';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';


@Component({
  selector: 'app-ajouter-session-formation-presentiel',
  standalone: true,
  imports: [
    Header,
    Footer,
    RouterLink,
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule

  ],
  templateUrl: './ajouter-session-formation-presentiel.html',
  styleUrls: ['./ajouter-session-formation-presentiel.css']
})
export class AjouterSessionFormationPresentiel implements OnInit {


  sessionPresentielForm!: FormGroup;
  formations: FormationResponseDTO[] = [];
  departements: DepartementDTO[] = [];
  salaries: SalarieRespDto[] = [];

  libelleFormationSelectionnee: string | null = '';
  idFormationChoisie: number | null = null;
  idDepartementChoisi: number | null = null;
  idSalarieChoisi: number | null = null;


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
   * Initialisation du formulaire
   * Chargement des formations
   * Chargement des salariés
   * Chargement des numéros de départements en Bretagne
   */
  ngOnInit() {

    this.initForm();
    this.initFormations();
    this.initSalaries();
    this.initDepartements();
  }

  initForm() {

    this.sessionPresentielForm = this.fb.group({
      idFormationChoisie: [null, Validators.required],
      idDepartementChoisi: [null, Validators.required],
      idSalarieChoisi: [null, Validators.required],
      libelleFormationSelectionnee: ['', Validators.required],
      noYoda: ['', Validators.maxLength(30)],
      intituleSessionF: ['', [Validators.required, Validators.maxLength(50)]],
      BI: [0, Validators.required],
      dateLimiteYoda: ['', Validators.pattern(/^\d{4}\-(0[1-9]|1[012])\-(0[1-9]|[12][0-9]|3[01])$/)],
      libelleSessionF: [''],
      lieuFormation: ['', Validators.maxLength(100)],
      dateDebutSession: ['', Validators.required],
      dureeFormation: ['', [Validators.min(1), Validators.max(100)]],
      commanditaire: ['', Validators.maxLength(125)],
      RPE: ['', Validators.maxLength(255)],
      nbJournee: [''],
      journees: this.fb.array([this.createJournee()])
      // dateJournee1: ['', Validators.pattern(/^\d{4}\-(0[1-9]|1[012])\-(0[1-9]|[12][0-9]|3[01])$/)],
      // formateurJourneeN: [''],
      // salleJourneeN: ['']
    });

    this.sessionPresentielForm.get('nbJournee')?.valueChanges.subscribe(val => {
      this.adjustJournees(val);
    });

  }

  /**
   * Récupération de la liste des formations en présentiel
   * Le thème peut être choisi et le libellé de la formation s'affiche dynamiquement
   */
  initFormations() {

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
  }


  /**
   *   Récupération de la liste des salariés
   */
  initSalaries() {

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
  }

  /**
   * Récupération de la liste des départements de Bretagne
   */
  initDepartements() {

    this.departementService.getDepartementBzh().subscribe({
      next: (data) => {
        this.departements = data;
        if (this.departements.length) {
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
  createJournee(): FormGroup {
    return this.fb.group({
      date: [''],
      formateur: [''],
      salle: ['']
    });
  }

  adjustJournees(nb: number){

    const journees = this.sessionPresentielForm.get('journees') as FormArray;
    while(journees.length < nb) {
      journees.push(this.createJournee());
    }
    while(journees.length > nb) {
      journees.removeAt(journees.length -1);
    }
  }

  get journees(): FormArray {
    return this.sessionPresentielForm.get('journees') as FormArray;
  }





  /**
   * Méthode qui ajoute la session de formation lorsque l'utilisateur clique sur Enregistrer
   */
  onSubmit(): void {

    console.log("sousmission du formulaire");

    this.sessionPresentielForm.markAllAsTouched();

    this.messageService.showError("Votre formulaire contient une ou des erreurs, veuillez les corriger s'il vous plaît.")

    if (this.sessionPresentielForm.invalid) {
      console.log("il y a des erreurs");
      return;
    }


    const sessionData = this.sessionPresentielForm.value;
    // Appelle ton service pour envoyer sessionData
    this.ajouterSession(sessionData);
    console.log("Données du formulaire prêtes à être envoyées :", sessionData);

  }

  /**
   * Méthode qui appelle l'api pour ajouter la session de formation en présentiel
   * @param sessionFopDto
   */
  ajouterSession(sessionFopDto: SessionFopRespDTO) {

    const valeurs = this.sessionPresentielForm.value;
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


}
