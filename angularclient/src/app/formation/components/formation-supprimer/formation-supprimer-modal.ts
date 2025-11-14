import { Component, Input, Output, EventEmitter } from '@angular/core';
import {FormationResponseDTO} from '../../dto/formation-resp-dto.model';

/**
 * Composant modal pour la suppression d'une formation.
 * Ce composant affiche une boîte de dialogue de confirmation pour supprimer une formation spécifique.
 *
 * @example
 * <app-formation-supprimer
 *   [isOpen]="showDeleteModal"
 *   [formation]="selectedFormation"
 *   (confirm)="onDeleteConfirm($event)"
 *   (cancel)="onDeleteCancel()">
 * </app-formation-supprimer>
 */
@Component({
  selector: 'app-formation-supprimer',
  templateUrl: './formation-supprimer-modal.html',
  styleUrl: './formation-supprimer-modal.css'
})
export class FormationSupprimerModal {

  /**
   * Contrôle la visibilité du modal de suppression.
   * @type {boolean}
   * @default false
   * @description Quand true, le modal est affiché. Quand false, le modal est masqué.
   */
  @Input() isOpen = false;

  /**
   * L'objet formation à supprimer.
   * @type {FormationResponseDTO | null}
   * @default null
   * @description Contient les données de la formation qui sera supprimée. Null si aucune formation n'est sélectionnée.
   */
  @Input() formation: FormationResponseDTO | null = null;

  /**
   * Événement émis lorsque l'utilisateur confirme la suppression.
   * @type {EventEmitter<FormationResponseDTO>}
   * @description Émet l'objet formation qui doit être supprimé vers le composant parent.
   */
  @Output() confirm = new EventEmitter<FormationResponseDTO>();

  /**
   * Événement émis lorsque l'utilisateur annule la suppression.
   * @type {EventEmitter<void>}
   * @description Émet un signal d'annulation vers le composant parent pour fermer le modal.
   */
  @Output() cancel = new EventEmitter<void>();

  /**
   * Méthode appelée lorsque l'utilisateur confirme la suppression.
   * @returns {void}
   * @description Vérifie qu'une formation est sélectionnée puis émet l'événement de confirmation
   * avec l'objet formation à supprimer.
   */
  onConfirm(): void {
    if (this.formation) {
      this.confirm.emit(this.formation);
    }
  }

  /**
   * Méthode appelée lorsque l'utilisateur annule la suppression.
   * @returns {void}
   * @description Émet l'événement d'annulation pour fermer le modal sans supprimer la formation.
   */
  onCancel(): void {
    this.cancel.emit();
  }

}
