import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ProcessMsgTaskMpPiService } from 'app/entities/process-msg-task-mp-pi/process-msg-task-mp-pi.service';
import { IProcessMsgTaskMpPi, ProcessMsgTaskMpPi } from 'app/shared/model/process-msg-task-mp-pi.model';
import { MessageStatus } from 'app/shared/model/enumerations/message-status.model';

describe('Service Tests', () => {
  describe('ProcessMsgTaskMpPi Service', () => {
    let injector: TestBed;
    let service: ProcessMsgTaskMpPiService;
    let httpMock: HttpTestingController;
    let elemDefault: IProcessMsgTaskMpPi;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(ProcessMsgTaskMpPiService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new ProcessMsgTaskMpPi(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate, MessageStatus.SentSuccessfully);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            executeTime: currentDate.format(DATE_TIME_FORMAT)
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

      it('should create a ProcessMsgTaskMpPi', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            executeTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            executeTime: currentDate
          },
          returnedFromService
        );
        service
          .create(new ProcessMsgTaskMpPi(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a ProcessMsgTaskMpPi', () => {
        const returnedFromService = Object.assign(
          {
            receivingDepartment: 'BBBBBB',
            receivingUser: 'BBBBBB',
            title: 'BBBBBB',
            json: 'BBBBBB',
            executeTime: currentDate.format(DATE_TIME_FORMAT),
            status: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            executeTime: currentDate
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

      it('should return a list of ProcessMsgTaskMpPi', () => {
        const returnedFromService = Object.assign(
          {
            receivingDepartment: 'BBBBBB',
            receivingUser: 'BBBBBB',
            title: 'BBBBBB',
            json: 'BBBBBB',
            executeTime: currentDate.format(DATE_TIME_FORMAT),
            status: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            executeTime: currentDate
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

      it('should delete a ProcessMsgTaskMpPi', () => {
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
