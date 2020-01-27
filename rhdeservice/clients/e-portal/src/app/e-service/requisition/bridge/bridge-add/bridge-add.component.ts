import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { FormGroup, FormBuilder, Validators, FormGroupDirective, FormControl, NgForm } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { RestClient } from '../../../../shared/services/rest-client';
import { E_SERVICE_URL, SAVE_BRIDGE } from '../../../../shared/constant/api';

/** Error when invalid control is dirty, touched, or submitted. */
export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}


@Component({
  selector: 'app-bridge-add',
  templateUrl: './bridge-add.component.html',
  styleUrls: ['./bridge-add.component.scss']
})
export class BridgeAddComponent implements OnInit {

  bridgeForm: FormGroup;
  matcher = new MyErrorStateMatcher();
  constructor(private location: Location,  private fb: FormBuilder, private http : RestClient) { }

  goBack() {
    this.location.back();
  }
  ngOnInit() {
    this.bridgeForm = this.fb.group({
      'division' : [null, Validators.required],
      'district' : [null, Validators.required],
      'upazilla' : [null, Validators.required],
      'union' : [null, Validators.required],
      'location' : [null, Validators.required],
      'startPoint' : [null, Validators.required],
      'endPoint' : [null, Validators.required],
      'purpose' : [null, Validators.required],
      'isNearByBridge' : [null],
      'otherBridgeName' : [null],

    });
  }
  saveBridge(){
    this.http.post(E_SERVICE_URL+SAVE_BRIDGE,this.bridgeForm)
    .subscribe(
      (response) =>{
      },
      (error) => console.log(error)
    );
  }
}
