import { Component, Input, Output, EventEmitter } from '@angular/core';
import { FormationResponseDTO } from '../../dto/formation-resp-dto.model';
import {FormsModule} from '@angular/forms';

/**
 * Composant modal pour la modification d'une formation.
 * Ce composant affiche une boîte de dialogue permettant d'éditer les informations d'une formation existante.
 *
 * @example
 * <app-formation-modifier
 *   [isOpen]="showEditModal"
 *   [formation]="selectedFormation"
 *   (save)="onEditSave($event)"
 *   (cancel)="onEditCancel()">
 * </app-formation-modifier>
 */
@Component({
  selector: 'app-formation-modifier',
  templateUrl: './formation-modifier-modal.html',
  imports: [
    FormsModule
  ],
  styleUrl: './formation-modifier-modal.css'
})
export class FormationModifierModal {

  /**
   * Contrôle la visibilité du modal de modification.
   * @type {boolean}
   * @default false
   * @description Quand true, le modal est affiché. Quand false, le modal est masqué.
   */
  @Input() isOpen = false;

  /**
   * Événement émis lorsque l'utilisateur sauvegarde les modifications.
   * @type {EventEmitter<FormationResponseDTO>}
   * @description Émet l'objet formation modifié vers le composant parent pour traitement.
   */
  @Output() save = new EventEmitter<FormationResponseDTO>();

  /**
   * Événement émis lorsque l'utilisateur annule la modification.
   * @type {EventEmitter<void>}
   * @description Émet un signal d'annulation vers le composant parent pour fermer le modal.
   */
  @Output() cancel = new EventEmitter<void>();

  /**
   * Formation originale reçue du composant parent.
   * @type {FormationResponseDTO | null}
   * @private
   * @description Stocke la formation originale sans la modifier directement.
   */
  private _formation: FormationResponseDTO | null = null;

  /**
   * Copie locale de la formation en cours de modification.
   * @type {FormationResponseDTO | null}
   * @private
   * @description Utilisée pour éditer les données sans modifier directement l'objet d'entrée.
   */
  private _formationToEdit: FormationResponseDTO | null = null;

  /**
   * Setter pour la formation à modifier (propriété d'entrée).
   * @param {FormationResponseDTO | null} value - La formation à modifier
   * @description Crée une copie locale de la formation pour éviter la mutation directe des données d'entrée.
   */
  @Input()
  set formation(value: FormationResponseDTO | null) {
    this._formation = value;
    if (value) {
      // Création d'une copie profonde pour éviter la mutation de l'objet original
      this._formationToEdit = { ...value };
    } else {
      this._formationToEdit = null;
    }
  }

  /**
   * Getter pour accéder à la formation originale.
   * @returns {FormationResponseDTO | null} La formation originale ou null
   * @description Retourne la formation originale reçue du composant parent.
   */
  get formation(): FormationResponseDTO | null {
    return this._formation;
  }

  /**
   * Getter pour accéder à la formation en cours d'édition.
   * @returns {FormationResponseDTO | null} La formation en cours d'édition ou null
   * @description Retourne la copie locale de la formation à modifier.
   */
  get formationToEdit(): FormationResponseDTO | null {
    return this._formationToEdit;
  }

  /**
   * Méthode appelée lorsque l'utilisateur sauvegarde les modifications.
   * @returns {void}
   * @description Vérifie qu'une formation est en cours d'édition puis émet l'événement de sauvegarde
   * avec l'objet formation modifié.
   */
  onSave(): void {
    if (this._formationToEdit) {
      this.save.emit(this._formationToEdit);
    }
  }

  /**
   * Méthode appelée lorsque l'utilisateur annule la modification.
   * @returns {void}
   * @description Émet l'événement d'annulation pour fermer le modal sans sauvegarder les modifications.
   * Remet également à zéro la formation en cours d'édition.
   */
  onCancel(): void {
    // Restauration de la copie originale si nécessaire
    if (this._formation) {
      this._formationToEdit = { ...this._formation };
    }
    this.cancel.emit();
  }
}
