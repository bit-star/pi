import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFormComponentValuesMpPi } from 'app/shared/model/form-component-values-mp-pi.model';
import { FormComponentValuesMpPiService } from './form-component-values-mp-pi.service';

@Component({
  selector: 'jhi-form-component-values-mp-pi-delete-dialog',
  templateUrl: './form-component-values-mp-pi-delete-dialog.component.html'
})
export class FormComponentValuesMpPiDeleteDialogComponent {
  formComponentValues: IFormComponentValuesMpPi;

  constructor(
    protected formComponentValuesService: FormComponentValuesMpPiService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.formComponentValuesService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'formComponentValuesListModification',
        content: 'Deleted an formComponentValues'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-form-component-values-mp-pi-delete-popup',
  template: ''
})
export class FormComponentValuesMpPiDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ formComponentValues }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(FormComponentValuesMpPiDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.formComponentValues = formComponentValues;
        this.ngbModalRef.result.then(
          () => {
            this.router.navigate(['/form-component-values-mp-pi', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          () => {
            this.router.navigate(['/form-component-values-mp-pi', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
