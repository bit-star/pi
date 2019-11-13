import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PiTestModule } from '../../../test.module';
import { ProcessInstanceEventMpPiUpdateComponent } from 'app/entities/process-instance-event-mp-pi/process-instance-event-mp-pi-update.component';
import { ProcessInstanceEventMpPiService } from 'app/entities/process-instance-event-mp-pi/process-instance-event-mp-pi.service';
import { ProcessInstanceEventMpPi } from 'app/shared/model/process-instance-event-mp-pi.model';

describe('Component Tests', () => {
  describe('ProcessInstanceEventMpPi Management Update Component', () => {
    let comp: ProcessInstanceEventMpPiUpdateComponent;
    let fixture: ComponentFixture<ProcessInstanceEventMpPiUpdateComponent>;
    let service: ProcessInstanceEventMpPiService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PiTestModule],
        declarations: [ProcessInstanceEventMpPiUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProcessInstanceEventMpPiUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProcessInstanceEventMpPiUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProcessInstanceEventMpPiService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProcessInstanceEventMpPi(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProcessInstanceEventMpPi();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
