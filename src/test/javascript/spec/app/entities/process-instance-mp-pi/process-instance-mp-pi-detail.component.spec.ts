import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PiTestModule } from '../../../test.module';
import { ProcessInstanceMpPiDetailComponent } from 'app/entities/process-instance-mp-pi/process-instance-mp-pi-detail.component';
import { ProcessInstanceMpPi } from 'app/shared/model/process-instance-mp-pi.model';

describe('Component Tests', () => {
  describe('ProcessInstanceMpPi Management Detail Component', () => {
    let comp: ProcessInstanceMpPiDetailComponent;
    let fixture: ComponentFixture<ProcessInstanceMpPiDetailComponent>;
    const route = ({ data: of({ processInstance: new ProcessInstanceMpPi(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PiTestModule],
        declarations: [ProcessInstanceMpPiDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProcessInstanceMpPiDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProcessInstanceMpPiDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.processInstance).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
