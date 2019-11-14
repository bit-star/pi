import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProcessMsgTaskMpPi } from 'app/shared/model/process-msg-task-mp-pi.model';

type EntityResponseType = HttpResponse<IProcessMsgTaskMpPi>;
type EntityArrayResponseType = HttpResponse<IProcessMsgTaskMpPi[]>;

@Injectable({ providedIn: 'root' })
export class ProcessMsgTaskMpPiService {
  public resourceUrl = SERVER_API_URL + 'api/process-msg-tasks';

  constructor(protected http: HttpClient) {}

  create(processMsgTask: IProcessMsgTaskMpPi): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(processMsgTask);
    return this.http
      .post<IProcessMsgTaskMpPi>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(processMsgTask: IProcessMsgTaskMpPi): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(processMsgTask);
    return this.http
      .put<IProcessMsgTaskMpPi>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IProcessMsgTaskMpPi>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IProcessMsgTaskMpPi[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(processMsgTask: IProcessMsgTaskMpPi): IProcessMsgTaskMpPi {
    const copy: IProcessMsgTaskMpPi = Object.assign({}, processMsgTask, {
      executeTime: processMsgTask.executeTime != null && processMsgTask.executeTime.isValid() ? processMsgTask.executeTime.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.executeTime = res.body.executeTime != null ? moment(res.body.executeTime) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((processMsgTask: IProcessMsgTaskMpPi) => {
        processMsgTask.executeTime = processMsgTask.executeTime != null ? moment(processMsgTask.executeTime) : null;
      });
    }
    return res;
  }
}
