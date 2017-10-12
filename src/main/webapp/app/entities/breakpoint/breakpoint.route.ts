import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { BreakpointComponent } from './breakpoint.component';
import { BreakpointDetailComponent } from './breakpoint-detail.component';
import { BreakpointPopupComponent } from './breakpoint-dialog.component';
import { BreakpointDeletePopupComponent } from './breakpoint-delete-dialog.component';

export const breakpointRoute: Routes = [
    {
        path: 'breakpoint',
        component: BreakpointComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'swarmServerApp.breakpoint.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'breakpoint/:id',
        component: BreakpointDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'swarmServerApp.breakpoint.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const breakpointPopupRoute: Routes = [
    {
        path: 'breakpoint-new',
        component: BreakpointPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'swarmServerApp.breakpoint.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'breakpoint/:id/edit',
        component: BreakpointPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'swarmServerApp.breakpoint.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'breakpoint/:id/delete',
        component: BreakpointDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'swarmServerApp.breakpoint.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
