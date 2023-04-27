import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DashboardRoutingModule } from './dashboard-routing.module';
import { DashboardLayoutComponent } from './dashboard-layout/dashboard-layout.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { DashboardService } from './services/dashboard.service';
import { CreateUrlComponent } from './create-url/create-url.component';


@NgModule({
  declarations: [
    DashboardLayoutComponent,
    DashboardComponent,
    CreateUrlComponent
  ],
  imports: [
    CommonModule,
    DashboardRoutingModule
  ],
  bootstrap: [ CreateUrlComponent ],
  providers:[DashboardService]
})
export class DashboardModule { }
