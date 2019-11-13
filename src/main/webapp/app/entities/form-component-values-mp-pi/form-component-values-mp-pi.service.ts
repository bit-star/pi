import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFormComponentValuesMpPi } from 'app/shared/model/form-component-values-mp-pi.model';

type EntityResponseType = HttpResponse<IFormComponentValuesMpPi>;
type EntityArrayResponseType = HttpResponse<IFormComponentValuesMpPi[]>;

@Injectable({ providedIn: 'root' })
export class FormComponentValuesMpPiService {
  public resourceUrl = SERVER_API_URL + 'api/form-component-values';

  constructor(protected http: HttpClient) {}

  create(formComponentValues: IFormComponentValuesMpPi): Observable<EntityResponseType> {
    return this.http.post<IFormComponentValuesMpPi>(this.resourceUrl, formComponentValues, { observe: 'response' });
  }

  update(formComponentValues: IFormComponentValuesMpPi): Observable<EntityResponseType> {
    return this.http.put<IFormComponentValuesMpPi>(this.resourceUrl, formComponentValues, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFormComponentValuesMpPi>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFormComponentValuesMpPi[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
