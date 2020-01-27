import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { FormGroupDirective, FormControl, NgForm } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { RoadService } from '../road.service'
import * as moment from 'moment'; 
import { TranslateService } from '@ngx-translate/core';
import { HttpEvent, HttpEventType, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/internal/Subscription';
import { saveAs } from 'file-saver';

/** Error when invalid control is dirty, touched, or submitted. */
export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

@Component({
  selector: 'app-road-details',
  templateUrl: './road-details.component.html',
  styleUrls: ['./road-details.component.scss']
})
export class RoadDetailsComponent implements OnInit {

  progress: number = 0;
  public subscription: Subscription;
  oid: string;
  formData: any;
  stepList: any;
  formMetaData: any = {
    formComponentId:"", 
    oid:"", 
    forward:"", 
    backward:"", 
    approve:"", 
    nextStepOid:"",
    previousStepOid : "",
  };
  stepOid: any[];
  approveBtn : boolean;
  previousBtn : boolean;
  nextBtn : boolean;
  btnShow  = false;
  details = {
    'division': "",
    'district': "",
    'upazilla': "",
    'postalCode': "",
    'location': "",
    'startPoint': "",
    'endPoint': "",
    'purpose': "",
    'isriverOrWaterbodynear': "",
    'status': "",
    "nextStep": ""
  };
  msg: string;
  stateList : any[];
  resFilename : any;
  actualFilename : any;
  loginStepMapList : any;
  forwardPerson = {
    loginid: "",
    name: "",
    sortOrder: "",
    stepName: "",
    stepoid: ""
  };
  constructor(private location: Location, private roadService: RoadService,public translate: TranslateService ,public route: ActivatedRoute) { }

  goBack() {
    this.location.back();
  }

  ngOnInit() {
    this.oid = this.route.snapshot.params.oid
    this.getFormComponetID(this.oid)
    this.getRoadDetails()
    this.getInstanceStepList()
    this.getLoginStepMapping()
  }
  
  getRoadDetails() {
    this.roadService.getRoadDetails(this.oid).subscribe(data => {
      this.details = data.body.data
      this.getStateDetails()
    })
  }

  getFormComponetID(routerParams){
    this.roadService.getFormComponetID(routerParams).subscribe(data => {
      if (data.body.data == null || data.body== {}){
       this.nextBtn = true
       this.previousBtn = true
       this.approveBtn = true
      }else{
        this.btnShow = true;
        this.formMetaData = data.body.data;
        if(this.formMetaData.forward == 'true'){
          this.nextBtn = false
        }else {
          this.nextBtn = true
        }
        if(this.formMetaData.backward == 'true'){
          this.previousBtn = false
        }else {
          this.previousBtn = true
        }
        if(this.formMetaData.approve == 'true'){
          this.approveBtn = false
        }else {
          this.approveBtn = true
        }
        
      }
    })
  }

  goToPrevious(){
    this.progress=0;
    let body = {
      oid : this.oid,
      nextStep:  this.formMetaData.previousStepOid,
      currentStepOid : this.formMetaData.previousStepOid,
      fromstepOid : this.formMetaData.oid,
      step : "Previous",
      fileName: this.resFilename,
      actualFilename : this.actualFilename

    }
    this.roadService.approveRoad(body).subscribe(data => {
      if (data.body.data == true){
        this.getRoadDetails()
        this.getFormComponetID( this.oid )
        this.getInstanceStepList()
        this.actualFilename = "";
        this.resFilename = ""
      }
    })
  }
  
  goToNext(){
    this.progress=0;
    let body = {
      oid : this.oid,
      nextStep:  this.formMetaData.nextStepOid,
      currentStepOid : this.formMetaData.oid,
      fromstepOid : this.formMetaData.oid,
      step: "Next",
      fileName: this.resFilename,
      actualFilename : this.actualFilename
    }
    this.roadService.approveRoad(body).subscribe(data => {
      if (data.body.data == true){
        this.getRoadDetails()
        this.getFormComponetID( this.oid )
        this.getInstanceStepList()
        this.actualFilename = "";
        this.resFilename = ""
      }
    })
  }

  approve(){
    this.progress=0;
    let body = {
      oid : this.oid,
      nextStep:  "",
      currentStepOid : this.formMetaData.oid,
      fromstepOid : this.formMetaData.oid,
      step: "Approve",
      fileName: this.resFilename,
      actualFilename : this.actualFilename
    }
    this.roadService.approveRoad(body).subscribe(data => {
      if (data.body.data == true){
        this.getRoadDetails()
        this.getFormComponetID( this.oid )
        this.getInstanceStepList()
        this.actualFilename = "";
        this.resFilename = ""
      }
    })
  }

  assignPerson(){
    this.progress=0;
    console.log(this.forwardPerson)
    let body = {
      oid : this.oid,
      nextStep:  this.forwardPerson.stepoid,
      currentStepOid :  this.forwardPerson.stepoid,
      fromstepOid : this.formMetaData.oid,
      step : this.forwardPerson.sortOrder,
      fileName: this.resFilename,
      actualFilename : this.actualFilename

    }
    this.roadService.assignPerson(body).subscribe(data => {
      if (data.body.data == true){
        this.getRoadDetails()
        this.getFormComponetID( this.oid )
        this.getInstanceStepList()
        this.actualFilename = "";
        this.resFilename = ""
      }
    })

  }
   
  getStateDetails() {
    this.roadService.getStateDetails(this.oid).subscribe(result => {
      this.stateList = result.body.data
      for (var i = this.stateList.length - 1; i >= 0; i--) {
        let lang = this.translate.currentLang;
        (lang === 'bn') ?  moment.locale('bn') : moment.locale('en');
        this.stateList[i].createdon = moment(this.stateList[i].createdon).format("DD-MMM-YYYY HH:MM")
      }
    });
  }

  getInstanceStepList() {
    this.roadService.getInstanceStepList(this.oid).subscribe(result => {
      this.stepList = result.body.data
      console.log(  this.stepList );
    });
  }

  getLoginStepMapping() {
    this.roadService.getLoginingStepMapping("P-01").subscribe(result => {
      this.loginStepMapList = result.body.data
      console.log("dc",result)
    });
  }
  fileUpload(doc) {
    let fileList: FileList = doc.target.files;
    // if (fileList.length > 0) {
        let file: File = doc.target.files[0];
        let formData: FormData = new FormData();
        formData.append('file', file, file.name);
        formData.append('oid', this.oid);
        this.actualFilename = file.name
        this.formData = formData;
       this.subscription = this.roadService.upload(file, formData).subscribe(event => {
          if (event.type === HttpEventType.UploadProgress) {
            this.progress = Math.round(event.loaded / event.total * 100);
            console.log(`Uploaded ${this.progress}%`);
          } else if (event instanceof HttpResponse) {
            if (event.status === 200 && event.body['header'].responseCode === '200') {
              this.resFilename = event.body['body']['fileName'];
            } else {
              // this.isSuccess = false;
              // this.isError = true;
              // this.progress.percentage = 0;
              // this.notificationService.showError('Unable to uploded policy data file.');
            }
        }
    }, error => {
      // this.reset();
      // this.notificationService.showError(error);
    }, () => {
        fileList = undefined;
        if (this.subscription) {this.subscription.unsubscribe(); }
        })
    // }
  }

  fileDownload(fileName, actualFilename){
    if(fileName === undefined || fileName === "" || fileName === null || actualFilename === ""){
      return
    }
    else{
      this.roadService.fileDownload(fileName).subscribe(result => {
        const blob = new Blob([result], { type: 'application/pdf;charset=utf-8' });
        saveAs(blob, actualFilename);
        console.log("noob",result)
      })

    }
  }
}