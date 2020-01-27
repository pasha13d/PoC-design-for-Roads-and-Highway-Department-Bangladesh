import { Injectable, OnInit } from '@angular/core';
import { scan } from 'rxjs/operators';
import { ReplaySubject } from 'rxjs/internal/ReplaySubject';
import { BehaviorSubject } from 'rxjs/internal/BehaviorSubject';


@Injectable({ providedIn: 'root'})
export class RolesService {
  // A stream that exposes all the roles a user has
  roles$ = new ReplaySubject<string[]>(1);

  // We leverage this roleUpdates$ to be able to update the roles
  // This is for demonstration purposes only
  roleUpdates$ = new BehaviorSubject([]);

  constructor() {
    this.roleUpdates$
      .pipe(
        scan((acc, next) => next, [])
      )
      .subscribe(this.roles$);
  }

  update(roles) {
    this.roleUpdates$.next(roles);
  }
}
