import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormGroupDirective, FormControl, NgForm } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { Location } from '@angular/common';
import { RestClient } from '../../shared/services/rest-client';
import { E_SERVICE_URL, SAVE_REGISTERATION_INFO, DESIGNATION_LIST } from '../../shared/constant/api';
import { AbstractControl } from '@angular/forms';
import { Observable } from 'rxjs';
import { first } from 'rxjs/internal/operators/first';
import { startWith, map } from 'rxjs/operators';
import { RegistrationService } from './register.service';
import { Registration } from '../../shared/model/model/registration';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from '../../shared/services/notification.service';
import { ok } from 'assert';


// @ViewChild('myModal') myModal;
/** Error when invalid control is dirty, touched, or submitted. */
export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

export interface DesignationList {
  oid: string;
  designationBn : string;
  designationEn : string;
}

// Password Validation
export class ConfirmPasswordValidator {
  static MatchPassword(control: AbstractControl) {
    let password = control.get('password').value;
    let confirmPassword = control.get('confirmPassword').value;
    if (password != confirmPassword) {
      control.get('confirmPassword').setErrors({ ConfirmPassword: true });
    }
    else {
      return null;
    }
  }
}


@Component({
  selector: 'app-dashboard',
  templateUrl: 'register.component.html',
  styleUrls: ['register.component.scss']
})
export class RegisterComponent implements OnInit{
  registerForm: FormGroup;
  designationList: DesignationList[];
  filteredDesignationList: Observable<DesignationList[]>;
  // radioFieldValueList = [
  //   {text: 'Male', value: '1'},
  //   {text: 'Female', value: '2'}
  // ]
  public mdlSampleIsOpen : boolean = false;
  matcher = new MyErrorStateMatcher();
  constructor(private location: Location, private translate: TranslateService, private notificationService: NotificationService, private registrationService: RegistrationService,  private fb: FormBuilder, private http : RestClient) { }

  ngOnInit(){
    this.registerForm = this.fb.group({
      'userName' : ['', Validators.required],
      'password' : ['', Validators.required],
      'confirmPassword' : ['', Validators.required],
      'nameBn' : ['', Validators.required],
      'nameEn' : ['', Validators.required],
      'designation': ['', Validators.required],
      'fatherName' : [''],
      'motherName' : [''],
      'gender' : [''],
      'dateOfBirth' : [''],
      'email' : ['', Validators.email],
      'mobileNo' : ['', Validators.required],
      'nidNo' : ['', Validators.required],
      'presentAddress' : [''],
      'permanentAddress' : [''],
      'status' : ['Submitted']
    }
    , { validator: ConfirmPasswordValidator.MatchPassword });
    this.getDesignationList()
    this.translate.setDefaultLang('bn');
  }

  getDesignationList() {
    this.registrationService.getDesignationList(this.registerForm.value.designation["oid"]).subscribe(result => {
 
      this.designationList = result['body']['data'];
      this.filteredDesignationList = this.registerForm.get('designation').valueChanges
        .pipe(
          startWith<string | DesignationList>(''),
          map(value => typeof value === 'string' ? value : value.designationBn),
          map(name => name ? this._filterDesignation(name) : this.designationList.slice())
        );
      
    });
  }

  goBack() {
    this.location.back();
  }
  

 

  registerInfo(){
    let body: Registration = this.modifier(this.registerForm)
    this.registrationService.registerInfo(body).subscribe(result => {
      // if (result.body.data == true){
      //   this.registerForm.reset();
      // }
      // this.clearForm()
      this.notificationService.showSuccess('সফলভাবে নিবন্ধন সম্পন্ন হয়েছে');
      console.log(result);
      this.mdlSampleIsOpen = true;
      console.log(this.mdlSampleIsOpen)
    });
  }

  clearForm() {
    this.registerForm.reset();
   }

  modifier(obj){
    let body = obj.value
    body.designation = body.designation["oid"]
    console.log(body)
    return body
  }

  displayfilterDesignation(name?: DesignationList): string | undefined {
    return name ? name.designationBn : undefined;
  }

  private _filterDesignation(name: string): DesignationList[] {
    const filterValue = name.toLowerCase();
    return this.designationList.filter(option => option.designationBn.toLowerCase().indexOf(filterValue) === 0);
  }
}
