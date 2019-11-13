import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProcessTemplateMpPi } from 'app/shared/model/process-template-mp-pi.model';

type EntityResponseType = HttpResponse<IProcessTemplateMpPi>;
type EntityArrayResponseType = HttpResponse<IProcessTemplateMpPi[]>;

@Injectable({ providedIn: 'root' })
export class ProcessTemplateMpPiService {
  public resourceUrl = SERVER_API_URL + 'api/process-templates';

  constructor(protected http: HttpClient) {}

  create(processTemplate: IProcessTemplateMpPi): Observable<EntityResponseType> {
    return this.http.post<IProcessTemplateMpPi>(this.resourceUrl, processTemplate, { observe: 'response' });
  }

  update(processTemplate: IProcessTemplateMpPi): Observable<EntityResponseType> {
    return this.http.put<IProcessTemplateMpPi>(this.resourceUrl, processTemplate, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProcessTemplateMpPi>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProcessTemplateMpPi[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
