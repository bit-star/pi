import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PiTestModule } from '../../../test.module';
import { ProcessInstanceEventMpPiDetailComponent } from 'app/entities/process-instance-event-mp-pi/process-instance-event-mp-pi-detail.component';
import { ProcessInstanceEventMpPi } from 'app/shared/model/process-instance-event-mp-pi.model';

describe('Component Tests', () => {
  describe('ProcessInstanceEventMpPi Management Detail Component', () => {
    let comp: ProcessInstanceEventMpPiDetailComponent;
    let fixture: ComponentFixture<ProcessInstanceEventMpPiDetailComponent>;
    const route = ({ data: of({ processInstanceEvent: new ProcessInstanceEventMpPi(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PiTestModule],
        declarations: [ProcessInstanceEventMpPiDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProcessInstanceEventMpPiDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProcessInstanceEventMpPiDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.processInstanceEvent).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
