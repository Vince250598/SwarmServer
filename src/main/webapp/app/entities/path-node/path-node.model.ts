import { BaseEntity } from './../../shared';

export class PathNode implements BaseEntity {
    constructor(
        public id?: number,
        public namespace?: string,
        public type?: string,
        public method?: string,
        public created?: any,
        public origin?: BaseEntity,
        public destination?: BaseEntity,
        public session?: BaseEntity,
    ) {
    }
}
