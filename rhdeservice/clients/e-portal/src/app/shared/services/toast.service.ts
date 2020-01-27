import { Injectable } from '@angular/core';
import { Toast, ToastModel } from '@syncfusion/ej2-notifications';

@Injectable({ providedIn: 'root'})
export class ToastService {
  public toastInstance: Toast;
  public toasts: { [key: string]: Object }[] = [
        { title: 'সতর্কতা!', cssClass: 'e-toast-warning', icon: 'e-warning toast-icons' },
        { title: 'সফলতা!',  cssClass: 'e-toast-success', icon: 'e-success toast-icons' },
        { title: 'ত্রুটি!', cssClass: 'e-toast-danger', icon: 'e-error toast-icons' },
        { title: 'তথ্য!', cssClass: 'e-toast-info', icon: 'e-info toast-icons' }
  ];
  constructor() {}

  // To create the toast component
  createToast: Function = (element: HTMLElement, model: ToastModel): Toast => {
    if (!element.classList.contains('e-toast')) {
      this.toastInstance = new Toast(model, element);
    }
    return this.toastInstance;
  }

  // To show the toast component
  showWarningToast: Function = (elemnet: HTMLElement, model: ToastModel) => {
    this.toastInstance = this.createToast(elemnet, model);
    this.toastInstance.position.X = 'Right';
    this.toastInstance.position.Y = 'Top';
    this.toastInstance.showCloseButton = true;
    this.toastInstance.show(this.toasts[0]);
  }

  showSuccessToast: Function = (elemnet: HTMLElement, model: ToastModel) => {
    this.toastInstance = this.createToast(elemnet, model);
    this.toastInstance.position.X = 'Right';
    this.toastInstance.position.Y = 'Top';
    this.toastInstance.showCloseButton = true;
    this.toastInstance.show(this.toasts[1]);
  }

showInfoToast: Function = (elemnet: HTMLElement, model: ToastModel) => {
    this.toastInstance = this.createToast(elemnet, model);
    this.toastInstance.position.X = 'Right';
    this.toastInstance.position.Y = 'Top';
    this.toastInstance.showCloseButton = true;
    this.toastInstance.show(this.toasts[3]);
}

showErrorToast: Function = (elemnet: HTMLElement, model: ToastModel) => {
    this.toastInstance = this.createToast(elemnet, model);
    this.toastInstance.position.X = 'Right';
    this.toastInstance.position.Y = 'Top';
    this.toastInstance.showCloseButton = true;
    this.toastInstance.show(this.toasts[2]);
}

  // To hide the toast component
  hideToast: Function = () => {
    if (this.toastInstance) {
      this.toastInstance.hide();
    }
  }

  // To hide the all toast component
  hideToastAll: Function = () => {
    if (this.toastInstance) {
      this.toastInstance.hide('All');
    }
  }
}
