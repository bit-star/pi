import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'process-template-mp-pi',
        loadChildren: () => import('./process-template-mp-pi/process-template-mp-pi.module').then(m => m.PiProcessTemplateMpPiModule)
      },
      {
        path: 'process-instance-event-mp-pi',
        loadChildren: () =>
          import('./process-instance-event-mp-pi/process-instance-event-mp-pi.module').then(m => m.PiProcessInstanceEventMpPiModule)
      },
      {
        path: 'process-instance-mp-pi',
        loadChildren: () => import('./process-instance-mp-pi/process-instance-mp-pi.module').then(m => m.PiProcessInstanceMpPiModule)
      },
      {
        path: 'form-component-values-mp-pi',
        loadChildren: () =>
          import('./form-component-values-mp-pi/form-component-values-mp-pi.module').then(m => m.PiFormComponentValuesMpPiModule)
      },
      {
        path: 'dd-message-mp-pi',
        loadChildren: () => import('./dd-message-mp-pi/dd-message-mp-pi.module').then(m => m.PiDdMessageMpPiModule)
      },
      {
        path: 'process-msg-task-mp-pi',
        loadChildren: () => import('./process-msg-task-mp-pi/process-msg-task-mp-pi.module').then(m => m.PiProcessMsgTaskMpPiModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class PiEntityModule {}
