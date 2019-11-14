import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IDdMessageMpPi, DdMessageMpPi } from 'app/shared/model/dd-message-mp-pi.model';
import { DdMessageMpPiService } from './dd-message-mp-pi.service';
import { IProcessInstanceMpPi } from 'app/shared/model/process-instance-mp-pi.model';
import { ProcessInstanceMpPiService } from 'app/entities/process-instance-mp-pi/process-instance-mp-pi.service';

@Component({
  selector: 'jhi-dd-message-mp-pi-update',
  templateUrl: './dd-message-mp-pi-update.component.html'
})
export class DdMessageMpPiUpdateComponent implements OnInit {
  isSaving: boolean;

  processinstances: IProcessInstanceMpPi[];

  editForm = this.fb.group({
    id: [],
    receivingDepartment: [],
    receivingUser: [],
    title: [],
    json: [],
    sendTime: [],
    type: [],
    processInstance: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected ddMessageService: DdMessageMpPiService,
    protected processInstanceService: ProcessInstanceMpPiService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ ddMessage }) => {
      this.updateForm(ddMessage);
    });
    this.processInstanceService
      .query()
      .subscribe(
        (res: HttpResponse<IProcessInstanceMpPi[]>) => (this.processinstances = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(ddMessage: IDdMessageMpPi) {
    this.editForm.patchValue({
      id: ddMessage.id,
      receivingDepartment: ddMessage.receivingDepartment,
      receivingUser: ddMessage.receivingUser,
      title: ddMessage.title,
      json: ddMessage.json,
      sendTime: ddMessage.sendTime != null ? ddMessage.sendTime.format(DATE_TIME_FORMAT) : null,
      type: ddMessage.type,
      processInstance: ddMessage.processInstance
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const ddMessage = this.createFromForm();
    if (ddMessage.id !== undefined) {
      this.subscribeToSaveResponse(this.ddMessageService.update(ddMessage));
    } else {
      this.subscribeToSaveResponse(this.ddMessageService.create(ddMessage));
    }
  }

  private createFromForm(): IDdMessageMpPi {
    return {
      ...new DdMessageMpPi(),
      id: this.editForm.get(['id']).value,
      receivingDepartment: this.editForm.get(['receivingDepartment']).value,
      receivingUser: this.editForm.get(['receivingUser']).value,
      title: this.editForm.get(['title']).value,
      json: this.editForm.get(['json']).value,
      sendTime: this.editForm.get(['sendTime']).value != null ? moment(this.editForm.get(['sendTime']).value, DATE_TIME_FORMAT) : undefined,
      type: this.editForm.get(['type']).value,
      processInstance: this.editForm.get(['processInstance']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDdMessageMpPi>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackProcessInstanceById(index: number, item: IProcessInstanceMpPi) {
    return item.id;
  }
}
