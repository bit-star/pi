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
import { IProcessInstanceEventMpPi, ProcessInstanceEventMpPi } from 'app/shared/model/process-instance-event-mp-pi.model';
import { ProcessInstanceEventMpPiService } from './process-instance-event-mp-pi.service';
import { IProcessInstanceMpPi } from 'app/shared/model/process-instance-mp-pi.model';
import { ProcessInstanceMpPiService } from 'app/entities/process-instance-mp-pi/process-instance-mp-pi.service';

@Component({
  selector: 'jhi-process-instance-event-mp-pi-update',
  templateUrl: './process-instance-event-mp-pi-update.component.html'
})
export class ProcessInstanceEventMpPiUpdateComponent implements OnInit {
  isSaving: boolean;

  processinstances: IProcessInstanceMpPi[];

  editForm = this.fb.group({
    id: [],
    finishTime: [],
    corpId: [],
    eventType: [],
    businessId: [],
    title: [],
    type: [],
    url: [],
    result: [],
    createTime: [],
    processCode: [],
    bizCategoryId: [],
    staffId: [],
    processInstance: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected processInstanceEventService: ProcessInstanceEventMpPiService,
    protected processInstanceService: ProcessInstanceMpPiService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ processInstanceEvent }) => {
      this.updateForm(processInstanceEvent);
    });
    this.processInstanceService
      .query()
      .subscribe(
        (res: HttpResponse<IProcessInstanceMpPi[]>) => (this.processinstances = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(processInstanceEvent: IProcessInstanceEventMpPi) {
    this.editForm.patchValue({
      id: processInstanceEvent.id,
      finishTime: processInstanceEvent.finishTime != null ? processInstanceEvent.finishTime.format(DATE_TIME_FORMAT) : null,
      corpId: processInstanceEvent.corpId,
      eventType: processInstanceEvent.eventType,
      businessId: processInstanceEvent.businessId,
      title: processInstanceEvent.title,
      type: processInstanceEvent.type,
      url: processInstanceEvent.url,
      result: processInstanceEvent.result,
      createTime: processInstanceEvent.createTime,
      processCode: processInstanceEvent.processCode,
      bizCategoryId: processInstanceEvent.bizCategoryId,
      staffId: processInstanceEvent.staffId,
      processInstance: processInstanceEvent.processInstance
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const processInstanceEvent = this.createFromForm();
    if (processInstanceEvent.id !== undefined) {
      this.subscribeToSaveResponse(this.processInstanceEventService.update(processInstanceEvent));
    } else {
      this.subscribeToSaveResponse(this.processInstanceEventService.create(processInstanceEvent));
    }
  }

  private createFromForm(): IProcessInstanceEventMpPi {
    return {
      ...new ProcessInstanceEventMpPi(),
      id: this.editForm.get(['id']).value,
      finishTime:
        this.editForm.get(['finishTime']).value != null ? moment(this.editForm.get(['finishTime']).value, DATE_TIME_FORMAT) : undefined,
      corpId: this.editForm.get(['corpId']).value,
      eventType: this.editForm.get(['eventType']).value,
      businessId: this.editForm.get(['businessId']).value,
      title: this.editForm.get(['title']).value,
      type: this.editForm.get(['type']).value,
      url: this.editForm.get(['url']).value,
      result: this.editForm.get(['result']).value,
      createTime: this.editForm.get(['createTime']).value,
      processCode: this.editForm.get(['processCode']).value,
      bizCategoryId: this.editForm.get(['bizCategoryId']).value,
      staffId: this.editForm.get(['staffId']).value,
      processInstance: this.editForm.get(['processInstance']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProcessInstanceEventMpPi>>) {
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
