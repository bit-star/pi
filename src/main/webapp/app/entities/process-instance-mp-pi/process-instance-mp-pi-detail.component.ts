import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProcessInstanceMpPi } from 'app/shared/model/process-instance-mp-pi.model';

@Component({
  selector: 'jhi-process-instance-mp-pi-detail',
  templateUrl: './process-instance-mp-pi-detail.component.html'
})
export class ProcessInstanceMpPiDetailComponent implements OnInit {
  processInstance: IProcessInstanceMpPi;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ processInstance }) => {
      this.processInstance = processInstance;
    });
  }

  previousState() {
    window.history.back();
  }
}
