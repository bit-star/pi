import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PiTestModule } from '../../../test.module';
import { ProcessInstanceMpPiUpdateComponent } from 'app/entities/process-instance-mp-pi/process-instance-mp-pi-update.component';
import { ProcessInstanceMpPiService } from 'app/entities/process-instance-mp-pi/process-instance-mp-pi.service';
import { ProcessInstanceMpPi } from 'app/shared/model/process-instance-mp-pi.model';

describe('Component Tests', () => {
  describe('ProcessInstanceMpPi Management Update Component', () => {
    let comp: ProcessInstanceMpPiUpdateComponent;
    let fixture: ComponentFixture<ProcessInstanceMpPiUpdateComponent>;
    let service: ProcessInstanceMpPiService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PiTestModule],
        declarations: [ProcessInstanceMpPiUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProcessInstanceMpPiUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProcessInstanceMpPiUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProcessInstanceMpPiService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProcessInstanceMpPi(123);
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
        const entity = new ProcessInstanceMpPi();
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
