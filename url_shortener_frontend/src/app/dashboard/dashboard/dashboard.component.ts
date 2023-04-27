import { Component, OnInit } from '@angular/core';
import { UserProfileModel } from '../../auth/models/user-profile.model';
import { CurrentUserService } from '../../auth/services/current-user.service';
import { UserGeneratedUrlModel } from '../models/user-url.model';
import { DashboardService } from '../services/dashboard.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
})
export class DashboardComponent implements OnInit {
  userProfile: UserProfileModel | null | undefined;
  userUrls!: UserGeneratedUrlModel[];
  userUrl!:UserGeneratedUrlModel;
  constructor(
    private currentService: CurrentUserService,
    private service: DashboardService
  ) {}

  ngOnInit(): void {
    this.getUserProfile();
    const profi = this.currentService.getUserProfile();
    this.getUrlList(profi?.email as string);
  }

  getUserProfile() {
    let userProfile = this.currentService.getUserProfile();
    this.userProfile = userProfile;
  }

  // generatedUrl()

  getUrlList(userEmail: string) {
    this.service.getUserUrlList(userEmail).subscribe((response) => {
      this.userUrls = response;
    });
  }

  getUrlById(userEmail: string, id: number) {
    this.service.getUserUrl(userEmail, id).subscribe(response=> {
      this.userUrl = response;
    })
  }
}
