import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { FormComponentValuesMpPi } from 'app/shared/model/form-component-values-mp-pi.model';
import { FormComponentValuesMpPiService } from './form-component-values-mp-pi.service';
import { FormComponentValuesMpPiComponent } from './form-component-values-mp-pi.component';
import { FormComponentValuesMpPiDetailComponent } from './form-component-values-mp-pi-detail.component';
import { FormComponentValuesMpPiUpdateComponent } from './form-component-values-mp-pi-update.component';
import { FormComponentValuesMpPiDeletePopupComponent } from './form-component-values-mp-pi-delete-dialog.component';
import { IFormComponentValuesMpPi } from 'app/shared/model/form-component-values-mp-pi.model';

@Injectable({ providedIn: 'root' })
export class FormComponentValuesMpPiResolve implements Resolve<IFormComponentValuesMpPi> {
  constructor(private service: FormComponentValuesMpPiService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFormComponentValuesMpPi> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((formComponentValues: HttpResponse<FormComponentValuesMpPi>) => formComponentValues.body));
    }
    return of(new FormComponentValuesMpPi());
  }
}

export const formComponentValuesRoute: Routes = [
  {
    path: '',
    component: FormComponentValuesMpPiComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'piApp.formComponentValues.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FormComponentValuesMpPiDetailComponent,
    resolve: {
      formComponentValues: FormComponentValuesMpPiResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'piApp.formComponentValues.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FormComponentValuesMpPiUpdateComponent,
    resolve: {
      formComponentValues: FormComponentValuesMpPiResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'piApp.formComponentValues.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FormComponentValuesMpPiUpdateComponent,
    resolve: {
      formComponentValues: FormComponentValuesMpPiResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'piApp.formComponentValues.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const formComponentValuesPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: FormComponentValuesMpPiDeletePopupComponent,
    resolve: {
      formComponentValues: FormComponentValuesMpPiResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'piApp.formComponentValues.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
