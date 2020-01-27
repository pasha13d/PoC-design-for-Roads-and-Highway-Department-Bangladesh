import { Component, OnInit, Input } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormGroupDirective, FormControl, NgForm } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { RoadService } from '../../road.service'
import * as moment from 'moment'; 
import { TranslateService } from '@ngx-translate/core';

/** Error when invalid control is dirty, touched, or submitted. */
export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

@Component({
  selector: 'app-road-requisition-comment',
  templateUrl: './road-requisition-comment.component.html',
  styleUrls: ['./road-requisition-comment.component.scss']
})
export class RoadRequisitionCommentComponent implements OnInit {
  roadRequisitionCommentForm: FormGroup;
  matcher = new MyErrorStateMatcher();
  
  comments : any[] = [];
  segmentedComments : any[] = [];
  currentIndex = 0;
  showMorebtn = true;
  
  @Input() oid : string;
  @Input() stepOid : string;
  constructor(private fb: FormBuilder, private roadService: RoadService, public translate: TranslateService) { }
  
  ngOnInit() {
    this.roadRequisitionCommentForm = this.fb.group({
      "comment" : ["", Validators.required]
    });
    this.getAllComment();
  }
  
  LoadMore(){
    this.currentIndex += 3
    if(this.comments.length <= this.currentIndex){
      this.showMorebtn = false;
    }
    this.segmentedComments = this.comments.slice(0, this.currentIndex)
  }
  
  saveComment() {
    let body = {
      roadRequisitionOid: this.oid,
      comment: this.roadRequisitionCommentForm.value.comment,
      stepOid: this.stepOid
    }
    this.roadService.saveComment(body).subscribe(result => {
      if (result.body.data == true) {
        this.roadRequisitionCommentForm.reset()
        this.getAllComment();
      }
    });
  }
  
  getAllComment() {
    this.roadService.getAllComment(this.oid).subscribe(result => {
      this.comments = result.body.data
      for (var i = this.comments.length - 1; i >= 0; i--) {
        let lang = this.translate.currentLang;
        (lang === 'bn') ?  moment.locale('bn') : moment.locale('en');
        this.comments[i].createdon = moment(this.comments[i].createdon).format("ddd, DD MMM YYYY, h:mm a")
      }
      this.LoadMore();
    });
  }
}