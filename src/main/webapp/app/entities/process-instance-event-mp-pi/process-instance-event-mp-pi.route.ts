import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ProcessInstanceEventMpPi } from 'app/shared/model/process-instance-event-mp-pi.model';
import { ProcessInstanceEventMpPiService } from './process-instance-event-mp-pi.service';
import { ProcessInstanceEventMpPiComponent } from './process-instance-event-mp-pi.component';
import { ProcessInstanceEventMpPiDetailComponent } from './process-instance-event-mp-pi-detail.component';
import { ProcessInstanceEventMpPiUpdateComponent } from './process-instance-event-mp-pi-update.component';
import { ProcessInstanceEventMpPiDeletePopupComponent } from './process-instance-event-mp-pi-delete-dialog.component';
import { IProcessInstanceEventMpPi } from 'app/shared/model/process-instance-event-mp-pi.model';

@Injectable({ providedIn: 'root' })
export class ProcessInstanceEventMpPiResolve implements Resolve<IProcessInstanceEventMpPi> {
  constructor(private service: ProcessInstanceEventMpPiService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProcessInstanceEventMpPi> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((processInstanceEvent: HttpResponse<ProcessInstanceEventMpPi>) => processInstanceEvent.body));
    }
    return of(new ProcessInstanceEventMpPi());
  }
}

export const processInstanceEventRoute: Routes = [
  {
    path: '',
    component: ProcessInstanceEventMpPiComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'piApp.processInstanceEvent.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProcessInstanceEventMpPiDetailComponent,
    resolve: {
      processInstanceEvent: ProcessInstanceEventMpPiResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'piApp.processInstanceEvent.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProcessInstanceEventMpPiUpdateComponent,
    resolve: {
      processInstanceEvent: ProcessInstanceEventMpPiResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'piApp.processInstanceEvent.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProcessInstanceEventMpPiUpdateComponent,
    resolve: {
      processInstanceEvent: ProcessInstanceEventMpPiResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'piApp.processInstanceEvent.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const processInstanceEventPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProcessInstanceEventMpPiDeletePopupComponent,
    resolve: {
      processInstanceEvent: ProcessInstanceEventMpPiResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'piApp.processInstanceEvent.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
