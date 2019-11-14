import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PiTestModule } from '../../../test.module';
import { ProcessMsgTaskMpPiDetailComponent } from 'app/entities/process-msg-task-mp-pi/process-msg-task-mp-pi-detail.component';
import { ProcessMsgTaskMpPi } from 'app/shared/model/process-msg-task-mp-pi.model';

describe('Component Tests', () => {
  describe('ProcessMsgTaskMpPi Management Detail Component', () => {
    let comp: ProcessMsgTaskMpPiDetailComponent;
    let fixture: ComponentFixture<ProcessMsgTaskMpPiDetailComponent>;
    const route = ({ data: of({ processMsgTask: new ProcessMsgTaskMpPi(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PiTestModule],
        declarations: [ProcessMsgTaskMpPiDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProcessMsgTaskMpPiDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProcessMsgTaskMpPiDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.processMsgTask).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
