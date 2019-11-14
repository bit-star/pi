import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PiSharedModule } from 'app/shared/shared.module';
import { ProcessMsgTaskMpPiComponent } from './process-msg-task-mp-pi.component';
import { ProcessMsgTaskMpPiDetailComponent } from './process-msg-task-mp-pi-detail.component';
import { ProcessMsgTaskMpPiUpdateComponent } from './process-msg-task-mp-pi-update.component';
import {
  ProcessMsgTaskMpPiDeletePopupComponent,
  ProcessMsgTaskMpPiDeleteDialogComponent
} from './process-msg-task-mp-pi-delete-dialog.component';
import { processMsgTaskRoute, processMsgTaskPopupRoute } from './process-msg-task-mp-pi.route';

const ENTITY_STATES = [...processMsgTaskRoute, ...processMsgTaskPopupRoute];

@NgModule({
  imports: [PiSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProcessMsgTaskMpPiComponent,
    ProcessMsgTaskMpPiDetailComponent,
    ProcessMsgTaskMpPiUpdateComponent,
    ProcessMsgTaskMpPiDeleteDialogComponent,
    ProcessMsgTaskMpPiDeletePopupComponent
  ],
  entryComponents: [ProcessMsgTaskMpPiDeleteDialogComponent]
})
export class PiProcessMsgTaskMpPiModule {}
