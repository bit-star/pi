import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProcessMsgTaskMpPi } from 'app/shared/model/process-msg-task-mp-pi.model';

@Component({
  selector: 'jhi-process-msg-task-mp-pi-detail',
  templateUrl: './process-msg-task-mp-pi-detail.component.html'
})
export class ProcessMsgTaskMpPiDetailComponent implements OnInit {
  processMsgTask: IProcessMsgTaskMpPi;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ processMsgTask }) => {
      this.processMsgTask = processMsgTask;
    });
  }

  previousState() {
    window.history.back();
  }
}
