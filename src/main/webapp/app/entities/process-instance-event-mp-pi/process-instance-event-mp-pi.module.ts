import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PiSharedModule } from 'app/shared/shared.module';
import { ProcessInstanceEventMpPiComponent } from './process-instance-event-mp-pi.component';
import { ProcessInstanceEventMpPiDetailComponent } from './process-instance-event-mp-pi-detail.component';
import { ProcessInstanceEventMpPiUpdateComponent } from './process-instance-event-mp-pi-update.component';
import {
  ProcessInstanceEventMpPiDeletePopupComponent,
  ProcessInstanceEventMpPiDeleteDialogComponent
} from './process-instance-event-mp-pi-delete-dialog.component';
import { processInstanceEventRoute, processInstanceEventPopupRoute } from './process-instance-event-mp-pi.route';

const ENTITY_STATES = [...processInstanceEventRoute, ...processInstanceEventPopupRoute];

@NgModule({
  imports: [PiSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProcessInstanceEventMpPiComponent,
    ProcessInstanceEventMpPiDetailComponent,
    ProcessInstanceEventMpPiUpdateComponent,
    ProcessInstanceEventMpPiDeleteDialogComponent,
    ProcessInstanceEventMpPiDeletePopupComponent
  ],
  entryComponents: [ProcessInstanceEventMpPiDeleteDialogComponent]
})
export class PiProcessInstanceEventMpPiModule {}
