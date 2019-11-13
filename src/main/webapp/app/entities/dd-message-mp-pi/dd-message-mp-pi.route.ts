import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { DdMessageMpPi } from 'app/shared/model/dd-message-mp-pi.model';
import { DdMessageMpPiService } from './dd-message-mp-pi.service';
import { DdMessageMpPiComponent } from './dd-message-mp-pi.component';
import { DdMessageMpPiDetailComponent } from './dd-message-mp-pi-detail.component';
import { DdMessageMpPiUpdateComponent } from './dd-message-mp-pi-update.component';
import { DdMessageMpPiDeletePopupComponent } from './dd-message-mp-pi-delete-dialog.component';
import { IDdMessageMpPi } from 'app/shared/model/dd-message-mp-pi.model';

@Injectable({ providedIn: 'root' })
export class DdMessageMpPiResolve implements Resolve<IDdMessageMpPi> {
  constructor(private service: DdMessageMpPiService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDdMessageMpPi> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((ddMessage: HttpResponse<DdMessageMpPi>) => ddMessage.body));
    }
    return of(new DdMessageMpPi());
  }
}

export const ddMessageRoute: Routes = [
  {
    path: '',
    component: DdMessageMpPiComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'piApp.ddMessage.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DdMessageMpPiDetailComponent,
    resolve: {
      ddMessage: DdMessageMpPiResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'piApp.ddMessage.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DdMessageMpPiUpdateComponent,
    resolve: {
      ddMessage: DdMessageMpPiResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'piApp.ddMessage.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DdMessageMpPiUpdateComponent,
    resolve: {
      ddMessage: DdMessageMpPiResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'piApp.ddMessage.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const ddMessagePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DdMessageMpPiDeletePopupComponent,
    resolve: {
      ddMessage: DdMessageMpPiResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'piApp.ddMessage.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
