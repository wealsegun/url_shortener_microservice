import { Component, OnInit } from '@angular/core';
import { CurrentUserService } from '../../auth/services/current-user.service';
import { UserProfileModel } from '../../auth/models/user-profile.model';

@Component({
  selector: 'app-dashboard-layout',
  templateUrl: './dashboard-layout.component.html',
  styleUrls: ['./dashboard-layout.component.scss']
})
export class DashboardLayoutComponent implements OnInit {
  userProfile: UserProfileModel | null | undefined;
  totalUrl: any = 0;
  constructor( private currentService: CurrentUserService,) { }

  ngOnInit(): void {
    this.getUserProfile();
    const profi = this.currentService.getUserProfile();
  }
  getUserProfile() {
    let userProfile = this.currentService.getUserProfile();
    this.userProfile = userProfile;
  }


}
