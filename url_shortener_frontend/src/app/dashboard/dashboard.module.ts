import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DashboardRoutingModule } from './dashboard-routing.module';
import { DashboardLayoutComponent } from './dashboard-layout/dashboard-layout.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { DashboardService } from './services/dashboard.service';
import { CreateUrlComponent } from './create-url/create-url.component';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatFormFieldModule } from '@angular/material/form-field';
import { ReactiveFormsModule } from '@angular/forms';



@NgModule({
  declarations: [
    DashboardLayoutComponent,
    DashboardComponent,
    CreateUrlComponent
  ],
  imports: [
    CommonModule,
    DashboardRoutingModule,
    ReactiveFormsModule,
    MatTableModule,
    MatPaginatorModule,
    MatFormFieldModule
  ],
  bootstrap: [ CreateUrlComponent ],
  providers:[DashboardService]
})
export class DashboardModule { }
