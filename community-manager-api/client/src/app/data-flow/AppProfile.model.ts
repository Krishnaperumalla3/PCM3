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

export interface AppProfile {
    inboundFlow: InboundFlow;
    outboundFlow: OutboundFlow;
}
interface InboundFlow {
    mfts: Mfts;
    docHandlings: DocHandlings;
}
interface Mfts {
    processDocModels: ProcessDocModelsItem[];
}
interface ProcessDocModelsItem {
    fileNamePattern?: string;
    processRulesList: ProcessRulesListItem[];
    docType?: string;
    partnerId?: string;
    receiverId?: string;
    docTrans?: string;
}
interface ProcessRulesListItem {
    ruleName: string;
    propertyValue1?: string;
    propertyValue3?: string;
    propertyValue4?: string;
    propertyValue2?: string;
}
interface DocHandlings {
    processDocModels: ProcessDocModelsItem[];
}
interface OutboundFlow {
    mfts: Mfts;
    docHandlings: DocHandlings;
}


// tslint:disable-next-line:no-empty-interface
export interface Search {

}


