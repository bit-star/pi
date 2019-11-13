import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PiTestModule } from '../../../test.module';
import { FormComponentValuesMpPiDetailComponent } from 'app/entities/form-component-values-mp-pi/form-component-values-mp-pi-detail.component';
import { FormComponentValuesMpPi } from 'app/shared/model/form-component-values-mp-pi.model';

describe('Component Tests', () => {
  describe('FormComponentValuesMpPi Management Detail Component', () => {
    let comp: FormComponentValuesMpPiDetailComponent;
    let fixture: ComponentFixture<FormComponentValuesMpPiDetailComponent>;
    const route = ({ data: of({ formComponentValues: new FormComponentValuesMpPi(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PiTestModule],
        declarations: [FormComponentValuesMpPiDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FormComponentValuesMpPiDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FormComponentValuesMpPiDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.formComponentValues).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
