import { Component } from '@angular/core';
import {Header} from "../header/header";
import {Footer} from '../footer/footer';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-ajouter-session-formation-presentiel',
  imports: [
    Header,
    Footer,
    RouterLink
  ],
  templateUrl: './ajouter-session-formation-presentiel.html',
  styleUrl: './ajouter-session-formation-presentiel.css'
})
export class AjouterSessionFormationPresentiel {

}
