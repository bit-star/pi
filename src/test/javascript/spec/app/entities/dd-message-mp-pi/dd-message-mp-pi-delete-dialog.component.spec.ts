import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PiTestModule } from '../../../test.module';
import { DdMessageMpPiDeleteDialogComponent } from 'app/entities/dd-message-mp-pi/dd-message-mp-pi-delete-dialog.component';
import { DdMessageMpPiService } from 'app/entities/dd-message-mp-pi/dd-message-mp-pi.service';

describe('Component Tests', () => {
  describe('DdMessageMpPi Management Delete Component', () => {
    let comp: DdMessageMpPiDeleteDialogComponent;
    let fixture: ComponentFixture<DdMessageMpPiDeleteDialogComponent>;
    let service: DdMessageMpPiService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PiTestModule],
        declarations: [DdMessageMpPiDeleteDialogComponent]
      })
        .overrideTemplate(DdMessageMpPiDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DdMessageMpPiDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DdMessageMpPiService);
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
