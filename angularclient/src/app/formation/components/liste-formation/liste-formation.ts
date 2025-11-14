import { Component } from '@angular/core';
import {FormationResponseDTO} from '../../dto/formation-resp-dto.model';
import {FormationService} from '../../services/formation.service';
import {MessageService} from '../../../message/services/message.service';
import {Header} from '../../../header/header';
import {Footer} from '../../../footer/footer';
import {FormsModule} from '@angular/forms';
import {Router, RouterLink} from '@angular/router';
import {MatIconModule} from '@angular/material/icon';

@Component({
  selector: 'app-formation',
  imports: [
    Header,
    Footer,
    FormsModule,
    RouterLink,
    MatIconModule
  ],
  templateUrl: './liste-formation.html',
  styleUrl: './liste-formation.css'
})
export class ListeFormation {

  formations : FormationResponseDTO[] =[];
  openEditModal = false;
  formationToEdit: any = null; // ou typer selon ton interface Formation


  constructor(
    private router: Router,
    private formationService : FormationService,
    private messageService : MessageService) {
  }

  ngOnInit() {
    this.loadFormations();
  }

  loadFormations() {
    this.formationService.getFormations().subscribe({
      next: (data: FormationResponseDTO[]) => this.formations = data,
      error: (err) => this.messageService.showError('Erreur de récupération des formations' + err)
    });
  }

  /**
   * Méthode qui permet d'ouvrir la fenêtre modale
   * @param formation
   */
  openModal(formation: any) {
    this.formationToEdit = { ...formation }; // copie pour éditer sans modifier directement la liste
    this.openEditModal = true;
  }

  /**
   * Méthode qui permet de fermet la fenêtre modale
   *
   */
  closeModal() {
    this.openEditModal = false;
    this.formationToEdit = null;
  }

  saveFormation() {

    const formationUpdateDTO = {
      idFormation: this.formationToEdit.idFormation,
      themeFormation: this.formationToEdit.themeFormation,
      libelleFormation: this.formationToEdit.libelleFormation,
      idTypeFormation: this.formationToEdit.typeFormation?.idTypeFormation
    };

    console.log(formationUpdateDTO.idTypeFormation);

    // Appelle le service de mise à jour, par ex :
    this.formationService.updateFormation(formationUpdateDTO).subscribe({
      next: (updatedFormation) => {
        const index = this.formations.findIndex(f => f.idFormation === updatedFormation.idFormation);
        if (index !== -1) {
          this.formations[index] = updatedFormation;  // update local array
        }
        this.messageService.showSuccess('Formation modifiée avec succès.');
        this.closeModal();
      },
      error: () => { /* gérer erreur */ }
    });
  }

}
