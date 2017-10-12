import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SwarmServerSharedModule } from '../../shared';
import {
    BreakpointService,
    BreakpointPopupService,
    BreakpointComponent,
    BreakpointDetailComponent,
    BreakpointDialogComponent,
    BreakpointPopupComponent,
    BreakpointDeletePopupComponent,
    BreakpointDeleteDialogComponent,
    breakpointRoute,
    breakpointPopupRoute,
} from './';

const ENTITY_STATES = [
    ...breakpointRoute,
    ...breakpointPopupRoute,
];

@NgModule({
    imports: [
        SwarmServerSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        BreakpointComponent,
        BreakpointDetailComponent,
        BreakpointDialogComponent,
        BreakpointDeleteDialogComponent,
        BreakpointPopupComponent,
        BreakpointDeletePopupComponent,
    ],
    entryComponents: [
        BreakpointComponent,
        BreakpointDialogComponent,
        BreakpointPopupComponent,
        BreakpointDeleteDialogComponent,
        BreakpointDeletePopupComponent,
    ],
    providers: [
        BreakpointService,
        BreakpointPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SwarmServerBreakpointModule {}
