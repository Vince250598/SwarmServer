import { BaseEntity } from './../../shared';

export const enum BreakpointKind {
    'LINE',
    'CONDITIONAL',
    'EXCEPTION'
}

export class Breakpoint implements BaseEntity {
    constructor(
        public id?: number,
        public kind?: BreakpointKind,
        public namespace?: string,
        public type?: string,
        public lineNumber?: number,
        public lineOfCode?: string,
        public created?: any,
        public session?: BaseEntity,
    ) {
    }
}
