import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { PathNode } from './path-node.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PathNodeService {

    private resourceUrl = SERVER_API_URL + 'api/path-nodes';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/path-nodes';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(pathNode: PathNode): Observable<PathNode> {
        const copy = this.convert(pathNode);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(pathNode: PathNode): Observable<PathNode> {
        const copy = this.convert(pathNode);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<PathNode> {
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
     * Convert a returned JSON object to PathNode.
     */
    private convertItemFromServer(json: any): PathNode {
        const entity: PathNode = Object.assign(new PathNode(), json);
        entity.created = this.dateUtils
            .convertDateTimeFromServer(json.created);
        return entity;
    }

    /**
     * Convert a PathNode to a JSON which can be sent to the server.
     */
    private convert(pathNode: PathNode): PathNode {
        const copy: PathNode = Object.assign({}, pathNode);

        copy.created = this.dateUtils.toDate(pathNode.created);
        return copy;
    }
}
