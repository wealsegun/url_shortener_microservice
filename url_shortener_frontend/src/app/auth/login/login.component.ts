import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CurrentUserService } from '../services/current-user.service';
import { AuthService } from '../services/auth.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ErrorSnackBarconfig, SuccessSnackBarconfig } from '../../shared-module/models/notification-snackbar.model';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginFormGroup!: FormGroup;
  errorMessage!: string;
  constructor(private fb: FormBuilder,
    private service: AuthService,
    private router: Router,
    private currentUserService: CurrentUserService,
    private snackbar: MatSnackBar) {

      this.loginFormGroup = fb.group({
        email: ['', [Validators.required, Validators.pattern('/^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;')]],
      password: ['', [Validators.required, Validators.pattern('/^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\W)(?!.* ).{8,16}$/')]]
      })
    }

    get email () {
      return this.loginFormGroup.get("email");
    }

    get password () {
      return this.loginFormGroup.get("password");
    }

  ngOnInit(): void {
  }

  submitLogin() {
    const login ={
      email: this.email?.value,
      password: this.password?.value
    };

    this.service.login(login).subscribe(user=> {
      if(user) {
        this.currentUserService.addToken(user.token);
        this.currentUserService.addUserProfile(user.user);
        this.router.navigate(["/dashboard"]);

      } else {
        this.snackbar.open("Login failed!!!", "Close", ErrorSnackBarconfig);
      }
    }, (error: any)=> {
      console.log(error.error);
      this.errorMessage = error.error;
      this.snackbar.open(this.errorMessage?? "Login Failed", "Close", ErrorSnackBarconfig)

    })
  }

}
