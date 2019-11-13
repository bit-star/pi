import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PiSharedModule } from 'app/shared/shared.module';
import { ProcessInstanceMpPiComponent } from './process-instance-mp-pi.component';
import { ProcessInstanceMpPiDetailComponent } from './process-instance-mp-pi-detail.component';
import { ProcessInstanceMpPiUpdateComponent } from './process-instance-mp-pi-update.component';
import {
  ProcessInstanceMpPiDeletePopupComponent,
  ProcessInstanceMpPiDeleteDialogComponent
} from './process-instance-mp-pi-delete-dialog.component';
import { processInstanceRoute, processInstancePopupRoute } from './process-instance-mp-pi.route';

const ENTITY_STATES = [...processInstanceRoute, ...processInstancePopupRoute];

@NgModule({
  imports: [PiSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProcessInstanceMpPiComponent,
    ProcessInstanceMpPiDetailComponent,
    ProcessInstanceMpPiUpdateComponent,
    ProcessInstanceMpPiDeleteDialogComponent,
    ProcessInstanceMpPiDeletePopupComponent
  ],
  entryComponents: [ProcessInstanceMpPiDeleteDialogComponent]
})
export class PiProcessInstanceMpPiModule {}
