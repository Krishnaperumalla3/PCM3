/*
 * Copyright (c) 2020 Pragma Edge Inc
 *
 * Licensed under the Pragma Edge Inc
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://pragmaedge.com/licenseagreement
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {DataFlowService} from '../services/data-flow.service';
import {forkJoin} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ReportsService {

  constructor(private http: HttpClient, private dataFlowService: DataFlowService) {

  }

  getRuleList() {
    return this.http.get(environment.REPORTS.GET_RULE_NAMES);
  }

  getRuleProperties(req) {
    return this.http.post(environment.REPORTS.GET_RULE_PROPERTIES, req);
  }


  getCargeBackProperties(req) {
    return this.http.post(environment.REPORTS.SEARCH_CHARGE_BACKS, req);
  }


  getSfgGraphData(req) {
    return this.http.post(environment.REPORTS.SFGGRAPHAPI, req);
  }

  getProducerData(req) {
    return this.http.post(environment.REPORTS.PRODUCERDATA, req);
  }

  getConsumerData(req) {
    return this.http.post(environment.REPORTS.CONSUMERDATA, req);
  }

  getUIDData(req) {
    return this.http.post(environment.REPORTS.UIDDATA, req);
  }

  getTotalCountsData(req) {
    return this.http.post(environment.REPORTS.TOTALCOUNTSALL, req);
  }

  getBarData(req) {
    return this.http.post(environment.REPORTS.BARCHART, req);
  }

  getLineChartData(req) {
    return this.http.post(environment.REPORTS.LINECHART, req);
  }

  getExtIntData(req) {
    return this.http.post(environment.REPORTS.INT_EXTCOUNTS, req);
  }

  getTopChargeback(req) {
    return this.http.post(environment.REPORTS.TOPCHARGEBACK, req);
  }

  getTopFileSize(req) {
    return this.http.post(environment.REPORTS.TOPFILESIZE, req);
  }

  getAPP(req) {
    return this.http.post(environment.REPORTS.APP, req);
  }

  getBU(req) {
    return this.http.post(environment.REPORTS.BU, req);
  }

  getMonthlyData(req) {
    return this.http.post(environment.REPORTS.MONTHLYAPI, req);
  }

  getQuarterlyData(req) {
    return this.http.post(environment.REPORTS.QUARTERLYAPI, req);
  }

  getPNODEdata(req){
    return this.http.post(environment.REPORTS.PNODE, req);
  }

  getSNODEdata(req){
    return this.http.post(environment.REPORTS.SNODE, req);
  }

  getSrcDesc(req){
    return this.http.post(environment.REPORTS.SRCDESTFILEDATA, req);
  }

  getProConsu(req){
    return this.http.post(environment.REPORTS.TOTALPRODUCERCONSUMERCOUNT, req);
  }


  exportDatFlow() {
    const URL = environment.REPORTS.EXPORT_ALL;
    return this.dataFlowService.exportWorkFlow(URL);
  }

  searchDataFlow(req, qryParams?) {
    let URL = environment.REPORTS.DATA_FLOW;
    if (qryParams) {
      URL = `${environment.REPORTS.DATA_FLOW}?size=${qryParams.size}&page=${
        qryParams.page
        }&sort=${qryParams.sortBy},${qryParams.sortDir.toUpperCase()}`;
    }
    return this.http.post(URL, req);
  }

  searchOverDue(req, qryParams?) {
    let URL = environment.REPORTS.OVER_DUE;
    if (qryParams) {
      URL = `${environment.REPORTS.OVER_DUE}?size=${qryParams.size}&page=${
        qryParams.page
        }&sort=${qryParams.sortBy},${qryParams.sortDir.toUpperCase()}`;
    }
    return this.http.post(URL, req);
  }

  getTransactionVolume({start, end}) {
    return this.http.get(environment.REPORTS.TRANSACTION_VOLUME({start, end}));
  }

  getDoctypeVolume({start, end}) {
    return this.http.get(environment.REPORTS.DOCTYPE_VOLUME({start, end}));
  }

  getPartnerVolume({start, end}) {
    return this.http.get(environment.REPORTS.PARTNER_VOLUME({start, end}));
  }

  getLast36monthsFiles(req) {
    return this.http.post(environment.REPORTS.LAST_36MONTHS_FILES, req);
  }

  getLast31daysFiles(req) {
    return this.http.post(environment.REPORTS.LAST_31DAYS_FILES, req);
  }

  getLast12MonthsFiles(req) {
    return this.http.post(environment.REPORTS.LAST_12MONTHS_FILES, req);
  }

  getLast30daysFiles(req) {
    return this.http.post(environment.REPORTS.LAST_30DAYS_FILES, req);
  }

  getLast4WeeksFiles(req) {
    return this.http.post(environment.REPORTS.LAST_4WEEKS_FILES, req);
  }

  inactivePartners(req) {
    const url = `${environment.REPORTS.INACTIVE_PARTNERS}?days=${req.days}`
    return this.http.get(url);
  }

  getThisHour(req) {
    return this.http.post(environment.REPORTS.THIS_HOUR, req);
  }
  getThisDay(req) {
    return this.http.post(environment.REPORTS.THIS_DAY, req);
  }
  getThisWeek(req) {
    return this.http.post(environment.REPORTS.THIS_WEEK, req);
  }
  getThisMonth(req) {
    return this.http.post(environment.REPORTS.THIS_MONTH, req);
  }

  getDashboard(req) {
    const hour = this.getThisHour(req);
    const day = this.getThisDay(req);
    const week = this.getThisWeek(req);
    const month = this.getThisMonth(req);

    return forkJoin([hour, day, week, month]);
  }

  getLast30daysFileSize(req) {
    return this.http.post(environment.REPORTS.LAST_30DAYS_FILESIZE, req);
  }

  getLast12MonthsFileSize(req) {
    return this.http.post(environment.REPORTS.LAST_12MONTHS_FILESIZE, req);
  }

  getLast36MonthsFileSize(req) {
    return this.http.post(environment.REPORTS.LAST_36MONTHS_FILESIZE, req);
  }

  getFileUploadReport() {
    return this.http.get(environment.FILE_REPORTS + 'upload');
  }

  getFileDownloadReport() {
    return this.http.get(environment.FILE_REPORTS + 'download');
  }
}
