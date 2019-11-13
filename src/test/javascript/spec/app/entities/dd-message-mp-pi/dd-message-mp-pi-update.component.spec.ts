import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PiTestModule } from '../../../test.module';
import { DdMessageMpPiUpdateComponent } from 'app/entities/dd-message-mp-pi/dd-message-mp-pi-update.component';
import { DdMessageMpPiService } from 'app/entities/dd-message-mp-pi/dd-message-mp-pi.service';
import { DdMessageMpPi } from 'app/shared/model/dd-message-mp-pi.model';

describe('Component Tests', () => {
  describe('DdMessageMpPi Management Update Component', () => {
    let comp: DdMessageMpPiUpdateComponent;
    let fixture: ComponentFixture<DdMessageMpPiUpdateComponent>;
    let service: DdMessageMpPiService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PiTestModule],
        declarations: [DdMessageMpPiUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DdMessageMpPiUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DdMessageMpPiUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DdMessageMpPiService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DdMessageMpPi(123);
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
        const entity = new DdMessageMpPi();
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
