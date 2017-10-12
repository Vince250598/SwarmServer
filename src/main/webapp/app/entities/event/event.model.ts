import { BaseEntity } from './../../shared';

export const enum EventKind {
    'STEP_OUT',
    'STEP_INTO',
    'STEP_OVER',
    'SUSPEND',
    'RESUME',
    'BREAKPOINT_ADD',
    'BREAKPOINT_CHANGE',
    'BREAKPOINT_REMOVE',
    'SUSPEND_BREAKPOINT',
    'INSPECT_VARIABLE',
    'MODIFY_VARIABLE',
    'DEFINE_WATCH',
    'EVALUATE_EXPRESSION'
}

export class Event implements BaseEntity {
    constructor(
        public id?: number,
        public kind?: EventKind,
        public detail?: string,
        public namespace?: string,
        public type?: string,
        public typeFullPath?: string,
        public method?: string,
        public methodKey?: string,
        public methodSignature?: string,
        public charStar?: number,
        public charEnd?: number,
        public lineNumber?: number,
        public lineOfCode?: string,
        public created?: any,
        public session?: BaseEntity,
    ) {
    }
}
