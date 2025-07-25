import { Component } from '@angular/core';
import {Footer} from '../footer/footer';
import {Header} from '../header/header';

@Component({
  selector: 'app-home',
  imports: [
    Footer,
    Header
  ],
  templateUrl: './home.html',
  styleUrl: './home.css'
})
export class Home {

}
