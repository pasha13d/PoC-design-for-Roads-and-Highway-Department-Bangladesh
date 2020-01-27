import { Component, OnInit, ChangeDetectorRef, DoCheck } from '@angular/core';
import { Router, Event, NavigationEnd, NavigationStart, NavigationCancel, NavigationError } from '@angular/router';
import { LoaderService } from './shared/services/loader.service';
import { SwUpdate } from '@angular/service-worker';
import { loadCldr } from '@syncfusion/ej2-base';
import { SlimLoadingBarService } from 'ng2-slim-loading-bar';
declare var require: any;

// loadCldr(
//     require('../../node_modules/cldr-data/supplemental/numberingSystems.json'),
//     require('../../node_modules/cldr-data/main/bn/ca-gregorian.json'),
//     require('../../node_modules/cldr-data/main/bn/currencies.json'),
//     require('../../node_modules/cldr-data/main/bn/numbers.json'),
//     require('../../node_modules/cldr-data/main/bn/timeZoneNames.json')
// );

@Component({
  // tslint:disable-next-line
  selector: 'body',
  template: '<router-outlet></router-outlet>'
})
export class AppComponent implements OnInit, DoCheck {
  showLoader: boolean;
  constructor(private router: Router, private loaderService: LoaderService,
     private updates: SwUpdate, private loadingBar: SlimLoadingBarService, private cdr: ChangeDetectorRef) {
        updates.available.subscribe(event => {
            updates.activateUpdate().then(() => document.location.reload());
        });
   }

  ngOnInit() {
      this.router.events.subscribe((event: Event) => {
        this.navigationInterceptor(event);
      });
  }

    private navigationInterceptor(event: Event): void {
        if (event instanceof NavigationStart) {
            this.loadingBar.start();
            this.loaderService.display(true);
        }
        if (event instanceof NavigationEnd) {
            this.loadingBar.complete();
            this.loaderService.display(false);
        }
        if (event instanceof NavigationCancel) {
            this.loadingBar.stop();
            this.loaderService.display(false);
        }
        if (event instanceof NavigationError) {
            this.loadingBar.stop();
            this.loaderService.display(false);
        }
        window.scrollTo(0, 0);
    }

    public ngDoCheck(): void {
        this.cdr.detectChanges();
    }
}
