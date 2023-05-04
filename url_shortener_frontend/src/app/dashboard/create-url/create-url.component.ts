import { UserProfileModel } from './../../auth/models/user-profile.model';
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DashboardService } from '../services/dashboard.service';
import { CurrentUserService } from '../../auth/services/current-user.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ErrorSnackBarconfig, SuccessSnackBarconfig } from '../../shared-module/models/notification-snackbar.model';

@Component({
  selector: 'app-create-url',
  templateUrl: './create-url.component.html',
  styleUrls: ['./create-url.component.scss']
})
export class CreateUrlComponent implements OnInit {
  url: string = '';
  generateUrlFormGroup!: FormGroup;
  customRequested: boolean = false;
  userEmail!: string | undefined;
  isCustomRequested: boolean = false;

  constructor(private service: DashboardService, private fb: FormBuilder, private currentService: CurrentUserService, private snackbar: MatSnackBar) {

    this.generateUrlFormGroup = this.fb.group({
      urlName: ['', Validators.required],
      longUrl: ['', Validators.required],
      // isCustomRequested: [false, Validators.required]
    })
  }
  ngOnInit(): void {

    this.userEmail = this.currentService.getUserProfile()?.email;
  }

  get urlName() {
    return this.generateUrlFormGroup.get('urlName');
  }

  get longUrl() {
    return this.generateUrlFormGroup.get('longUrl');
  }

  // get isCustomRequested() {
  //   return this.generateUrlFormGroup.get('isCustomRequested');
  // }

  @Output() submitEvent = new EventEmitter<{url: string, customRequested: boolean}>();

  setCustomRequested(value: boolean) {
    this.isCustomRequested = value;

  }
  submit() {
    const url = {
      urlName: this.urlName?.value,
      longUrl: this.longUrl?.value,
      userEmail: this.userEmail,
      isCustomRequested: this.isCustomRequested
    }
    this.service.generateURL(url).subscribe(response => {
      if(response) {
        this.snackbar.open("URL generated successful", "Close", SuccessSnackBarconfig)

      } else {
        this.snackbar.open("URL generated", "Close", ErrorSnackBarconfig);
      }
    }, error=> {
      this.snackbar.open(error.error ?? "URL generated", "Close", ErrorSnackBarconfig);
    })

  }
}

