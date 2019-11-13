import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PiSharedModule } from 'app/shared/shared.module';
import { ProcessTemplateMpPiComponent } from './process-template-mp-pi.component';
import { ProcessTemplateMpPiDetailComponent } from './process-template-mp-pi-detail.component';
import { ProcessTemplateMpPiUpdateComponent } from './process-template-mp-pi-update.component';
import {
  ProcessTemplateMpPiDeletePopupComponent,
  ProcessTemplateMpPiDeleteDialogComponent
} from './process-template-mp-pi-delete-dialog.component';
import { processTemplateRoute, processTemplatePopupRoute } from './process-template-mp-pi.route';

const ENTITY_STATES = [...processTemplateRoute, ...processTemplatePopupRoute];

@NgModule({
  imports: [PiSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProcessTemplateMpPiComponent,
    ProcessTemplateMpPiDetailComponent,
    ProcessTemplateMpPiUpdateComponent,
    ProcessTemplateMpPiDeleteDialogComponent,
    ProcessTemplateMpPiDeletePopupComponent
  ],
  entryComponents: [ProcessTemplateMpPiDeleteDialogComponent]
})
export class PiProcessTemplateMpPiModule {}
