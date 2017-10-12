/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { SwarmServerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { BreakpointDetailComponent } from '../../../../../../main/webapp/app/entities/breakpoint/breakpoint-detail.component';
import { BreakpointService } from '../../../../../../main/webapp/app/entities/breakpoint/breakpoint.service';
import { Breakpoint } from '../../../../../../main/webapp/app/entities/breakpoint/breakpoint.model';

describe('Component Tests', () => {

    describe('Breakpoint Management Detail Component', () => {
        let comp: BreakpointDetailComponent;
        let fixture: ComponentFixture<BreakpointDetailComponent>;
        let service: BreakpointService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SwarmServerTestModule],
                declarations: [BreakpointDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    BreakpointService,
                    JhiEventManager
                ]
            }).overrideTemplate(BreakpointDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BreakpointDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BreakpointService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Breakpoint(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.breakpoint).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
