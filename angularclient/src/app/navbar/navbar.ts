import {Component} from '@angular/core';
import {Router, RouterLink, RouterLinkActive} from '@angular/router';
import {UserService} from '../services/user.service';
import {MessageService} from '../services/message.service';


@Component({
  selector: 'app-navbar',
  imports: [
    RouterLink,
    RouterLinkActive
  ],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css'
})
export class Navbar {

  isLoggedIn = false;

  constructor(private userService: UserService, private router: Router, private messageService: MessageService) {
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
    this.router.navigate(['/login']);
    this.messageService.showSuccess("Vous avez été déconnecté avec succès.")
  }

}
