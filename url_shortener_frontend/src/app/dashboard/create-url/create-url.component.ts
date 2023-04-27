import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-create-url',
  templateUrl: './create-url.component.html',
  styleUrls: ['./create-url.component.scss']
})
export class CreateUrlComponent {
  url: string = '';
  customRequested: boolean = false;

  @Output() submitEvent = new EventEmitter<{url: string, customRequested: boolean}>();

  submit() {
    this.submitEvent.emit({url: this.url, customRequested: this.customRequested});
  }
}

