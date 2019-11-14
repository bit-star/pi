import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PiTestModule } from '../../../test.module';
import { ProcessMsgTaskMpPiDeleteDialogComponent } from 'app/entities/process-msg-task-mp-pi/process-msg-task-mp-pi-delete-dialog.component';
import { ProcessMsgTaskMpPiService } from 'app/entities/process-msg-task-mp-pi/process-msg-task-mp-pi.service';

describe('Component Tests', () => {
  describe('ProcessMsgTaskMpPi Management Delete Component', () => {
    let comp: ProcessMsgTaskMpPiDeleteDialogComponent;
    let fixture: ComponentFixture<ProcessMsgTaskMpPiDeleteDialogComponent>;
    let service: ProcessMsgTaskMpPiService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PiTestModule],
        declarations: [ProcessMsgTaskMpPiDeleteDialogComponent]
      })
        .overrideTemplate(ProcessMsgTaskMpPiDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProcessMsgTaskMpPiDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProcessMsgTaskMpPiService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
