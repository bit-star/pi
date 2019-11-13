import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProcessTemplateMpPi } from 'app/shared/model/process-template-mp-pi.model';
import { ProcessTemplateMpPiService } from './process-template-mp-pi.service';

@Component({
  selector: 'jhi-process-template-mp-pi-delete-dialog',
  templateUrl: './process-template-mp-pi-delete-dialog.component.html'
})
export class ProcessTemplateMpPiDeleteDialogComponent {
  processTemplate: IProcessTemplateMpPi;

  constructor(
    protected processTemplateService: ProcessTemplateMpPiService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.processTemplateService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'processTemplateListModification',
        content: 'Deleted an processTemplate'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-process-template-mp-pi-delete-popup',
  template: ''
})
export class ProcessTemplateMpPiDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ processTemplate }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProcessTemplateMpPiDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.processTemplate = processTemplate;
        this.ngbModalRef.result.then(
          () => {
            this.router.navigate(['/process-template-mp-pi', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          () => {
            this.router.navigate(['/process-template-mp-pi', { outlets: { popup: null } }]);
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
