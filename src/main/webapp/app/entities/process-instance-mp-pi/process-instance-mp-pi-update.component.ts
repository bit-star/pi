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
import { IProcessInstanceMpPi, ProcessInstanceMpPi } from 'app/shared/model/process-instance-mp-pi.model';
import { ProcessInstanceMpPiService } from './process-instance-mp-pi.service';
import { IProcessTemplateMpPi } from 'app/shared/model/process-template-mp-pi.model';
import { ProcessTemplateMpPiService } from 'app/entities/process-template-mp-pi/process-template-mp-pi.service';

@Component({
  selector: 'jhi-process-instance-mp-pi-update',
  templateUrl: './process-instance-mp-pi-update.component.html'
})
export class ProcessInstanceMpPiUpdateComponent implements OnInit {
  isSaving: boolean;

  processtemplates: IProcessTemplateMpPi[];

  editForm = this.fb.group({
    id: [],
    attachedProcessInstanceIds: [],
    bizAction: [],
    businessId: [],
    createTime: [],
    finishTime: [],
    formComponentValues: [],
    operationRecords: [],
    originatorDeptId: [],
    originatorDeptName: [],
    originatorUserid: [],
    result: [],
    status: [],
    tasks: [],
    title: [],
    processTemplate: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected processInstanceService: ProcessInstanceMpPiService,
    protected processTemplateService: ProcessTemplateMpPiService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ processInstance }) => {
      this.updateForm(processInstance);
    });
    this.processTemplateService
      .query()
      .subscribe(
        (res: HttpResponse<IProcessTemplateMpPi[]>) => (this.processtemplates = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(processInstance: IProcessInstanceMpPi) {
    this.editForm.patchValue({
      id: processInstance.id,
      attachedProcessInstanceIds: processInstance.attachedProcessInstanceIds,
      bizAction: processInstance.bizAction,
      businessId: processInstance.businessId,
      createTime: processInstance.createTime != null ? processInstance.createTime.format(DATE_TIME_FORMAT) : null,
      finishTime: processInstance.finishTime != null ? processInstance.finishTime.format(DATE_TIME_FORMAT) : null,
      formComponentValues: processInstance.formComponentValues,
      operationRecords: processInstance.operationRecords,
      originatorDeptId: processInstance.originatorDeptId,
      originatorDeptName: processInstance.originatorDeptName,
      originatorUserid: processInstance.originatorUserid,
      result: processInstance.result,
      status: processInstance.status,
      tasks: processInstance.tasks,
      title: processInstance.title,
      processTemplate: processInstance.processTemplate
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const processInstance = this.createFromForm();
    if (processInstance.id !== undefined) {
      this.subscribeToSaveResponse(this.processInstanceService.update(processInstance));
    } else {
      this.subscribeToSaveResponse(this.processInstanceService.create(processInstance));
    }
  }

  private createFromForm(): IProcessInstanceMpPi {
    return {
      ...new ProcessInstanceMpPi(),
      id: this.editForm.get(['id']).value,
      attachedProcessInstanceIds: this.editForm.get(['attachedProcessInstanceIds']).value,
      bizAction: this.editForm.get(['bizAction']).value,
      businessId: this.editForm.get(['businessId']).value,
      createTime:
        this.editForm.get(['createTime']).value != null ? moment(this.editForm.get(['createTime']).value, DATE_TIME_FORMAT) : undefined,
      finishTime:
        this.editForm.get(['finishTime']).value != null ? moment(this.editForm.get(['finishTime']).value, DATE_TIME_FORMAT) : undefined,
      formComponentValues: this.editForm.get(['formComponentValues']).value,
      operationRecords: this.editForm.get(['operationRecords']).value,
      originatorDeptId: this.editForm.get(['originatorDeptId']).value,
      originatorDeptName: this.editForm.get(['originatorDeptName']).value,
      originatorUserid: this.editForm.get(['originatorUserid']).value,
      result: this.editForm.get(['result']).value,
      status: this.editForm.get(['status']).value,
      tasks: this.editForm.get(['tasks']).value,
      title: this.editForm.get(['title']).value,
      processTemplate: this.editForm.get(['processTemplate']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProcessInstanceMpPi>>) {
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

  trackProcessTemplateById(index: number, item: IProcessTemplateMpPi) {
    return item.id;
  }
}
