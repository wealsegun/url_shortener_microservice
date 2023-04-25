

import { NgModule } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatMenuModule } from '@angular/material/menu';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import {  MatTabsModule} from '@angular/material/tabs';
import {MatProgressBarModule } from '@angular/material/progress-bar';
import { MatStepperModule}  from '@angular/material/stepper';
import{ MatFormFieldModule } from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';


@NgModule({
  imports: [
    MatButtonModule,
    MatMenuModule,
    MatToolbarModule,
    MatIconModule,
    MatCardModule,
    MatTabsModule,
    MatProgressBarModule,
    MatStepperModule,
    MatFormFieldModule,
    MatInputModule
  ],
  exports: [
    MatButtonModule,
    MatMenuModule,
    MatToolbarModule,
    MatIconModule,
    MatCardModule,
    MatTabsModule,
    MatProgressBarModule,
    MatStepperModule,
    MatFormFieldModule,
    MatInputModule
  ]
})
export class MaterialModule {
}
