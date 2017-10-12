import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { SwarmServerProjectModule } from './project/project.module';
import { SwarmServerTaskModule } from './task/task.module';
import { SwarmServerSessionModule } from './session/session.module';
import { SwarmServerDeveloperModule } from './developer/developer.module';
import { SwarmServerBreakpointModule } from './breakpoint/breakpoint.module';
import { SwarmServerEventModule } from './event/event.module';
import { SwarmServerPathNodeModule } from './path-node/path-node.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        SwarmServerProjectModule,
        SwarmServerTaskModule,
        SwarmServerSessionModule,
        SwarmServerDeveloperModule,
        SwarmServerBreakpointModule,
        SwarmServerEventModule,
        SwarmServerPathNodeModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SwarmServerEntityModule {}
