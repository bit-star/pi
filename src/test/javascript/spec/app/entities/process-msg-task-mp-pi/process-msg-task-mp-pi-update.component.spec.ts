import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PiTestModule } from '../../../test.module';
import { ProcessMsgTaskMpPiUpdateComponent } from 'app/entities/process-msg-task-mp-pi/process-msg-task-mp-pi-update.component';
import { ProcessMsgTaskMpPiService } from 'app/entities/process-msg-task-mp-pi/process-msg-task-mp-pi.service';
import { ProcessMsgTaskMpPi } from 'app/shared/model/process-msg-task-mp-pi.model';

describe('Component Tests', () => {
  describe('ProcessMsgTaskMpPi Management Update Component', () => {
    let comp: ProcessMsgTaskMpPiUpdateComponent;
    let fixture: ComponentFixture<ProcessMsgTaskMpPiUpdateComponent>;
    let service: ProcessMsgTaskMpPiService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PiTestModule],
        declarations: [ProcessMsgTaskMpPiUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProcessMsgTaskMpPiUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProcessMsgTaskMpPiUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProcessMsgTaskMpPiService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProcessMsgTaskMpPi(123);
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
        const entity = new ProcessMsgTaskMpPi();
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
