import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DashboardService } from '../services/dashboard.service';
import { CurrentUserService } from '../../auth/services/current-user.service';

@Component({
  selector: 'app-create-url',
  templateUrl: './create-url.component.html',
  styleUrls: ['./create-url.component.scss']
})
export class CreateUrlComponent implements OnInit {
  url: string = '';
  generateUrlFormGroup!: FormGroup;
  customRequested: boolean = false;

  constructor(private service: DashboardService, private fb: FormBuilder, private currentService: CurrentUserService) {

    this.generateUrlFormGroup = this.fb.group({
      urlName: ['', Validators.required],
      longUrl: ['', Validators.required],
      isCustomRequested: [false, Validators.required]
    })

  }
  ngOnInit(): void {

  }

  get urlName() {
    return this.generateUrlFormGroup.get('urlName');
  }

  get longUrl() {
    return this.generateUrlFormGroup.get('longUrl');
  }

  get isCustomRequested() {
    return this.generateUrlFormGroup.get('isCustomRequested');
  }

  @Output() submitEvent = new EventEmitter<{url: string, customRequested: boolean}>();

  submit() {
    this.submitEvent.emit({url: this.url, customRequested: this.customRequested});
  }
}

