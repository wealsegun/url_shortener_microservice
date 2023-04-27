import {
  Component,
  Input,
  OnInit,
  ViewChild,
  AfterViewInit,
} from '@angular/core';
import { UserProfileModel } from '../../auth/models/user-profile.model';
import { CurrentUserService } from '../../auth/services/current-user.service';
import { UserGeneratedUrlModel } from '../models/user-url.model';
import { DashboardService } from '../services/dashboard.service';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { DataSource } from '@angular/cdk/table';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
})
export class DashboardComponent implements OnInit {
  userProfile: UserProfileModel | null | undefined;
  userUrls!: UserGeneratedUrlModel[];
  userUrl!: UserGeneratedUrlModel;
  @Input() response: any;
  displayedColumns: string[] = [
    'id',
    'urlName',
    'longUrl',
    'tinyUrl',
    'shortenedBitlyUrl',
    'userEmail',
    'customUrl',
    'expiryDate',
    'createdDate',
    'customRequested',
  ];
  dataSource = new MatTableDataSource<UserGeneratedUrlModel>(this.userUrls);
  @ViewChild(MatPaginator) set paginator(paginator: MatPaginator){
    this.dataSource.paginator = paginator;
  }
  @ViewChild(MatSort) set sort(sort: MatSort){
    this.dataSource.sort = sort;
  }


  // @ViewChild(MatPaginator) paginator!: MatPaginator;
  // @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private currentService: CurrentUserService,
    private service: DashboardService
  ) {}

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  ngOnInit(): void {
    this.getUserProfile();
    const profi = this.currentService.getUserProfile();
    this.getUrlList(profi?.email as string);
  }

  getUserProfile() {
    let userProfile = this.currentService.getUserProfile();
    this.userProfile = userProfile;
  }

  getUrlList(userEmail: string) {
    this.service.getUserUrlList(userEmail).subscribe((response) => {
      this.userUrls = response;
      this.dataSource = response;
    });
  }

  getUrlById(userEmail: string, id: number) {
    this.service.getUserUrl(userEmail, id).subscribe((response) => {
      this.userUrl = response;
    });
  }
}
