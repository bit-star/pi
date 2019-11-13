import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDdMessageMpPi } from 'app/shared/model/dd-message-mp-pi.model';

type EntityResponseType = HttpResponse<IDdMessageMpPi>;
type EntityArrayResponseType = HttpResponse<IDdMessageMpPi[]>;

@Injectable({ providedIn: 'root' })
export class DdMessageMpPiService {
  public resourceUrl = SERVER_API_URL + 'api/dd-messages';

  constructor(protected http: HttpClient) {}

  create(ddMessage: IDdMessageMpPi): Observable<EntityResponseType> {
    return this.http.post<IDdMessageMpPi>(this.resourceUrl, ddMessage, { observe: 'response' });
  }

  update(ddMessage: IDdMessageMpPi): Observable<EntityResponseType> {
    return this.http.put<IDdMessageMpPi>(this.resourceUrl, ddMessage, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDdMessageMpPi>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDdMessageMpPi[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
