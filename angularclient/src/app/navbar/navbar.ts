import { Component } from '@angular/core';
import {RouterLink} from '@angular/router';
import {UserService} from '../services/user.service';


@Component({
  selector: 'app-navbar',
  imports: [
    RouterLink
  ],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css'
})
export class Navbar {

  isLoggedIn = false;

  constructor(private userService: UserService) {
    this.checkLogin();
  }

  checkLogin() {
    this.isLoggedIn = this.userService.isLoggedIn();
  }

  loginUser() {
    // Logique pour ouvrir un formulaire/modal de connexion, ou rediriger vers une page login
    console.log('Afficher formulaire ou redirection login');
  }

  subscribeUser() {
    // Logique pour créer un compte, redirection page d’inscription...
    console.log('Afficher formulaire ou redirection inscription');
  }

  logout() {
    this.userService.logout();
    this.isLoggedIn = false;
    // Eventuellement rediriger vers accueil ou page login
    console.log('Déconnexion effectuée');
  }

}
