import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DateLocaleFilter } from './date-locale-filter';
import { LocalNumberPipe } from './locale-number.pipe';


@NgModule({
    imports: [
        CommonModule
    ],
    providers: [DateLocaleFilter, LocalNumberPipe],
    declarations: [DateLocaleFilter, LocalNumberPipe],
    exports: [DateLocaleFilter, LocalNumberPipe]
})
export class SharedPipesModule { }
