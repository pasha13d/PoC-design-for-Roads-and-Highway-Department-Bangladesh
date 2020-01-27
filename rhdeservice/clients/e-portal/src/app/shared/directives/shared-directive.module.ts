import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HasRoleDirective } from './has-role.directive';

@NgModule({
    imports: [
        CommonModule
    ],
    providers: [HasRoleDirective],
    declarations: [HasRoleDirective],
    exports: [HasRoleDirective]
})
export class SharedDirectiveModule { }
