import { BaseEntity } from './../../shared';

export class Task implements BaseEntity {
    constructor(
        public id?: number,
        public tag?: string,
        public title?: string,
        public description?: string,
        public url?: string,
        public sessions?: BaseEntity[],
        public project?: BaseEntity,
    ) {
    }
}
