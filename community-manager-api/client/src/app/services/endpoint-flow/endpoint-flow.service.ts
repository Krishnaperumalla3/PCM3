import { Injectable } from '@angular/core';
import {environment} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class EndpointFlowService {

  constructor(private http: HttpClient) { }

  getEndpointFlow(url) {
    return this.http.get(url);
  }

  createFlow(req) {
    return this.http.post(environment.CREATE_ENDPOINT_FLOW, req);
  }

  deleteWorkLow(url) {
    return this.http.delete(url);
  }
}
