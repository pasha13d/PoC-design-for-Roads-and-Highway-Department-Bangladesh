import { Injectable } from '@angular/core';
import {  createSpinner,  showSpinner,  setSpinner,  hideSpinner, SpinnerArgs, SetSpinnerArgs } from '@syncfusion/ej2-popups';

@Injectable({ providedIn: 'root'})
export class SpinnerService {

  constructor() {

  }

  hideSpinner: Function = (element: HTMLElement) => {
    hideSpinner(element);
  }
  showSpinner: Function = (element: HTMLElement) => {
    this.createSpinner({ target: element, label: 'Loading ...', });
    showSpinner(element);
  }
  createSpinner: Function = (spinnerArgs: SpinnerArgs) => {
    createSpinner({
      target: spinnerArgs.target,
      label: spinnerArgs.label,
      cssClass: spinnerArgs.cssClass,
      template: spinnerArgs.template,
      type: spinnerArgs.type,
      width: spinnerArgs.width,
    });
  }

  setSpinner: Function = (spinnerArgs: SetSpinnerArgs) => {

    setSpinner({
      cssClass: spinnerArgs.cssClass,
      template: spinnerArgs.template,
      type: spinnerArgs.type,
    });

  }

}
