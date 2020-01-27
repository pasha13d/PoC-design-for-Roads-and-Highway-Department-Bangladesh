import { Component, OnInit, ViewChild } from '@angular/core';
import { setCulture, L10n } from '@syncfusion/ej2-base';
import { PAGE_SIZE, PAGE_COUNT } from '../../../../shared/constant/constant';
import GridBnIntl from '../../../../shared/utils/grid-bangla-intl.json';
import { PageSettingsModel, FilterSettingsModel, ContextMenuItem, PagerDropDown, Pager } from '@syncfusion/ej2-grids';
import { SearchParams } from '../../../../shared/model/request/search-params';
import { FormControl } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { GridComponent } from '@syncfusion/ej2-angular-grids';
import { StorageService } from '../../../../shared/services/storage.service';
import { RoadService  } from '../road.service';
Pager.Inject(PagerDropDown);

@Component({
  selector: 'app-road-list',
  templateUrl: './road-list.component.html',
  styleUrls: ['./road-list.component.scss']
})
export class RoadListComponent implements OnInit {

  public data: any[];
  public pageSettings: PageSettingsModel;
  public filterSettings: FilterSettingsModel;
  public contextMenuItems: ContextMenuItem[];
  public locale: any;
  public params = new SearchParams();
  public pageNum: number; serial: number;
  public searchText: FormControl = new FormControl();
  public currentOffset: number; 
  public totalCount = 0;
  @ViewChild('grid', { static: true }) public grid: GridComponent;
  constructor(private translate: TranslateService, private storageService: StorageService, private roadService: RoadService) { }

  getRoadList() {
    this.roadService.getRoadList().subscribe(data => {
      this.data = data.body.data;
      this.totalCount = data.length;
      this.serial = this.pageSettings.pageSize * (this.pageSettings.currentPage  - 1);
    });
  }

  pageChange(args) {
    if (args.currentPage) {
      this.pageSettings.currentPage = args.currentPage;
      this.params.offset = (args.currentPage - 1) * this.pageSettings.pageSize;
      this.params.limit = this.pageSettings.pageSize;
      this.getRoadList();
    }
  }

  dropDownChanged(args) {
    this.pageSettings.pageSize = args.pageSize;
    this.params.offset = (args.currentPage - 1) * this.pageSettings.pageSize;
    this.params.limit = this.pageSettings.pageSize;
    this.getRoadList();
  }

  setPagination() {
    this.pageNum = Number(this.storageService.read('PAGE_NUMBER'));
    if (this.pageNum === 0) {
      this.currentOffset = Number((this.pageSettings.currentPage - 1) * this.pageSettings.pageSize);
    } else {
      this.currentOffset = (Number(this.storageService.read('PAGE_NUMBER')) * Number(this.pageSettings.pageSize)) - Number(this.pageSettings.pageSize);
      this.pageSettings.currentPage = Number(this.storageService.read('PAGE_NUMBER'));
    }
    this.params.offset = this.currentOffset;
    this.params.limit = Number(this.pageSettings.pageSize);
  }

  dataTableIntl() {
    setCulture('bn-BD');
    L10n.load(GridBnIntl);
    this.pageSettings =  { pageSizes: true, pageSize: PAGE_SIZE,  pageCount: PAGE_COUNT, currentPage: 1 };
    this.filterSettings = { type: 'CheckBox' };
    this.contextMenuItems = ['Copy', 'PdfExport'];
    this.translate.onLangChange.subscribe(s => {
      this.locale = s.lang === 'en' ? 'en-US' : 'bn-BD';
      this.getRoadList();
    });
  }

  ngOnInit() {
    this.dataTableIntl();
  }

}
