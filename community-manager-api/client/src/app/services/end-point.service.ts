import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class EndPointService {

  constructor(private http: HttpClient) {
  }

  getPoolingInterval() {
    return this.http.get(environment.GET_POOLING_INTERVAL);
  }

  createEndPoint(req, id) {
    if (id) {
      return this.http.put(environment.PROXY_ENDPOINT, req);
    } else {
      return this.http.post(environment.PROXY_ENDPOINT, req);
    }
  }

  searchEndPoint(req, qryParams) {
    let URL = environment.PROXY_ENDPOINT + '/search';

    if (qryParams) {
      URL = `${environment.PROXY_ENDPOINT + '/search'}?size=${qryParams.size}&page=${
        qryParams.page
      }&sort=${qryParams.sortBy},${(qryParams.sortDir || '').toUpperCase()}`;
    }

    return this.http.post(URL, req);
  }

  getEndPoint(id) {
    return this.http.get(environment.PROXY_ENDPOINT + '/' + id);
  }

  deleteEndPoint(id) {
    return this.http.get(environment.PROXY_ENDPOINT + '/' + id);
  }

  getEndpointsList() {
    return this.http.get(environment.GET_ENDPOINT_MAP);
  }

}
