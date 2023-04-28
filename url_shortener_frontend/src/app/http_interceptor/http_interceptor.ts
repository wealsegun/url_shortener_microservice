import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler } from '@angular/common/http';
import { CurrentUserService } from '../auth/services/current-user.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private currentService: CurrentUserService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    const authToken = this.currentService.getToken(); // Replace with your own token
    console.log(authToken);

    // Clone the original request and add the Authorization header
    const authReq = req.clone({
      headers: req.headers.set('Authorization', `Bearer ${authToken}`)
    });

    // Pass the cloned request instead of the original request to the next handle
    return next.handle(authReq);
  }
}
