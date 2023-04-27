import { Component, OnInit } from '@angular/core';
import { UserProfileModel } from 'src/app/auth/models/user-profile.model';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  userProfile!: UserProfileModel;
  constructor() { }

  ngOnInit(): void {
  }

}
