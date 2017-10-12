import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Breakpoint } from './breakpoint.model';
import { BreakpointPopupService } from './breakpoint-popup.service';
import { BreakpointService } from './breakpoint.service';
import { Session, SessionService } from '../session';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-breakpoint-dialog',
    templateUrl: './breakpoint-dialog.component.html'
})
export class BreakpointDialogComponent implements OnInit {

    breakpoint: Breakpoint;
    isSaving: boolean;

    sessions: Session[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private breakpointService: BreakpointService,
        private sessionService: SessionService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.sessionService.query()
            .subscribe((res: ResponseWrapper) => { this.sessions = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.breakpoint.id !== undefined) {
            this.subscribeToSaveResponse(
                this.breakpointService.update(this.breakpoint));
        } else {
            this.subscribeToSaveResponse(
                this.breakpointService.create(this.breakpoint));
        }
    }

    private subscribeToSaveResponse(result: Observable<Breakpoint>) {
        result.subscribe((res: Breakpoint) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Breakpoint) {
        this.eventManager.broadcast({ name: 'breakpointListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackSessionById(index: number, item: Session) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-breakpoint-popup',
    template: ''
})
export class BreakpointPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private breakpointPopupService: BreakpointPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.breakpointPopupService
                    .open(BreakpointDialogComponent as Component, params['id']);
            } else {
                this.breakpointPopupService
                    .open(BreakpointDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
