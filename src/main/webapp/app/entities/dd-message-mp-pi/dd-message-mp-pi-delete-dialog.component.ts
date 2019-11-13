import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDdMessageMpPi } from 'app/shared/model/dd-message-mp-pi.model';
import { DdMessageMpPiService } from './dd-message-mp-pi.service';

@Component({
  selector: 'jhi-dd-message-mp-pi-delete-dialog',
  templateUrl: './dd-message-mp-pi-delete-dialog.component.html'
})
export class DdMessageMpPiDeleteDialogComponent {
  ddMessage: IDdMessageMpPi;

  constructor(
    protected ddMessageService: DdMessageMpPiService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.ddMessageService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'ddMessageListModification',
        content: 'Deleted an ddMessage'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-dd-message-mp-pi-delete-popup',
  template: ''
})
export class DdMessageMpPiDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ ddMessage }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DdMessageMpPiDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.ddMessage = ddMessage;
        this.ngbModalRef.result.then(
          () => {
            this.router.navigate(['/dd-message-mp-pi', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          () => {
            this.router.navigate(['/dd-message-mp-pi', { outlets: { popup: null } }]);
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
