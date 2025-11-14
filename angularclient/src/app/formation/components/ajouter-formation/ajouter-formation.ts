import {Component, OnInit} from '@angular/core';
import {Header} from '../../../header/header';
import {Footer} from '../../../footer/footer';
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  ValidatorFn,
  Validators
} from '@angular/forms';
import {Router, RouterLink} from '@angular/router';
import {MessageService} from '../../../message/services/message.service';
import {FormationReqDTO} from '../../dto/formation-req-dto.model';
import {FormationService} from '../../services/formation.service';

@Component({
  selector: 'app-ajouter-formation',
  standalone: true,
  imports: [
    Header,
    Footer,
    FormsModule,
    ReactiveFormsModule,
    RouterLink
  ],
  templateUrl: './ajouter-formation.html',
  styleUrls: ['./ajouter-formation.css']
})
export class AjouterFormation implements OnInit {

  // Formulaire principal gestion des formations
  formationForm!: FormGroup;


  constructor(
    private router: Router,
    private fb: FormBuilder,
    private formationService: FormationService,
    private messageService: MessageService) {
  }

  /**
   * Initialisation globale du composant :
   * - Initialisation du formulaire
   */
  ngOnInit() {
    this.initForm();
  }

  /**
   * Initialisation du FormGroup réactif avec ses contrôles :
   * - Champ simples (textes)
   */
  initForm() {

    this.formationForm = this.fb.group({
        themeFormation: ['', [Validators.required, Validators.maxLength(20)]],
        libelleFormation: ['', [Validators.required, Validators.maxLength(300)]],
        isPresentiel: [false],
        isDistanciel: [false],
      },
      {
        validators: [this.atLeastOneTypeValidator]
      })
  }

  atLeastOneTypeValidator: ValidatorFn = (group: AbstractControl) => {
    const isPres = group.get('isPresentiel')?.value;
    const isDist = group.get('isDistanciel')?.value;
    return isPres || isDist ? null : {atLeastOneType: true};
  };

// Accessibilité de l’erreur dans template
  get typeFormationInvalide(): false | boolean | undefined {
    return this.formationForm.hasError('atLeastOneType') &&
      (this.formationForm.get('isPresentiel')?.touched || this.formationForm.get('isDistanciel')?.touched);
  }


  /**
   * Méthode appelée lors de la sauvegarde du formulaire :
   * - Valide la saisie
   * - Construit un DTO cohérent pour la création (SessionFopReqDTO)
   * - Envoie la requête via service
   * - Gère le succès et les erreurs utilisateur
   */
  onSubmit(): void {

    console.log("Soumission du formulaire");

    this.formationForm.markAllAsTouched();

    if (this.formationForm.invalid) {
      this.messageService.showError("Votre formulaire contient une ou des erreurs, veuillez les corriger s'il vous plaît.")
      console.log("il y a des erreurs");
      return;
    }

    const formValue = this.formationForm.value;

    // liste des formations à envoyer
    const formationsToSend: FormationReqDTO[] = [];

    if (formValue.isPresentiel) {
      formationsToSend.push({
        idFormation: null,
        themeFormation: formValue.themeFormation || null,
        libelleFormation: formValue.libelleFormation || null,
        typeFormation: {idTypeFormation: 1}
      })
    }

    if (formValue.isDistanciel) {
      formationsToSend.push({
        idFormation: null,
        themeFormation: formValue.themeFormation || null,
        libelleFormation: formValue.libelleFormation || null,
        typeFormation: {idTypeFormation: 2}
      })
    }

    // Envoi des formations 1 par 1 (avec gestion des retours)
    const sendFormation = (index: number) => {
      if (index >= formationsToSend.length) {
        // toutes les formations envoyées avec succès
        this.messageService.showSuccess('Formation(s) ajoutée(s) avec succès.');
        this.router.navigate(["listeFormations"]);
        return;
      }

      console.log('Formations à envoyer :', formationsToSend);


      // Envoi de la requête via ton service
      this.formationService.ajoutFormation(formationsToSend[index]).subscribe({
        next: () => {
          // appel récursif pour la suivante
          sendFormation(index + 1);
        },
        error: (err) => {
          const errMsg = err.error?.message || 'Erreur lors de l\'ajout de la formation.';
          this.messageService.showError(errMsg);
        }
      });
    }
    // Lancer l’envoi à partir du premier élément
    if (formationsToSend.length > 0) {
      sendFormation(0);
    }
  }


}
