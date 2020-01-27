import { NgModule } from '@angular/core';
import { ScheduleModule, DayService, WeekService, WorkWeekService, MonthService, AgendaService, MonthAgendaService, TimelineViewsService, TimelineMonthService, ResizeService, ExcelExportService } from '@syncfusion/ej2-angular-schedule';
import { PageService, GroupService, SortService, FilterService, ColumnMenuService, EditService, PdfExportService, ContextMenuService, ToolbarService, CommandColumnService, GridModule, PagerModule, DetailRowService } from '@syncfusion/ej2-angular-grids';
import { DialogModule } from '@syncfusion/ej2-angular-popups';
import { ToastModule } from '@syncfusion/ej2-angular-notifications';


@NgModule({
    exports: [ScheduleModule, GridModule, PagerModule, DialogModule, ToastModule ],
    providers: [DayService, WeekService, WorkWeekService, MonthService, AgendaService, MonthAgendaService, TimelineViewsService, TimelineMonthService,
        PageService, ResizeService, GroupService, SortService, FilterService, ColumnMenuService, EditService, ExcelExportService, PdfExportService, ContextMenuService,
        ToolbarService, CommandColumnService, DetailRowService]
})
export class SyncfusionModule { }
