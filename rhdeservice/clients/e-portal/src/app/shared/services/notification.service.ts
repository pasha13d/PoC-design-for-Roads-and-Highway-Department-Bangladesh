import { Injectable, NgZone } from '@angular/core';
import { MatSnackBar, MatSnackBarHorizontalPosition, MatSnackBarVerticalPosition, MatSnackBarConfig } from '@angular/material/snack-bar';

@Injectable({
    providedIn: 'root'
})
export class NotificationService {

  horizontalPosition: MatSnackBarHorizontalPosition = 'right';
  verticalPosition: MatSnackBarVerticalPosition = 'bottom';
  addExtraClass: boolean = false;
  constructor(public snackBar: MatSnackBar, private zone: NgZone) {

  }

  showSuccess(message: string): void {
    // Had an issue with the snackbar being ran outside of angular's zone.
    this.zone.run(() => {
      const config = new MatSnackBarConfig();
      config.horizontalPosition = this.horizontalPosition;
      config.verticalPosition = this.verticalPosition;
      config.panelClass = ['alert-success'];
      config.duration = 5000;
      this.snackBar.open(message, 'X', config);
    });
  }

  showError(message: string): void {
    this.zone.run(() => {
      const config = new MatSnackBarConfig();
      config.horizontalPosition = this.horizontalPosition;
      config.verticalPosition = this.verticalPosition;
      config.panelClass = ['alert-danger'];
      config.duration = 5000;
      // The second parameter is the text in the button.
      // In the third, we send in the css class for the snack bar.
      // this.snackBar.open(message, 'X', {panelClass: ['error']});
      this.snackBar.open(message, 'X', config);
    });
  }
}
