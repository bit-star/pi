import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProcessInstanceEventMpPi } from 'app/shared/model/process-instance-event-mp-pi.model';

@Component({
  selector: 'jhi-process-instance-event-mp-pi-detail',
  templateUrl: './process-instance-event-mp-pi-detail.component.html'
})
export class ProcessInstanceEventMpPiDetailComponent implements OnInit {
  processInstanceEvent: IProcessInstanceEventMpPi;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ processInstanceEvent }) => {
      this.processInstanceEvent = processInstanceEvent;
    });
  }

  previousState() {
    window.history.back();
  }
}
