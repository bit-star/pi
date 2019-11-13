import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PiTestModule } from '../../../test.module';
import { ProcessTemplateMpPiUpdateComponent } from 'app/entities/process-template-mp-pi/process-template-mp-pi-update.component';
import { ProcessTemplateMpPiService } from 'app/entities/process-template-mp-pi/process-template-mp-pi.service';
import { ProcessTemplateMpPi } from 'app/shared/model/process-template-mp-pi.model';

describe('Component Tests', () => {
  describe('ProcessTemplateMpPi Management Update Component', () => {
    let comp: ProcessTemplateMpPiUpdateComponent;
    let fixture: ComponentFixture<ProcessTemplateMpPiUpdateComponent>;
    let service: ProcessTemplateMpPiService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PiTestModule],
        declarations: [ProcessTemplateMpPiUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProcessTemplateMpPiUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProcessTemplateMpPiUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProcessTemplateMpPiService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProcessTemplateMpPi(123);
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
        const entity = new ProcessTemplateMpPi();
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
