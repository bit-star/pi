import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ProcessTemplateMpPi } from 'app/shared/model/process-template-mp-pi.model';
import { ProcessTemplateMpPiService } from './process-template-mp-pi.service';
import { ProcessTemplateMpPiComponent } from './process-template-mp-pi.component';
import { ProcessTemplateMpPiDetailComponent } from './process-template-mp-pi-detail.component';
import { ProcessTemplateMpPiUpdateComponent } from './process-template-mp-pi-update.component';
import { ProcessTemplateMpPiDeletePopupComponent } from './process-template-mp-pi-delete-dialog.component';
import { IProcessTemplateMpPi } from 'app/shared/model/process-template-mp-pi.model';

@Injectable({ providedIn: 'root' })
export class ProcessTemplateMpPiResolve implements Resolve<IProcessTemplateMpPi> {
  constructor(private service: ProcessTemplateMpPiService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProcessTemplateMpPi> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((processTemplate: HttpResponse<ProcessTemplateMpPi>) => processTemplate.body));
    }
    return of(new ProcessTemplateMpPi());
  }
}

export const processTemplateRoute: Routes = [
  {
    path: '',
    component: ProcessTemplateMpPiComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'piApp.processTemplate.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProcessTemplateMpPiDetailComponent,
    resolve: {
      processTemplate: ProcessTemplateMpPiResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'piApp.processTemplate.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProcessTemplateMpPiUpdateComponent,
    resolve: {
      processTemplate: ProcessTemplateMpPiResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'piApp.processTemplate.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProcessTemplateMpPiUpdateComponent,
    resolve: {
      processTemplate: ProcessTemplateMpPiResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'piApp.processTemplate.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const processTemplatePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProcessTemplateMpPiDeletePopupComponent,
    resolve: {
      processTemplate: ProcessTemplateMpPiResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'piApp.processTemplate.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
