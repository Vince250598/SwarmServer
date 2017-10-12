import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Developer } from './developer.model';
import { DeveloperPopupService } from './developer-popup.service';
import { DeveloperService } from './developer.service';

@Component({
    selector: 'jhi-developer-dialog',
    templateUrl: './developer-dialog.component.html'
})
export class DeveloperDialogComponent implements OnInit {

    developer: Developer;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private developerService: DeveloperService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.developer.id !== undefined) {
            this.subscribeToSaveResponse(
                this.developerService.update(this.developer));
        } else {
            this.subscribeToSaveResponse(
                this.developerService.create(this.developer));
        }
    }

    private subscribeToSaveResponse(result: Observable<Developer>) {
        result.subscribe((res: Developer) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Developer) {
        this.eventManager.broadcast({ name: 'developerListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-developer-popup',
    template: ''
})
export class DeveloperPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private developerPopupService: DeveloperPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.developerPopupService
                    .open(DeveloperDialogComponent as Component, params['id']);
            } else {
                this.developerPopupService
                    .open(DeveloperDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
