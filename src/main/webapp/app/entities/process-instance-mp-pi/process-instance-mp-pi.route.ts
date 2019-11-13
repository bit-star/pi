import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ProcessInstanceMpPi } from 'app/shared/model/process-instance-mp-pi.model';
import { ProcessInstanceMpPiService } from './process-instance-mp-pi.service';
import { ProcessInstanceMpPiComponent } from './process-instance-mp-pi.component';
import { ProcessInstanceMpPiDetailComponent } from './process-instance-mp-pi-detail.component';
import { ProcessInstanceMpPiUpdateComponent } from './process-instance-mp-pi-update.component';
import { ProcessInstanceMpPiDeletePopupComponent } from './process-instance-mp-pi-delete-dialog.component';
import { IProcessInstanceMpPi } from 'app/shared/model/process-instance-mp-pi.model';

@Injectable({ providedIn: 'root' })
export class ProcessInstanceMpPiResolve implements Resolve<IProcessInstanceMpPi> {
  constructor(private service: ProcessInstanceMpPiService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProcessInstanceMpPi> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((processInstance: HttpResponse<ProcessInstanceMpPi>) => processInstance.body));
    }
    return of(new ProcessInstanceMpPi());
  }
}

export const processInstanceRoute: Routes = [
  {
    path: '',
    component: ProcessInstanceMpPiComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'piApp.processInstance.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProcessInstanceMpPiDetailComponent,
    resolve: {
      processInstance: ProcessInstanceMpPiResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'piApp.processInstance.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProcessInstanceMpPiUpdateComponent,
    resolve: {
      processInstance: ProcessInstanceMpPiResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'piApp.processInstance.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProcessInstanceMpPiUpdateComponent,
    resolve: {
      processInstance: ProcessInstanceMpPiResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'piApp.processInstance.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const processInstancePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProcessInstanceMpPiDeletePopupComponent,
    resolve: {
      processInstance: ProcessInstanceMpPiResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'piApp.processInstance.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
