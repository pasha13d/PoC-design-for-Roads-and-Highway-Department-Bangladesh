import { Component, OnInit } from '@angular/core';
import { RoadService } from '../e-service/requisition/road/road.service';
import { RegistrationService } from '../core/register/register.service';

@Component({
  templateUrl: 'dashboard.component.html',
  styleUrls: ['dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  public totalUserCount = 0;
  public totalRoadCount = 0;
  constructor(private roadService: RoadService, private registrationService: RegistrationService) { }
  
  getUserCount() {
    this.registrationService.getRegistrationList().subscribe(data => {
      this.totalUserCount = data.body.data.length;
    });
  }
  getRoadCount() {
    this.roadService.getRoadList().subscribe(data => {
      this.totalRoadCount = data.body.data.length;
    });
  }

  ngOnInit() {
    this.getRoadCount()
    this.getUserCount()
  }
}

