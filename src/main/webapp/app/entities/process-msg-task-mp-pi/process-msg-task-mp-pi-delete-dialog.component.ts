import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProcessMsgTaskMpPi } from 'app/shared/model/process-msg-task-mp-pi.model';
import { ProcessMsgTaskMpPiService } from './process-msg-task-mp-pi.service';

@Component({
  selector: 'jhi-process-msg-task-mp-pi-delete-dialog',
  templateUrl: './process-msg-task-mp-pi-delete-dialog.component.html'
})
export class ProcessMsgTaskMpPiDeleteDialogComponent {
  processMsgTask: IProcessMsgTaskMpPi;

  constructor(
    protected processMsgTaskService: ProcessMsgTaskMpPiService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.processMsgTaskService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'processMsgTaskListModification',
        content: 'Deleted an processMsgTask'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-process-msg-task-mp-pi-delete-popup',
  template: ''
})
export class ProcessMsgTaskMpPiDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ processMsgTask }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProcessMsgTaskMpPiDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.processMsgTask = processMsgTask;
        this.ngbModalRef.result.then(
          () => {
            this.router.navigate(['/process-msg-task-mp-pi', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          () => {
            this.router.navigate(['/process-msg-task-mp-pi', { outlets: { popup: null } }]);
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
