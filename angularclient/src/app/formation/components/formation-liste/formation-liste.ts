import {Component, inject, OnDestroy, OnInit} from '@angular/core';
import {FormationResponseDTO} from '../../dto/formation-resp-dto.model';
import {FormationService} from '../../services/formation.service';
import {MessageService} from '../../../message/services/message.service';
import {Header} from '../../../header/header';
import {Footer} from '../../../footer/footer';
import {FormsModule} from '@angular/forms';
import {Router, RouterLink} from '@angular/router';
import {MatIconModule} from '@angular/material/icon';
import {Subject, takeUntil} from 'rxjs';
import {FormationUpdateDTO} from '../../dto/formation-update-dto.model';
import {FormationSupprimerModal} from '../formation-supprimer/formation-supprimer-modal';
import {FormationModifierModal} from '../formation-modifier/formation-modifier-modal';

@Component({
  selector: 'app-formation',
  imports: [
    Header,
    Footer,
    FormsModule,
    RouterLink,
    MatIconModule,
    FormationSupprimerModal,
    FormationModifierModal
  ],
  templateUrl: './formation-liste.html',
  styleUrl: './formation-liste.css'
})
export class FormationListe implements OnInit, OnDestroy {

  private router = inject(Router);
  private formationService = inject(FormationService);
  private messageService = inject(MessageService);
  private destroy$ = new Subject<void>();

  // Liste des formations
  formations: FormationResponseDTO[] = [];


  /**
   * Indicateur d'affichage du modal de modification.
   * @type {boolean}
   * @description true si le modal de modification est ouvert, false sinon.
   */
  showEditModal = false;

  /**
   * Indicateur d'affichage du modal de suppression.
   * @type {boolean}
   * @description true si le modal de suppression est ouvert, false sinon.
   */
  showDeleteModal = false;

  /**
   * Formation actuellement sélectionnée pour modification ou suppression.
   * @type {FormationResponseDTO | null}
   * @description Référence vers la formation en cours de traitement.
   */
  selectedFormation: FormationResponseDTO | null = null;

  constructor() {
  };

  /**
   * Initialisation du composant.
   * @description Charge la liste des formations au démarrage.
   */
  ngOnInit() {
    this.loadFormations();
  }

  /**
   * Nettoyage lors de la destruction du composant.
   * @description Termine les observables pour éviter les fuites mémoire.
   */
  ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.complete();
  }

  /**
   * Charge la liste des formations depuis le service.
   * @returns {void}
   * @description Récupère toutes les formations et met à jour la liste locale.
   */
  loadFormations() {
    this.formationService.getFormations()
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (data: FormationResponseDTO[]) => this.formations = data,
        error: (err) => this.messageService.showError('Erreur de récupération des formations' + err)
      });
  }

  /**
   * Ouvre le modal de modification pour une formation donnée.
   * @param {FormationResponseDTO} formation - La formation à modifier
   * @returns {void}
   * @description Sélectionne la formation et affiche le modal de modification.
   */
  openEditModal(formation: FormationResponseDTO): void {
    this.selectedFormation = formation;
    this.showEditModal = true;
  }

  /**
   * Gestionnaire d'événement pour la sauvegarde d'une formation modifiée.
   * @param {FormationResponseDTO} modifiedFormation - La formation avec les modifications
   * @returns {void}
   * @description Traite la sauvegarde de la formation modifiée via le service.
   */
  onEditSave(modifiedFormation: FormationResponseDTO): void {
    if (!modifiedFormation) {
      this.messageService.showError('Aucune formation à sauvegarder.');
      return;
    }

    // Validation des champs obligatoires
    if (!modifiedFormation.idFormation ||
      !modifiedFormation.themeFormation ||
      !modifiedFormation.libelleFormation ||
      !modifiedFormation.typeFormation?.idTypeFormation) {
      this.messageService.showError('Tous les champs sont obligatoires.');
      return;
    }

    // Création du DTO pour la mise à jour
    const formationUpdateDTO: FormationUpdateDTO = {
      idFormation: modifiedFormation.idFormation,
      themeFormation: modifiedFormation.themeFormation,
      libelleFormation: modifiedFormation.libelleFormation,
      idTypeFormation: modifiedFormation.typeFormation.idTypeFormation
    };

    // Appel du service de mise à jour
    this.formationService.updateFormation(formationUpdateDTO)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (updatedFormation: FormationResponseDTO) => {
          // Mise à jour de la liste locale
          const index = this.formations.findIndex(f => f.idFormation === updatedFormation.idFormation);
          if (index !== -1) {
            this.formations[index] = updatedFormation;
          }
          this.messageService.showSuccess('Formation modifiée avec succès.');
          this.onEditCancel(); // Fermeture du modal
        },
        error: (error) => {
          this.messageService.showError('Erreur lors de la mise à jour de la formation');
          console.error('Erreur lors de la mise à jour:', error);
        }
      });
  }

  /**
   * Gestionnaire d'événement pour l'annulation de la modification.
   * @returns {void}
   * @description Ferme le modal de modification et remet à zéro la sélection.
   */
  onEditCancel(): void {
    this.showEditModal = false;
    this.selectedFormation = null;
  }

  /**
   * Ouvre le modal de confirmation de suppression.
   * @param {FormationResponseDTO} formation - La formation à supprimer
   * @returns {void}
   * @description Sélectionne la formation et affiche le modal de suppression.
   */
  openDeleteModal(formation: FormationResponseDTO): void {
    this.selectedFormation = formation;
    this.showDeleteModal = true;
  }


  /**
   * Gestionnaire d'événement pour l'annulation de la suppression.
   * @returns {void}
   * @description Ferme le modal de suppression et remet à zéro la sélection.
   */
  onDeleteCancel(): void {
    this.showDeleteModal = false;
    this.selectedFormation = null;
  }

  /**
   * Gestionnaire d'événement pour la confirmation de suppression.
   * @param {FormationResponseDTO} formation - La formation à supprimer
   * @returns {void}
   * @description Effectue la suppression de la formation via le service.
   */
  onDeleteConfirm(formation : FormationResponseDTO): void {

      this.formationService.deleteFormation(formation.idFormation)
        .pipe(takeUntil(this.destroy$))
        .subscribe({
        next: (response) => {
          // Suppression réussie - retirer la formation de la liste locale
          this.formations = this.formations.filter(
            f => f.idFormation !== formation.idFormation
          );
          this.messageService.showSuccess("Formation supprimée avec succès.")
          this.onDeleteCancel() ; // Fermeture du modal
        },
        error: (error) => {
          this.messageService.showError("Erreur lors de la suppression de la formation.");
          console.error('Erreur lors de la suppression:', error);
        }
      });
    }


}
