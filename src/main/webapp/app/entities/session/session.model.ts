import { BaseEntity } from './../../shared';

export class Session implements BaseEntity {
    constructor(
        public id?: number,
        public label?: string,
        public description?: string,
        public purpose?: string,
        public started?: any,
        public finished?: any,
        public segments?: BaseEntity[],
        public breakpoints?: BaseEntity[],
        public events?: BaseEntity[],
        public task?: BaseEntity,
        public developer?: BaseEntity,
    ) {
    }
}
