import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PathNodeComponent } from './path-node.component';
import { PathNodeDetailComponent } from './path-node-detail.component';
import { PathNodePopupComponent } from './path-node-dialog.component';
import { PathNodeDeletePopupComponent } from './path-node-delete-dialog.component';

export const pathNodeRoute: Routes = [
    {
        path: 'path-node',
        component: PathNodeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'swarmServerApp.pathNode.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'path-node/:id',
        component: PathNodeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'swarmServerApp.pathNode.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pathNodePopupRoute: Routes = [
    {
        path: 'path-node-new',
        component: PathNodePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'swarmServerApp.pathNode.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'path-node/:id/edit',
        component: PathNodePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'swarmServerApp.pathNode.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'path-node/:id/delete',
        component: PathNodeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'swarmServerApp.pathNode.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
