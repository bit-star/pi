import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PiTestModule } from '../../../test.module';
import { ProcessInstanceMpPiDeleteDialogComponent } from 'app/entities/process-instance-mp-pi/process-instance-mp-pi-delete-dialog.component';
import { ProcessInstanceMpPiService } from 'app/entities/process-instance-mp-pi/process-instance-mp-pi.service';

describe('Component Tests', () => {
  describe('ProcessInstanceMpPi Management Delete Component', () => {
    let comp: ProcessInstanceMpPiDeleteDialogComponent;
    let fixture: ComponentFixture<ProcessInstanceMpPiDeleteDialogComponent>;
    let service: ProcessInstanceMpPiService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PiTestModule],
        declarations: [ProcessInstanceMpPiDeleteDialogComponent]
      })
        .overrideTemplate(ProcessInstanceMpPiDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProcessInstanceMpPiDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProcessInstanceMpPiService);
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
