import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { UserProfileModel } from '../models/user-profile.model';
import { LoginModel } from '../models/login.model';
import { RegisterModel } from '../models/register.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private httpClient: HttpClient,) {

  }

  login(user: LoginModel): Observable<any> {
    const url = environment.baseUrl +`auth/authenticate`;
    return this.httpClient.post<UserProfileModel>(url,user);
  }

  logoutUser(userId: number) {
    const url = environment.baseUrl +`account/logout/${userId}`;
    return this.httpClient.get<any>(url);
  }

  registerUser(registerProfile: RegisterModel): Observable<any>{
    const url = environment.baseUrl+  `auth/register`;
    return this.httpClient.post<any>(url, registerProfile);
  }

}
