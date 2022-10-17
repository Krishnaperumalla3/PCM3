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

import {IPagination, IPartnerSearch} from "./partner-models";

export class SearchPartner {
  static readonly type = '[Partner] Search list';
  constructor(public payload: IPartnerSearch) {
  }
}

export class ClearSearchPartner {
  static readonly type = '[Partner] Clear Search Result';
}

export class ClearPartnerPagination {
  static readonly type = '[Partner] Clear Pagination';
}

export class UpdatePartnerPagination {
  static readonly type = '[Partner] Update Pagination';
  constructor(public payload: IPagination) {
  }
}

export class PartnerListSuccess {
  static readonly type = '[Partner] PartnerListSuccess';
  constructor(public payload: any) {
  }
}

export class PartnerTotalPages {
  static readonly type = '[Partner] PartnerTotalPages';
  constructor(public payload: any){}
}
export class PartnerTotalElements {
  static readonly type = '[Partner] PartnerTotalElements';
  constructor(public payload: any){}
}

