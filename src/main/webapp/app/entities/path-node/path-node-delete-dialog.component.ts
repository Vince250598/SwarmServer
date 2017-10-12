import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PathNode } from './path-node.model';
import { PathNodePopupService } from './path-node-popup.service';
import { PathNodeService } from './path-node.service';

@Component({
    selector: 'jhi-path-node-delete-dialog',
    templateUrl: './path-node-delete-dialog.component.html'
})
export class PathNodeDeleteDialogComponent {

    pathNode: PathNode;

    constructor(
        private pathNodeService: PathNodeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pathNodeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'pathNodeListModification',
                content: 'Deleted an pathNode'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-path-node-delete-popup',
    template: ''
})
export class PathNodeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pathNodePopupService: PathNodePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.pathNodePopupService
                .open(PathNodeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
