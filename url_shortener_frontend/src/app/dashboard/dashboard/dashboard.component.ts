import {
  Component,
  Input,
  OnInit,
  ViewChild,
  AfterViewInit,
  OnChanges,
  SimpleChanges,
} from '@angular/core';
import { UserProfileModel } from '../../auth/models/user-profile.model';
import { CurrentUserService } from '../../auth/services/current-user.service';
import { UserGeneratedUrlModel } from '../models/user-url.model';
import { DashboardService } from '../services/dashboard.service';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { MatDialog } from '@angular/material/dialog';
import { CreateUrlComponent } from '../create-url/create-url.component';
import {
  SuccessSnackBarconfig,
  ErrorSnackBarconfig,
} from 'src/app/shared-module/models/notification-snackbar.model';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CreateCountModel } from '../models/create-count.model';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
})
export class DashboardComponent implements OnInit, AfterViewInit, OnChanges {
  userProfile: UserProfileModel | null | undefined;
  userUrls!: UserGeneratedUrlModel[];
  userUrl!: UserGeneratedUrlModel;
  @Input() submitEvent: any;
  displayedColumns: string[] = [
    'urlName',
    'longUrl',
    'tinyUrl',
    // 'shortenedBitlyUrl',
    'customUrl',
    'customRequested',
    'clickedCount',
    'createdDate',
    'expiryDate',
  ];
  dataSource = new MatTableDataSource<UserGeneratedUrlModel>(this.userUrls);
  @ViewChild(MatPaginator) set paginator(paginator: MatPaginator) {
    this.dataSource.paginator = paginator;
  }
  @ViewChild(MatSort) set sort(sort: MatSort) {
    this.dataSource.sort = sort;
  }

  constructor(
    private currentService: CurrentUserService,
    private service: DashboardService,
    private dialog: MatDialog,
    private snackbar: MatSnackBar
  ) {}

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  ngOnInit(): void {
    this.getUserProfile();
    const profi = this.currentService.getUserProfile();
    this.getUrlList(profi?.email as string);
  }
  ngOnChanges(changes: SimpleChanges): void {
    this.onSubmit(this.submitEvent);
  }

  getUserProfile() {
    let userProfile = this.currentService.getUserProfile();
    this.userProfile = userProfile;
  }

  getUrlList(userEmail: string) {
    this.service.getUserUrlList(userEmail).subscribe((response) => {
      this.userUrls = response;
      this.dataSource = response;
      console.log(this.dataSource);
      this.currentService.addURLCount(this.userUrls.length);
    });
  }

  getUrlById(userEmail: string, id: number) {
    this.service.getUserUrl(userEmail, id).subscribe((response) => {
      this.userUrl = response;
    });
  }

  openPopup() {
    const dialogRef = this.dialog.open(CreateUrlComponent);
  }

  SendCountDetails(userUrl: any) {
    console.log(userUrl);
    const url: CreateCountModel = {
      id: 0,
      ClickedLink: userUrl.customUrl,
      clickUrlName: userUrl.urlName,
      userEmail: this.userProfile?.email,
    };

    this.service.createUrlCount(url).subscribe((response) => {
      console.log(response);
      const email = this.userProfile?.email? this.userProfile?.email: "";
      this.getUrlList(email);
    });
  }

  // getCount(userEmail: string) {
  //   console.log(userEmail);
  // }

  // getClicked(details: any) {
  //   const {userEmail, clickedLink} = details;
  //   this.service.getClicked(userEmail,clickedLink ).subscribe((response) => {
  //     console.log(response);
  //     this.noOfClicked = +response;
  //     return response;

  //   })
  // }

  onSubmit(url: any) {
    this.service.generateURL(url).subscribe(
      (response) => {
        if (response) {
          this.snackbar.open(
            'URL generated successful',
            'Close',
            SuccessSnackBarconfig
          );
          this.submitEvent.emit(response);
        } else {
          this.snackbar.open('URL generated', 'Close', ErrorSnackBarconfig);
        }
      },
      (error) => {
        this.snackbar.open(
          error.error ?? 'URL generated',
          'Close',
          ErrorSnackBarconfig
        );
      }
    );
  }
}
