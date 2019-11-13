import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PiTestModule } from '../../../test.module';
import { FormComponentValuesMpPiUpdateComponent } from 'app/entities/form-component-values-mp-pi/form-component-values-mp-pi-update.component';
import { FormComponentValuesMpPiService } from 'app/entities/form-component-values-mp-pi/form-component-values-mp-pi.service';
import { FormComponentValuesMpPi } from 'app/shared/model/form-component-values-mp-pi.model';

describe('Component Tests', () => {
  describe('FormComponentValuesMpPi Management Update Component', () => {
    let comp: FormComponentValuesMpPiUpdateComponent;
    let fixture: ComponentFixture<FormComponentValuesMpPiUpdateComponent>;
    let service: FormComponentValuesMpPiService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PiTestModule],
        declarations: [FormComponentValuesMpPiUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FormComponentValuesMpPiUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FormComponentValuesMpPiUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FormComponentValuesMpPiService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FormComponentValuesMpPi(123);
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
        const entity = new FormComponentValuesMpPi();
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
