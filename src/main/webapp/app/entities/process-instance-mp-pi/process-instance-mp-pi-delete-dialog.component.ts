import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProcessInstanceMpPi } from 'app/shared/model/process-instance-mp-pi.model';
import { ProcessInstanceMpPiService } from './process-instance-mp-pi.service';

@Component({
  selector: 'jhi-process-instance-mp-pi-delete-dialog',
  templateUrl: './process-instance-mp-pi-delete-dialog.component.html'
})
export class ProcessInstanceMpPiDeleteDialogComponent {
  processInstance: IProcessInstanceMpPi;

  constructor(
    protected processInstanceService: ProcessInstanceMpPiService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.processInstanceService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'processInstanceListModification',
        content: 'Deleted an processInstance'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-process-instance-mp-pi-delete-popup',
  template: ''
})
export class ProcessInstanceMpPiDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ processInstance }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProcessInstanceMpPiDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.processInstance = processInstance;
        this.ngbModalRef.result.then(
          () => {
            this.router.navigate(['/process-instance-mp-pi', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          () => {
            this.router.navigate(['/process-instance-mp-pi', { outlets: { popup: null } }]);
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
