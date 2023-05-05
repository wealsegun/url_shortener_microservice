import { Injectable } from '@angular/core';
import { UserProfileModel } from '../models/user-profile.model';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Token } from '../models/token.model';

@Injectable({
  providedIn: 'root',
})
export class CurrentUserService {
  currentUser!: UserProfileModel;
  constructor(private httpClient: HttpClient) {}

  getUserProfile() {
    const stringUser = localStorage.getItem('user_profile');
    if (stringUser !== '' || ' ') {
      // this.currentUser = ;
      const user: any = localStorage.getItem('user_profile');
      this.currentUser = JSON.parse(user);
      return this.currentUser as UserProfileModel;
    } else {
      return null;
    }
  }

  getToken() {
    return localStorage.getItem('token');
  }
  addUserProfile(userProfile: any) {
    console.log(userProfile);
    localStorage.setItem('user_profile', JSON.stringify(userProfile));
  }
  addURLCount(url: number) {
    localStorage.setItem('urlCount', JSON.stringify(url));
  }
  getURLCount() {
    return localStorage.getItem('urlCount');
  }
  addToken(token: Token) {
    console.log(token);
    localStorage.setItem('token', JSON.stringify(token.token));
  }
  // logout() {
  //   console.log(this.getUserProfile());
  //   const userId = this.getUserProfile()?.id;
  //   this.logoutUser(userId ?? 0).subscribe((response) => {
  //     if (response) {
  //       localStorage.removeItem('user_profile');
  //       localStorage.removeItem('token');
  //     } else {
  //     }
  //   });
  // }
  logoutUser() {
    localStorage.removeItem("user_profile");
    localStorage.removeItem("token")
  }
}
