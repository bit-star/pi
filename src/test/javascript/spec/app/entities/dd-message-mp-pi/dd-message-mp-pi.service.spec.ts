import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { DdMessageMpPiService } from 'app/entities/dd-message-mp-pi/dd-message-mp-pi.service';
import { IDdMessageMpPi, DdMessageMpPi } from 'app/shared/model/dd-message-mp-pi.model';
import { DdMessageType } from 'app/shared/model/enumerations/dd-message-type.model';

describe('Service Tests', () => {
  describe('DdMessageMpPi Service', () => {
    let injector: TestBed;
    let service: DdMessageMpPiService;
    let httpMock: HttpTestingController;
    let elemDefault: IDdMessageMpPi;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(DdMessageMpPiService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new DdMessageMpPi(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate, DdMessageType.ActionCard);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            sendTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
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
            id: 0,
            sendTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            sendTime: currentDate
          },
          returnedFromService
        );
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
            sendTime: currentDate.format(DATE_TIME_FORMAT),
            type: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            sendTime: currentDate
          },
          returnedFromService
        );
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
            sendTime: currentDate.format(DATE_TIME_FORMAT),
            type: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            sendTime: currentDate
          },
          returnedFromService
        );
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
