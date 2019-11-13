import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PiSharedModule } from 'app/shared/shared.module';
import { FormComponentValuesMpPiComponent } from './form-component-values-mp-pi.component';
import { FormComponentValuesMpPiDetailComponent } from './form-component-values-mp-pi-detail.component';
import { FormComponentValuesMpPiUpdateComponent } from './form-component-values-mp-pi-update.component';
import {
  FormComponentValuesMpPiDeletePopupComponent,
  FormComponentValuesMpPiDeleteDialogComponent
} from './form-component-values-mp-pi-delete-dialog.component';
import { formComponentValuesRoute, formComponentValuesPopupRoute } from './form-component-values-mp-pi.route';

const ENTITY_STATES = [...formComponentValuesRoute, ...formComponentValuesPopupRoute];

@NgModule({
  imports: [PiSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    FormComponentValuesMpPiComponent,
    FormComponentValuesMpPiDetailComponent,
    FormComponentValuesMpPiUpdateComponent,
    FormComponentValuesMpPiDeleteDialogComponent,
    FormComponentValuesMpPiDeletePopupComponent
  ],
  entryComponents: [FormComponentValuesMpPiDeleteDialogComponent]
})
export class PiFormComponentValuesMpPiModule {}
