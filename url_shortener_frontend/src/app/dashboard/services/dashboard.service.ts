import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { GenerateUrlModel } from '../models/generate-url.model';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { CreateCountModel } from '../models/create-count.model';
@Injectable({
  providedIn: 'root',
})
export class DashboardService {
  constructor(private httpClient: HttpClient) {}

  generateURL(gen: GenerateUrlModel): Observable<any> {
    const url = `${environment.baseUrl}short/generate`;
    return this.httpClient.post<any>(url, gen);
  }
  getUserUrlList(userEmail: string): Observable<any> {
    const url = `${environment.baseUrl}short/links/${userEmail}`;
    return this.httpClient.get<any>(url);
  }
  getUserUrl(userEmail: string, id: number): Observable<any> {
    const url = `${environment.baseUrl}link/${userEmail}/data/${id}`;
    return this.httpClient.get<any>(url);
  }
  createUrlCount(urlCount: CreateCountModel): Observable<any> {
    const url = `${environment.baseUrl}short/count-create`;
    return this.httpClient.post<any>(url, urlCount);
  }
}
