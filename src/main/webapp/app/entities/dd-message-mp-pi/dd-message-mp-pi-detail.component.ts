import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDdMessageMpPi } from 'app/shared/model/dd-message-mp-pi.model';

@Component({
  selector: 'jhi-dd-message-mp-pi-detail',
  templateUrl: './dd-message-mp-pi-detail.component.html'
})
export class DdMessageMpPiDetailComponent implements OnInit {
  ddMessage: IDdMessageMpPi;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ ddMessage }) => {
      this.ddMessage = ddMessage;
    });
  }

  previousState() {
    window.history.back();
  }
}
