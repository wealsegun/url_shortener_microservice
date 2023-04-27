import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { CurrentUserService } from '../services/current-user.service';
import { ErrorSnackBarconfig, SuccessSnackBarconfig } from 'src/app/shared-module/models/notification-snackbar.model';
import { RegisterModel } from '../models/register.model';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent implements OnInit {
  registerFormGroup!: FormGroup;
  errorMessage!: string;
  constructor(
    private fb: FormBuilder,
    private service: AuthService,
    private router: Router,
    private currentUserService: CurrentUserService,
    private snackbar: MatSnackBar
  ) {
    this.registerFormGroup = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: [
        '',
        [
          Validators.required,
          Validators.pattern(
            '/^(([^<>()[].,;:s@"]+(.[^<>()[].,;:s@"]+)*)|(".+"))@(([^<>()[].,;:s@"]+.)+[^<>()[].,;:s@"]{2,})$/i;'
          ),
        ],
      ],
      password: [
        '',
        [
          Validators.required,
          Validators.pattern(
            '/^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*W)(?!.* ).{8,16}$/'
          ),
        ],
      ],
    });
  }

  get firstName() {
    return this.registerFormGroup.get('firstName');
  }

  get lastName() {
    return this.registerFormGroup.get('lastName');
  }
  get email() {
    return this.registerFormGroup.get('email');
  }
  get password() {
    return this.registerFormGroup.get('password');
  }

  ngOnInit(): void {}

  registerUser() {
    const user: RegisterModel = {
      firstName: this.firstName?.value,
      lastName: this.lastName?.value,
      email: this.email?.value,
      password: this.password?.value
    };
    this.service.registerUser(user).subscribe(user=> {
      if(user) {
        this.snackbar.open(this.errorMessage ??"Account created successful", "Close", SuccessSnackBarconfig)
        this.currentUserService.addToken(user.token);
        this.currentUserService.addUserProfile(user.user);
        this.router.navigate(["/dashboard"]);

      } else {
        this.snackbar.open("Login failed!!!", "Close", ErrorSnackBarconfig);
      }
    }, (error: any)=> {
      console.log(error.error);
      this.errorMessage = error.error;
      this.snackbar.open(this.errorMessage ??"Sign up failed", "Close", ErrorSnackBarconfig);

    })
  }
}
