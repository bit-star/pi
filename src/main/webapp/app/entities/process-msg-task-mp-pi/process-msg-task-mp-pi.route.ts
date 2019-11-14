import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ProcessMsgTaskMpPi } from 'app/shared/model/process-msg-task-mp-pi.model';
import { ProcessMsgTaskMpPiService } from './process-msg-task-mp-pi.service';
import { ProcessMsgTaskMpPiComponent } from './process-msg-task-mp-pi.component';
import { ProcessMsgTaskMpPiDetailComponent } from './process-msg-task-mp-pi-detail.component';
import { ProcessMsgTaskMpPiUpdateComponent } from './process-msg-task-mp-pi-update.component';
import { ProcessMsgTaskMpPiDeletePopupComponent } from './process-msg-task-mp-pi-delete-dialog.component';
import { IProcessMsgTaskMpPi } from 'app/shared/model/process-msg-task-mp-pi.model';

@Injectable({ providedIn: 'root' })
export class ProcessMsgTaskMpPiResolve implements Resolve<IProcessMsgTaskMpPi> {
  constructor(private service: ProcessMsgTaskMpPiService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProcessMsgTaskMpPi> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((processMsgTask: HttpResponse<ProcessMsgTaskMpPi>) => processMsgTask.body));
    }
    return of(new ProcessMsgTaskMpPi());
  }
}

export const processMsgTaskRoute: Routes = [
  {
    path: '',
    component: ProcessMsgTaskMpPiComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'piApp.processMsgTask.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProcessMsgTaskMpPiDetailComponent,
    resolve: {
      processMsgTask: ProcessMsgTaskMpPiResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'piApp.processMsgTask.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProcessMsgTaskMpPiUpdateComponent,
    resolve: {
      processMsgTask: ProcessMsgTaskMpPiResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'piApp.processMsgTask.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProcessMsgTaskMpPiUpdateComponent,
    resolve: {
      processMsgTask: ProcessMsgTaskMpPiResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'piApp.processMsgTask.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const processMsgTaskPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProcessMsgTaskMpPiDeletePopupComponent,
    resolve: {
      processMsgTask: ProcessMsgTaskMpPiResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'piApp.processMsgTask.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
