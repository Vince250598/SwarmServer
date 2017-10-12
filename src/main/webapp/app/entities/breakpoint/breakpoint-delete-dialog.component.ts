import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Breakpoint } from './breakpoint.model';
import { BreakpointPopupService } from './breakpoint-popup.service';
import { BreakpointService } from './breakpoint.service';

@Component({
    selector: 'jhi-breakpoint-delete-dialog',
    templateUrl: './breakpoint-delete-dialog.component.html'
})
export class BreakpointDeleteDialogComponent {

    breakpoint: Breakpoint;

    constructor(
        private breakpointService: BreakpointService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.breakpointService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'breakpointListModification',
                content: 'Deleted an breakpoint'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-breakpoint-delete-popup',
    template: ''
})
export class BreakpointDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private breakpointPopupService: BreakpointPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.breakpointPopupService
                .open(BreakpointDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
