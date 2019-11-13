import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PiTestModule } from '../../../test.module';
import { ProcessInstanceEventMpPiDeleteDialogComponent } from 'app/entities/process-instance-event-mp-pi/process-instance-event-mp-pi-delete-dialog.component';
import { ProcessInstanceEventMpPiService } from 'app/entities/process-instance-event-mp-pi/process-instance-event-mp-pi.service';

describe('Component Tests', () => {
  describe('ProcessInstanceEventMpPi Management Delete Component', () => {
    let comp: ProcessInstanceEventMpPiDeleteDialogComponent;
    let fixture: ComponentFixture<ProcessInstanceEventMpPiDeleteDialogComponent>;
    let service: ProcessInstanceEventMpPiService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PiTestModule],
        declarations: [ProcessInstanceEventMpPiDeleteDialogComponent]
      })
        .overrideTemplate(ProcessInstanceEventMpPiDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProcessInstanceEventMpPiDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProcessInstanceEventMpPiService);
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
