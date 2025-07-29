import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root',

})
export class MessageService {

  constructor(private snackBar: MatSnackBar) {}

  showSuccess(message: string) {
    this.snackBar.open(message, 'OK', {
      duration: 3000,
      verticalPosition: 'top',
      horizontalPosition:'left',
      panelClass: ['snackbar-custom-success']
    });
  }

  showError(message: string) {
    this.snackBar.open(message, 'OK', {
      duration: 5000,
      verticalPosition: 'top',
      horizontalPosition:'left',
      panelClass: ['snackbar-custom-error']
    });
  }
}

