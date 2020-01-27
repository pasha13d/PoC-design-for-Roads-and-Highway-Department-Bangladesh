import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { FormGroup, FormBuilder, Validators, FormGroupDirective, FormControl, NgForm } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { RestClient } from '../../../../shared/services/rest-client';
import { E_SERVICE_URL, SAVE_ROAD, DIVISION_LIST, DISTRICT_LIST } from '../../../../shared/constant/api';
import { Observable } from 'rxjs';
import { startWith, map } from 'rxjs/operators';
import { RoadService } from '../road.service';
import { NotificationService } from '../../../../shared/services/notification.service';
import { ActivatedRoute, Router } from '@angular/router';

/** Error when invalid control is dirty, touched, or submitted. */
export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

export interface DivisionName {
  oid: string;
  divisionNameEn: string;
  divisionNameBn: string;
}

export interface DistrictName {
  oid: string;
  districtNameEn: string;
  districtNameBn: string;
}

export interface UpazillaName {
  oid: string;
  upazillaNameBn: string;
  upazillaNameEn: string;
}

@Component({
  selector: 'app-road-add',
  templateUrl: './road-add.component.html',
  styleUrls: ['./road-add.component.scss']
})
export class RoadAddComponent implements OnInit {

  roadForm: FormGroup;
  divisionNames: DivisionName[];
  filteredDivisionNames: Observable<DivisionName[]>;
  districtNames: DistrictName[];
  filteredDistrictNames: Observable<DistrictName[]>;
  upazillaNames: UpazillaName[];
  filteredUpazillaNames: Observable<UpazillaName[]>;
  stepList = [];

  matcher = new MyErrorStateMatcher();

  constructor(private location: Location, private fb: FormBuilder, private router: ActivatedRoute, private _route: Router, private notificationService: NotificationService, private roadService: RoadService, private http: RestClient) { }

  goBack() {
    this.location.back();
  }
  ngOnInit() {
    this.roadForm = this.fb.group({
      'division': [null, Validators.required],
      'district': [null, Validators.required],
      'upazilla': [null, Validators.required],
      'postalCode': [null, Validators.required],
      'location': [null, Validators.required],
      'startPoint': [null, Validators.required],
      'endPoint': [null, Validators.required],
      'purpose': [null, Validators.required],
      'isriverOrWaterbodynear': [null],
      'status': ['রাস্তা চাহিদাপত্র অনুরোধকৃত'],
      "nextStep":['RRS-2']
    });
    
    this.getDivisionList()
    this.getStepList()
  }
  getStepList(){
    this.roadService.getStepList("P-01").subscribe(result => {
      this.stepList = result.body.data
    });
  }
  getDivisionList() {
    let body = {
      "operation": "Get-Division-List"
    }
    this.http.post(E_SERVICE_URL + DIVISION_LIST, body)
      .subscribe(
        (response) => {
          this.divisionNames = response["body"]["data"]
          this.filteredDivisionNames = this.roadForm.get('division').valueChanges
            .pipe(
              startWith<string | DivisionName>(''),
              map(value => typeof value === 'string' ? value : value.divisionNameBn),
              map(name => name ? this._filterDivision(name) : this.divisionNames.slice())
            );
          console.log(this.divisionNames)
        },
        (error) => console.log(error)
      );
  }
  getDistrictList() {
    this.roadService.getDistrictList(this.roadForm.value.division["oid"]).subscribe(result => {
 
      this.districtNames = result['body']['data'];
      this.filteredDistrictNames = this.roadForm.get('district').valueChanges
        .pipe(
          startWith<string | DistrictName>(''),
          map(value => typeof value === 'string' ? value : value.districtNameBn),
          map(name => name ? this._filterDistrict(name) : this.districtNames.slice())
        );
      
    });
  }

  getUpazillaList() {
  
    this.roadService.getUpazillaList(this.roadForm.value.district["oid"]).subscribe(result => {
      
      this.upazillaNames = result['body']['data'];
      console.log(this.upazillaNames)
      this.filteredUpazillaNames = this.roadForm.get('upazilla').valueChanges
        .pipe(
          startWith<string | UpazillaName>(''),
          map(value => typeof value === 'string' ? value : value.upazillaNameBn),
          map(name => name ? this._filterUpazilla(name) : this.upazillaNames.slice())
        );
      
    });
  }
  saveRoad() {
    let body = this.modifier(this.roadForm)
    this.roadService.saveRoad(body).subscribe(result => {
      if (result.body.data !== null){
        this.stepList.forEach( (x) => {
          let instance = {
            roadrequisitionoid : result.body.data,
            stepoid : x.oid,
            isdone : "false" ,
            isactive : "false"
          }
          this.roadService.saveStepInstance(instance).subscribe( data =>{
            console.log(data)
            if(data.header.responseCode=="200"){
              this.notificationService.showSuccess('সফলভাবে সংরক্ষণ হয়েছে');
              this._route.navigate(['/requisition/road']);
            }
          })
        })
     
      }
      console.log(result)
    });
  }

  modifier(obj){
    let body =  { ...obj.value }
    body.division = body.division["oid"]
    body.district = body.district["oid"]
    body.upazilla = body.upazilla["oid"]
    return body
  }

  displayfilterDivision(name?: DivisionName): string | undefined {
    return name ? name.divisionNameBn : undefined;
  }
  private _filterDivision(name: string): DivisionName[] {
    const filterValue = name.toLowerCase();
    return this.divisionNames.filter(option => option.divisionNameBn.toLowerCase().indexOf(filterValue) === 0);
  }

  displayfilterDistrict(name?: DistrictName): string | undefined {
    return name ? name.districtNameBn : undefined;
  }
  private _filterDistrict(name: string): DistrictName[] {
    const filterValue = name.toLowerCase();
    return this.districtNames.filter(option => option.districtNameBn.toLowerCase().indexOf(filterValue) === 0);
  }
  
  displayfilterUpazilla(name?: UpazillaName): string | undefined {
    return name ? name.upazillaNameBn : undefined;
  }
  private _filterUpazilla(name: string): UpazillaName[] {
    const filterValue = name.toLowerCase();
    return this.upazillaNames.filter(option => option.upazillaNameBn.toLowerCase().indexOf(filterValue) === 0);
  }
}
