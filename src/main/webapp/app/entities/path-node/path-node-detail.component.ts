import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PathNode } from './path-node.model';
import { PathNodeService } from './path-node.service';

@Component({
    selector: 'jhi-path-node-detail',
    templateUrl: './path-node-detail.component.html'
})
export class PathNodeDetailComponent implements OnInit, OnDestroy {

    pathNode: PathNode;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private pathNodeService: PathNodeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPathNodes();
    }

    load(id) {
        this.pathNodeService.find(id).subscribe((pathNode) => {
            this.pathNode = pathNode;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPathNodes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'pathNodeListModification',
            (response) => this.load(this.pathNode.id)
        );
    }
}
