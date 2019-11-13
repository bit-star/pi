import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProcessTemplateMpPi } from 'app/shared/model/process-template-mp-pi.model';

@Component({
  selector: 'jhi-process-template-mp-pi-detail',
  templateUrl: './process-template-mp-pi-detail.component.html'
})
export class ProcessTemplateMpPiDetailComponent implements OnInit {
  processTemplate: IProcessTemplateMpPi;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ processTemplate }) => {
      this.processTemplate = processTemplate;
    });
  }

  previousState() {
    window.history.back();
  }
}
