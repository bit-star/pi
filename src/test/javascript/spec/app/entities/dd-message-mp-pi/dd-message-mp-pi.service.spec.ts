import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { DdMessageMpPiService } from 'app/entities/dd-message-mp-pi/dd-message-mp-pi.service';
import { IDdMessageMpPi, DdMessageMpPi } from 'app/shared/model/dd-message-mp-pi.model';
import { DdMessageType } from 'app/shared/model/enumerations/dd-message-type.model';
import { MessageStatus } from 'app/shared/model/enumerations/message-status.model';

describe('Service Tests', () => {
  describe('DdMessageMpPi Service', () => {
    let injector: TestBed;
    let service: DdMessageMpPiService;
    let httpMock: HttpTestingController;
    let elemDefault: IDdMessageMpPi;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(DdMessageMpPiService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new DdMessageMpPi(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        DdMessageType.ActionCard,
        MessageStatus.SentSuccessfully
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a DdMessageMpPi', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new DdMessageMpPi(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a DdMessageMpPi', () => {
        const returnedFromService = Object.assign(
          {
            receivingDepartment: 'BBBBBB',
            receivingUser: 'BBBBBB',
            title: 'BBBBBB',
            json: 'BBBBBB',
            type: 'BBBBBB',
            status: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of DdMessageMpPi', () => {
        const returnedFromService = Object.assign(
          {
            receivingDepartment: 'BBBBBB',
            receivingUser: 'BBBBBB',
            title: 'BBBBBB',
            json: 'BBBBBB',
            type: 'BBBBBB',
            status: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a DdMessageMpPi', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
