import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PiTestModule } from '../../../test.module';
import { ProcessTemplateMpPiDetailComponent } from 'app/entities/process-template-mp-pi/process-template-mp-pi-detail.component';
import { ProcessTemplateMpPi } from 'app/shared/model/process-template-mp-pi.model';

describe('Component Tests', () => {
  describe('ProcessTemplateMpPi Management Detail Component', () => {
    let comp: ProcessTemplateMpPiDetailComponent;
    let fixture: ComponentFixture<ProcessTemplateMpPiDetailComponent>;
    const route = ({ data: of({ processTemplate: new ProcessTemplateMpPi(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PiTestModule],
        declarations: [ProcessTemplateMpPiDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProcessTemplateMpPiDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProcessTemplateMpPiDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.processTemplate).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
