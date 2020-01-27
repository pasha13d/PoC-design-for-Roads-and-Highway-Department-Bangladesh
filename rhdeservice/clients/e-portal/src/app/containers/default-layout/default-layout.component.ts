import { AuthService } from './../../core/auth/auth.service';
import { Component, OnDestroy, Inject, OnInit, ChangeDetectorRef, DoCheck } from '@angular/core';
import { DOCUMENT } from '@angular/common';
import { NavData, navItems } from '../../_nav';
import { TranslateService } from '@ngx-translate/core';
import { setCulture, setCurrencyCode } from '@syncfusion/ej2-base';
import { LoaderService } from '../../shared/services/loader.service';
import { CommonService } from '../../shared/services/common.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './default-layout.component.html'
})
export class DefaultLayoutComponent implements OnInit, OnDestroy, DoCheck {

  public navItems = navItems;
  // public navItems: NavData[];
  public userInfo: any; locale: string;
  public sidebarMinimized = true;
  private changes: MutationObserver;
  public element: HTMLElement;
  public showLanguage: string;
  public LANGUAGE_BANGLA = 'বাংলা';
  public LANGUAGE_ENGLISH = 'English';
  public showLoader: boolean;
  constructor(private translate: TranslateService, private cdr: ChangeDetectorRef, private commonService: CommonService,
     private loaderService: LoaderService, private authService: AuthService,  @Inject(DOCUMENT) _document?: any) {

      this.changes = new MutationObserver((mutations) => {
        this.sidebarMinimized = _document.body.classList.contains('sidebar-minimized');
      });
      this.element = _document.body;
      this.changes.observe(<Element>this.element, {
        attributes: true,
        attributeFilter: ['class']
      });
      this.translate.addLangs(['bn', 'en']);
      this.translate.setDefaultLang('bn');
      this.showLanguage = this.LANGUAGE_ENGLISH;
      // const browserLang = this.translate.getBrowserLang();
      this.translate.use('bn');
      // this.translate.use(browserLang.match(/bn|en/) ? browserLang : 'bn');
  }

  goToProfile() {

  }



  getUserInfo() {
    this.commonService.getUserBasicInfo().subscribe(result => {
        if (result != null) {
            this.userInfo = result['body']['data'];
        }
    });
  }


  // getMenuJsonData() {
  //   this.loaderService.display(true);
  //   this.commonService.getMenuJsonList(this.roleOid).subscribe(result => {
  //     // this.getEmployeeInfo();
  //     this.loaderService.display(false);
  //       if (result != null) {
  //           this.menuJson = result['body']['data']['menuJson'];
  //           this.navItems = JSON.parse(this.menuJson);
  //       }
  //   });
  // }

  ngOnInit(): void {
    this.loaderService.status.subscribe((val: boolean) => {
      this.showLoader = val;
    });
    this.getUserInfo();
  }

  logout() {
    this.authService.logout();
  }
  changeLang() {
    if (this.translate.currentLang === 'bn') {
        this.translate.use('en');
        setCulture('en');
        setCurrencyCode('USD');
        this.locale = 'en';
        this.showLanguage = this.LANGUAGE_BANGLA;
      } else {
        this.translate.use('bn');
        setCulture('bn');
        setCurrencyCode('BDT');
        this.locale = 'bn';
        this.showLanguage = this.LANGUAGE_ENGLISH;
      }
  }

  ngOnDestroy(): void {
    this.changes.disconnect();
  }

  public ngDoCheck(): void {
    this.cdr.detectChanges();
  }
}
