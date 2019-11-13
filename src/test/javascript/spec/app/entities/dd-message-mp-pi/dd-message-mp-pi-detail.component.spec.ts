import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PiTestModule } from '../../../test.module';
import { DdMessageMpPiDetailComponent } from 'app/entities/dd-message-mp-pi/dd-message-mp-pi-detail.component';
import { DdMessageMpPi } from 'app/shared/model/dd-message-mp-pi.model';

describe('Component Tests', () => {
  describe('DdMessageMpPi Management Detail Component', () => {
    let comp: DdMessageMpPiDetailComponent;
    let fixture: ComponentFixture<DdMessageMpPiDetailComponent>;
    const route = ({ data: of({ ddMessage: new DdMessageMpPi(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PiTestModule],
        declarations: [DdMessageMpPiDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DdMessageMpPiDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DdMessageMpPiDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.ddMessage).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
