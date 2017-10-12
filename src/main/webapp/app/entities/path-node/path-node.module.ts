import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SwarmServerSharedModule } from '../../shared';
import {
    PathNodeService,
    PathNodePopupService,
    PathNodeComponent,
    PathNodeDetailComponent,
    PathNodeDialogComponent,
    PathNodePopupComponent,
    PathNodeDeletePopupComponent,
    PathNodeDeleteDialogComponent,
    pathNodeRoute,
    pathNodePopupRoute,
} from './';

const ENTITY_STATES = [
    ...pathNodeRoute,
    ...pathNodePopupRoute,
];

@NgModule({
    imports: [
        SwarmServerSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PathNodeComponent,
        PathNodeDetailComponent,
        PathNodeDialogComponent,
        PathNodeDeleteDialogComponent,
        PathNodePopupComponent,
        PathNodeDeletePopupComponent,
    ],
    entryComponents: [
        PathNodeComponent,
        PathNodeDialogComponent,
        PathNodePopupComponent,
        PathNodeDeleteDialogComponent,
        PathNodeDeletePopupComponent,
    ],
    providers: [
        PathNodeService,
        PathNodePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SwarmServerPathNodeModule {}
