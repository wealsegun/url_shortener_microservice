import { MatSnackBarConfig } from '@angular/material/snack-bar';


export const  ErrorSnackBarconfig = new MatSnackBarConfig();
ErrorSnackBarconfig.duration = 5000;
ErrorSnackBarconfig.panelClass = ['red-snackbar'];
ErrorSnackBarconfig.verticalPosition = 'bottom';
ErrorSnackBarconfig.horizontalPosition = 'end';

 export const DefaultSnackBarconfig = new MatSnackBarConfig();
DefaultSnackBarconfig.duration = 5000;
DefaultSnackBarconfig.verticalPosition = 'bottom';
DefaultSnackBarconfig.horizontalPosition = 'center';

export const  SuccessSnackBarconfig = new MatSnackBarConfig();
SuccessSnackBarconfig.duration = 5000;
SuccessSnackBarconfig.panelClass = ['green-snackbar'];
SuccessSnackBarconfig.verticalPosition = 'bottom';
SuccessSnackBarconfig.horizontalPosition = 'end';


