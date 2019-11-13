import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IProcessTemplateMpPi, ProcessTemplateMpPi } from 'app/shared/model/process-template-mp-pi.model';
import { ProcessTemplateMpPiService } from './process-template-mp-pi.service';

@Component({
  selector: 'jhi-process-template-mp-pi-update',
  templateUrl: './process-template-mp-pi-update.component.html'
})
export class ProcessTemplateMpPiUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [],
    processCode: []
  });

  constructor(
    protected processTemplateService: ProcessTemplateMpPiService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ processTemplate }) => {
      this.updateForm(processTemplate);
    });
  }

  updateForm(processTemplate: IProcessTemplateMpPi) {
    this.editForm.patchValue({
      id: processTemplate.id,
      name: processTemplate.name,
      processCode: processTemplate.processCode
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const processTemplate = this.createFromForm();
    if (processTemplate.id !== undefined) {
      this.subscribeToSaveResponse(this.processTemplateService.update(processTemplate));
    } else {
      this.subscribeToSaveResponse(this.processTemplateService.create(processTemplate));
    }
  }

  private createFromForm(): IProcessTemplateMpPi {
    return {
      ...new ProcessTemplateMpPi(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      processCode: this.editForm.get(['processCode']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProcessTemplateMpPi>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
