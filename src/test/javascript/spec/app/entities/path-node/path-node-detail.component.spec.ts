/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { SwarmServerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PathNodeDetailComponent } from '../../../../../../main/webapp/app/entities/path-node/path-node-detail.component';
import { PathNodeService } from '../../../../../../main/webapp/app/entities/path-node/path-node.service';
import { PathNode } from '../../../../../../main/webapp/app/entities/path-node/path-node.model';

describe('Component Tests', () => {

    describe('PathNode Management Detail Component', () => {
        let comp: PathNodeDetailComponent;
        let fixture: ComponentFixture<PathNodeDetailComponent>;
        let service: PathNodeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SwarmServerTestModule],
                declarations: [PathNodeDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PathNodeService,
                    JhiEventManager
                ]
            }).overrideTemplate(PathNodeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PathNodeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PathNodeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PathNode(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.pathNode).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
