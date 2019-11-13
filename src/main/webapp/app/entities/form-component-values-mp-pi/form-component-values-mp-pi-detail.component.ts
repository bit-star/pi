import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFormComponentValuesMpPi } from 'app/shared/model/form-component-values-mp-pi.model';

@Component({
  selector: 'jhi-form-component-values-mp-pi-detail',
  templateUrl: './form-component-values-mp-pi-detail.component.html'
})
export class FormComponentValuesMpPiDetailComponent implements OnInit {
  formComponentValues: IFormComponentValuesMpPi;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ formComponentValues }) => {
      this.formComponentValues = formComponentValues;
    });
  }

  previousState() {
    window.history.back();
  }
}
