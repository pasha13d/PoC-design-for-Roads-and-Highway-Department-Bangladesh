import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from '@angular/common';
import { RestClient } from '../../../shared/services/rest-client';
import { RegistrationService } from '../../../core/register/register.service';
import { Registration } from '../../../shared/model/model/registration';
import { SendMail } from '../../../shared/model/model/send-mail';
import { ErrorStateMatcher } from '@angular/material/core';
import { FormGroup, FormBuilder, Validators, FormGroupDirective, FormControl, NgForm } from '@angular/forms';
import { NotificationService } from '../../../shared/services/notification.service';
import { CommonService } from '../../../shared/services/common.service';
import { TranslateService } from '@ngx-translate/core';
import * as moment from 'moment';

export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

@Component({
  selector: 'app-registration-details',
  templateUrl: './registration-details.component.html',
  styleUrls: ['./registration-details.component.scss']
})
export class RegistrationDetailsComponent implements OnInit {

  regDetailsForm: FormGroup;
  public details = {
    oid : "",
    designation : "",
    userName : "",
    password :"",
    nameBn :"",
    nameEn :"",
    fatherName :"",
    motherName :"",
    gender : "",
    dateOfBirth : "",
    email : "",
    mobileNo : "",
    nidNo : "",
    presentAddress : "",
    permanentAddress : "",
    status : "",
    approve : "",
    receive : ""
  };
  roles = [
      {text: 'ADMIN', value: 'RHD.ADMIN'},
      {text: 'USER', value: 'RHD.USER'}
    ]
  matcher = new MyErrorStateMatcher();
  constructor(private location: Location, private translate: TranslateService, private _router: Router, private registrationService: RegistrationService, private commonService: CommonService, private notificationService: NotificationService, private http: RestClient, private route: ActivatedRoute,  private fb: FormBuilder) { }

  goBack() {
    this.location.back();
  }
  ngOnInit() {
    this.getRegistrationDetails(this.route.snapshot.params.oid);
    this.regDetailsForm = this.fb.group({
      'comment': ['', Validators.required],
      'oid' : this.route.snapshot.params.oid,
      'role': ['', Validators.required]
    });
  }
  getRegistrationDetails(routerParams){
    this.registrationService.getRegistrationDetails(routerParams).subscribe(data => {
      this.details = data.body.data;
      let lang = this.translate.currentLang;
      (lang === 'bn') ?  moment.locale('bn') : moment.locale('en');
      this.details.dateOfBirth= moment(this.details.dateOfBirth).format("DD-MMM-YYYY")
    })
  }

  regApproved(){
    let body: Registration = this.modifier(this.regDetailsForm)
    this.registrationService.regApproved(body).subscribe(result => {
      this.notificationService.showSuccess('অনুমোদন সম্পন্ন হয়েছে');
      let message = "Hello, "+ this.details.nameEn +". Your registration has been approved. Your User name is: "+ this.details.userName +" and Password is: "+ this.details.password
      this.sendMail(message)
      console.log(result)
      this._router.navigate(['/registration']);
    });
  }

  regReceived(){
    let body: Registration = this.modifier(this.regDetailsForm)
    this.registrationService.regReceived(body).subscribe(result => {
      this.notificationService.showSuccess('গৃহীত হয়েছে');
      let message = "Hello, "+ this.details.nameEn +". Your registration has been received. Your User name is: "+ this.details.userName +" and Password is: "+ this.details.password
      this.sendMail(message)
      console.log(result)
    });
  }

  sendMail(message){
    let body: SendMail = {
      toAddress: this.details.email,
      subject: "Registration Information",
      body: message
    }
    this.commonService.sendMail(body).subscribe(result => {
      // console.log(body)
    });
  }

  modifier(obj){
    let body = obj.value
    console.log(body)
    return body
  }

}
