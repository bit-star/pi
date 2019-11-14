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
import { IProcessMsgTaskMpPi, ProcessMsgTaskMpPi } from 'app/shared/model/process-msg-task-mp-pi.model';
import { ProcessMsgTaskMpPiService } from './process-msg-task-mp-pi.service';
import { IProcessInstanceMpPi } from 'app/shared/model/process-instance-mp-pi.model';
import { ProcessInstanceMpPiService } from 'app/entities/process-instance-mp-pi/process-instance-mp-pi.service';

@Component({
  selector: 'jhi-process-msg-task-mp-pi-update',
  templateUrl: './process-msg-task-mp-pi-update.component.html'
})
export class ProcessMsgTaskMpPiUpdateComponent implements OnInit {
  isSaving: boolean;

  processinstances: IProcessInstanceMpPi[];

  editForm = this.fb.group({
    id: [],
    receivingDepartment: [],
    receivingUser: [],
    title: [],
    json: [],
    executeTime: [],
    status: [],
    processInstance: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected processMsgTaskService: ProcessMsgTaskMpPiService,
    protected processInstanceService: ProcessInstanceMpPiService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ processMsgTask }) => {
      this.updateForm(processMsgTask);
    });
    this.processInstanceService
      .query()
      .subscribe(
        (res: HttpResponse<IProcessInstanceMpPi[]>) => (this.processinstances = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(processMsgTask: IProcessMsgTaskMpPi) {
    this.editForm.patchValue({
      id: processMsgTask.id,
      receivingDepartment: processMsgTask.receivingDepartment,
      receivingUser: processMsgTask.receivingUser,
      title: processMsgTask.title,
      json: processMsgTask.json,
      executeTime: processMsgTask.executeTime != null ? processMsgTask.executeTime.format(DATE_TIME_FORMAT) : null,
      status: processMsgTask.status,
      processInstance: processMsgTask.processInstance
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const processMsgTask = this.createFromForm();
    if (processMsgTask.id !== undefined) {
      this.subscribeToSaveResponse(this.processMsgTaskService.update(processMsgTask));
    } else {
      this.subscribeToSaveResponse(this.processMsgTaskService.create(processMsgTask));
    }
  }

  private createFromForm(): IProcessMsgTaskMpPi {
    return {
      ...new ProcessMsgTaskMpPi(),
      id: this.editForm.get(['id']).value,
      receivingDepartment: this.editForm.get(['receivingDepartment']).value,
      receivingUser: this.editForm.get(['receivingUser']).value,
      title: this.editForm.get(['title']).value,
      json: this.editForm.get(['json']).value,
      executeTime:
        this.editForm.get(['executeTime']).value != null ? moment(this.editForm.get(['executeTime']).value, DATE_TIME_FORMAT) : undefined,
      status: this.editForm.get(['status']).value,
      processInstance: this.editForm.get(['processInstance']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProcessMsgTaskMpPi>>) {
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
