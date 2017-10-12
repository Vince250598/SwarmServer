import { BaseEntity } from './../../shared';

export class Developer implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public sessions?: BaseEntity[],
    ) {
    }
}
