import {Component, OnInit} from '@angular/core';
import {Header} from "../../../header/header";
import {Footer} from '../../../footer/footer';
import {Router, RouterLink} from '@angular/router';
import {
  AjouterSessionFormationPresentielService
} from '../../services/ajouter-session-formation-presentiel.service';
import {MessageService} from '../../../message/services/message.service';
import {FormationResponseDTO} from '../../../formation/dto/formation-resp-dto.model';
import {FormationService} from '../../../formation/services/formation.service';
import {
  FormArray,
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';
import {SalarieService} from '../../../salarie/services/salarie.service';
import {DepartementService} from '../../../departement/services/departement.service';
import {DepartementDTO} from '../../../departement/dto/departement-resp-dto.model';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {SalarieRespDTO} from '../../../salarie/dto/salarie-resp-dto.model';
import {SessionFopReqDTO} from '../../dto/session-formation-presentiel-req-dto.model';
import {StatutSessionFormation} from '../../dto/statut-session-formation.enum';
import {StatutSessionDate} from '../../../sessionDate/dto/statut-session-date.enum';


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


  // Formulaire principal gestion session de formation
  sessionPresentielForm!: FormGroup;

  // Listes chargées depuis services pour sélections
  formations: FormationResponseDTO[] = [];
  departements: DepartementDTO[] = [];
  salaries: SalarieRespDTO[] = [];

  // Libellé de la formation sélectionnée
  libelleFormationSelectionnee: string | null = '';

  // Valeurs sélectionnées dans les listes
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
   * Initialisation globale du composant :
   * - Initialisation du formulaire réactif
   * - Chargement des données de référence (formations, salariés, départements)
   */
  ngOnInit() {

    this.initForm();
    this.initFormations();
    this.initSalaries();
    this.initDepartements();
  }

  /**
   * Initialisation du FormGroup réactif avec ses contrôles :
   * - Champ simples (textes, dates, nombres)
   * - FormArray dynamique 'sessionsDate' pour gérer les sessions date multiples
   * - Synchronisation dynamique de sessionsDate selon nbJournee
   */
  initForm() {
    this.sessionPresentielForm = this.fb.group({
      idFormationChoisie: [null, Validators.required],
      idDepartementChoisi: [null, Validators.required],
      idSalarieChoisi: [null, Validators.required],
      libelleFormationSelectionnee: [''],
      noYoda: ['', Validators.maxLength(30)],
      intituleSessionF: ['', [Validators.required, Validators.maxLength(50)]],
      dateLimiteYoda: [''],
      lieuSessionFormation: ['', [Validators.maxLength(100),Validators.required]],
      dateDebutSession: ['', Validators.required],
      nbHeureSession: [0, [Validators.min(1), Validators.max(100)]],
      commanditaire: ['', Validators.maxLength(125)],
      confirmationRPE: ['', Validators.maxLength(255)],
      nbJournee: [0],
      sessionsDate: this.fb.array([]),
    });

    // Abonnement à la valeur nbJournee pour ajuster dynamiquement le nombre de sessionsDate
    this.sessionPresentielForm.get('nbJournee')?.valueChanges.subscribe(val => {
      this.adjustSessionsDate(val);
    });
  }

  /**
   * Getter pratique pour accéder au FormArray 'sessionsDate'
   */
  get sessionsDate(): FormArray {
    return this.sessionPresentielForm.get('sessionsDate') as FormArray;
  }


  /**
   * Crée un nouveau FormGroup pour un élément 'SessionDate' :
   * - Initialise les champs requis avec validations
   * - Valeur fixe 'duree' = 7 pour présentiel
   * - 'sessionFormation.idFormation' est initialisé à la formation choisie fixe
   */
  createSessionsDate(): FormGroup {
    return this.fb.group({
      idSessionDate: [null],
      dateSession: ['', Validators.required],
      duree: [7],
      heureVisio: [''],
      statutSessionDate: [StatutSessionDate.SESSION_DATE_SALLE_GRATUITE || null],
      formateur: this.fb.group({
        idPersonne: [null],
      }),
      sessionFormation: this.fb.group({
        idFormation: [this.sessionPresentielForm?.get('idFormationChoisie')?.value || null],
      }),
      salle: this.fb.group({
        idSessionSalle: [null],
      })
    })
  }


  /**
   * Ajuste dynamiquement la taille du tableau sessionsDate
   * - Ajoute ou supprime des groupes pour correspondre à nb
   * @param nb Nombre de sessionsDate souhaitées
   */
  adjustSessionsDate(nb: number) {

    const sessionsDate = this.sessionsDate;

    if (!nb || nb <= 0) {
      while (sessionsDate.length > 0) {
        sessionsDate.removeAt(0);
      }
      return;
    }

    while (sessionsDate.length < nb) {
      sessionsDate.push(this.createSessionsDate());
    }

    while (sessionsDate.length > nb) {
      sessionsDate.removeAt(sessionsDate.length - 1);
    }
  }


  /**
   * Charge la liste des formations, filtre celles en présentiel,
   * initialise la sélection avec la première formation trouvée,
   * et met à jour le libellé de la formation affiché.
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

    // Mise à jour du libellé à chaque changement de formation sélectionnée
    this.sessionPresentielForm.get('idFormationChoisie')?.valueChanges.subscribe(val => {
      const formationTrouvee = this.formations.find(f => f.idFormation === Number(val));
      this.libelleFormationSelectionnee = formationTrouvee ? formationTrouvee.libelleFormation : '';
    });
  }


  /**
   * Charge la liste des salariés et initialise la sélection avec le premier salarié disponible
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
   * Charge la liste des départements de Bretagne et initialise la sélection au premier département
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
   * Méthode appelée lors de la sauvegarde du formulaire :
   * - Valide la saisie
   * - Construit un DTO cohérent pour la création (SessionFopReqDTO)
   * - Envoie la requête via service
   * - Gère le succès et les erreurs utilisateur
   */
  onSubmit(): void {

    console.log("sousmission du formulaire");

    this.sessionPresentielForm.markAllAsTouched();

    if (this.sessionPresentielForm.invalid) {
      this.messageService.showError("Votre formulaire contient une ou des erreurs, veuillez les corriger s'il vous plaît.")
      console.log("il y a des erreurs");
      return;
    }

    const formValue = this.sessionPresentielForm.value;

    const sessionRequest: SessionFopReqDTO = {

      idSessionFormation: null,
      noYoda: formValue.noYoda || null,
      libelleSessionFormation: formValue.intituleSessionF || null,
      statutYoda: "DO",
      dateDebutSession: formValue.dateDebutSession || null,
      nbHeureSession: formValue.nbHeureSession || null,
      lieuSessionFormation: formValue.lieuSessionFormation || null,
      commanditaire: formValue.commanditaire || null,
      confirmationRPE: formValue.confirmationRPE || null,
      statutSessionFormation: StatutSessionFormation.SESSION_FORMATION_NON_COMMENCEE,

      // Lier la formation par son id
      formation: {
        idFormation: formValue.idFormationChoisie
      },
      // Lier le salarié par son id
      salarie: {
        idPersonne: Number(formValue.idSalarieChoisi)
      },
      // Lier le département par son id
      departement: {
        idDepartement: formValue.idDepartementChoisi
      },
      // Lier la fin de session de formation par son id s'il existe todo mettre null pour la création
      finSessionFormation: {
        idFinSessionFormation: formValue.idFinSessionFormation || null
      },

      sessionsSalle: formValue.salle || null,
      sessionsFormateur: formValue.formateur || null,
      // Envoi uniquement si non vide
      sessionsDate: formValue.sessionsDate && formValue.sessionsDate.length > 0 ? formValue.sessionsDate : null,
    }

    console.log('sessionRequest:', sessionRequest);

    // Envoi de la requête via ton service
    this.ajouterSessionFOP.ajoutSessionFOP(sessionRequest).subscribe({
      next: () => {
        this.messageService.showSuccess('Session ajoutée avec succès.');
        this.router.navigate(["listeSessionFormationPresentiel"]);
      },
      error: (err) => {
        const errMsg = err.error?.message || 'Erreur lors de l\'ajout de la session.';
        this.messageService.showError(errMsg);
      }
    });
  }
}
