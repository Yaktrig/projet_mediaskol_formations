import {Component} from '@angular/core';
import {Router, RouterLink} from '@angular/router';
import {UserService} from '../services/user.service';
import {FormsModule} from '@angular/forms';
import {firstValueFrom} from 'rxjs';
import {Header} from '../header/header';
import {Footer} from '../footer/footer';

@Component({
  selector: 'app-login',
  imports: [
    FormsModule,
    Header,
    Footer,
    RouterLink
  ],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class LoginComponent {

  pseudo: string = '';
  password: string = '';
  error: string = '';
  public rememberMe = false;

  constructor(private auth: UserService, private router: Router) {
  }


  ngOnInit() {
    this.autoLogin();
  }

  async autoLogin() {
    if (await this.auth.autologin()) {
      await this.router.navigate(["listeSessionFormationPresentiel"]);
    }
  }

  async loginUser() {
    this.error = "";
    if (this.pseudo.length < 3) {
      this.error = "Le login doit faire au moins 3 caractères";
      return;
    }
    if (this.password.length < 6) {
      this.error = "Le mot de passe doit faire au moins 6 caractères";
      return;
    }
    try {
      await firstValueFrom(this.auth.login(this.pseudo, this.password, this.rememberMe));
      await this.router.navigate(["listeSessionFormationPresentiel"]);
    } catch (err: any) {
      console.error(err);
      this.error = "Erreur lors de la connexion : " + (err.message || err);
    }
  }


  async subscribeUser() {
    this.error = "";
    if (this.pseudo.length < 3) {
      this.error = "Le pseudo doit faire au moins 3 caractères.";
      return;
    }
    if (this.password.length < 6) {
      this.error = "Le mot de passe doit faire au moins 6 caractères";
      return;
    }
    try {
      await this.auth.register(this.pseudo, this.password);
    } catch (err: any) {
      console.error(err);
      this.error = "Erreur lors de l'enregistrement : " + (err.message || err);
    }
  }

  onSubmit() {
    this.loginUser();
  }
}
