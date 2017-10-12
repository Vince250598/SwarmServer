import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Breakpoint } from './breakpoint.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class BreakpointService {

    private resourceUrl = SERVER_API_URL + 'api/breakpoints';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/breakpoints';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(breakpoint: Breakpoint): Observable<Breakpoint> {
        const copy = this.convert(breakpoint);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(breakpoint: Breakpoint): Observable<Breakpoint> {
        const copy = this.convert(breakpoint);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Breakpoint> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to Breakpoint.
     */
    private convertItemFromServer(json: any): Breakpoint {
        const entity: Breakpoint = Object.assign(new Breakpoint(), json);
        entity.created = this.dateUtils
            .convertDateTimeFromServer(json.created);
        return entity;
    }

    /**
     * Convert a Breakpoint to a JSON which can be sent to the server.
     */
    private convert(breakpoint: Breakpoint): Breakpoint {
        const copy: Breakpoint = Object.assign({}, breakpoint);

        copy.created = this.dateUtils.toDate(breakpoint.created);
        return copy;
    }
}
