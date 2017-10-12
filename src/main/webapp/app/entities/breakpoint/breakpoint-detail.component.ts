import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Breakpoint } from './breakpoint.model';
import { BreakpointService } from './breakpoint.service';

@Component({
    selector: 'jhi-breakpoint-detail',
    templateUrl: './breakpoint-detail.component.html'
})
export class BreakpointDetailComponent implements OnInit, OnDestroy {

    breakpoint: Breakpoint;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private breakpointService: BreakpointService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBreakpoints();
    }

    load(id) {
        this.breakpointService.find(id).subscribe((breakpoint) => {
            this.breakpoint = breakpoint;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBreakpoints() {
        this.eventSubscriber = this.eventManager.subscribe(
            'breakpointListModification',
            (response) => this.load(this.breakpoint.id)
        );
    }
}
