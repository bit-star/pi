import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PiSharedModule } from 'app/shared/shared.module';
import { DdMessageMpPiComponent } from './dd-message-mp-pi.component';
import { DdMessageMpPiDetailComponent } from './dd-message-mp-pi-detail.component';
import { DdMessageMpPiUpdateComponent } from './dd-message-mp-pi-update.component';
import { DdMessageMpPiDeletePopupComponent, DdMessageMpPiDeleteDialogComponent } from './dd-message-mp-pi-delete-dialog.component';
import { ddMessageRoute, ddMessagePopupRoute } from './dd-message-mp-pi.route';

const ENTITY_STATES = [...ddMessageRoute, ...ddMessagePopupRoute];

@NgModule({
  imports: [PiSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DdMessageMpPiComponent,
    DdMessageMpPiDetailComponent,
    DdMessageMpPiUpdateComponent,
    DdMessageMpPiDeleteDialogComponent,
    DdMessageMpPiDeletePopupComponent
  ],
  entryComponents: [DdMessageMpPiDeleteDialogComponent]
})
export class PiDdMessageMpPiModule {}
