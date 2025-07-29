import {Component, signal} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {FirstLetterUpperPipe} from './pipe/first-letter-upper.pipe';


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './app.html',
  styleUrls: ['./app.css'],

})
export class App {

  title = 'Organisme de formation - Mediaskol';

}
