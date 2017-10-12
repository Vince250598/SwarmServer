import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PathNode } from './path-node.model';
import { PathNodePopupService } from './path-node-popup.service';
import { PathNodeService } from './path-node.service';
import { Session, SessionService } from '../session';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-path-node-dialog',
    templateUrl: './path-node-dialog.component.html'
})
export class PathNodeDialogComponent implements OnInit {

    pathNode: PathNode;
    isSaving: boolean;

    origins: PathNode[];

    pathnodes: PathNode[];

    sessions: Session[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private pathNodeService: PathNodeService,
        private sessionService: SessionService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.pathNodeService
            .query({filter: 'destination-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.pathNode.origin || !this.pathNode.origin.id) {
                    this.origins = res.json;
                } else {
                    this.pathNodeService
                        .find(this.pathNode.origin.id)
                        .subscribe((subRes: PathNode) => {
                            this.origins = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.pathNodeService.query()
            .subscribe((res: ResponseWrapper) => { this.pathnodes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.sessionService.query()
            .subscribe((res: ResponseWrapper) => { this.sessions = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.pathNode.id !== undefined) {
            this.subscribeToSaveResponse(
                this.pathNodeService.update(this.pathNode));
        } else {
            this.subscribeToSaveResponse(
                this.pathNodeService.create(this.pathNode));
        }
    }

    private subscribeToSaveResponse(result: Observable<PathNode>) {
        result.subscribe((res: PathNode) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: PathNode) {
        this.eventManager.broadcast({ name: 'pathNodeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackPathNodeById(index: number, item: PathNode) {
        return item.id;
    }

    trackSessionById(index: number, item: Session) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-path-node-popup',
    template: ''
})
export class PathNodePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pathNodePopupService: PathNodePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.pathNodePopupService
                    .open(PathNodeDialogComponent as Component, params['id']);
            } else {
                this.pathNodePopupService
                    .open(PathNodeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
