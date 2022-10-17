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

import {Action, State, StateContext} from "@ngxs/store";
import {
  ClearPartnerPagination,
  ClearSearchPartner,
  PartnerListSuccess,
  SearchPartner,
  UpdatePartnerPagination,
  PartnerTotalElements,
  PartnerTotalPages
} from "./partner-actions";
import {IPagination, IPartnerSearch} from "./partner-models";

interface IPartnerState {
  currentlyLoading: boolean;
  totalPages: any;
  totalElements: any;
  partnersList: any[];
  searchFields?: IPartnerSearch;
  pagination: IPagination;
}

const initialPagination = {
  page: 0,
  sortBy: 'tpName',
  sortDir: 'asc',
  size: 10
};

export const initialState: IPartnerState = {
  currentlyLoading: false,
  partnersList: [],
  searchFields: {},
  pagination: {
    ...initialPagination
  },
  totalPages: 0,
  totalElements: 0
};

@State<IPartnerState>({
  name: 'partner',
  defaults: initialState
})
export class PartnerState {

  @Action(PartnerListSuccess)
  PartnerListSuccess(context: StateContext<IPartnerState>, action: PartnerListSuccess) {
    context.setState({
      ...context.getState(),
      partnersList: action.payload

    })
  }

  @Action(PartnerTotalElements)
  PartnerTotalElements(context: StateContext<IPartnerState>, action: PartnerTotalElements) {
    context.setState({
      ...context.getState(),
      totalElements: action.payload

    })
  }

  @Action(PartnerTotalPages)
  PartnerTotalPages(context: StateContext<IPartnerState>, action: PartnerTotalPages) {
    context.setState({
      ...context.getState(),
      totalPages: action.payload

    })
  }

  @Action(SearchPartner)
  searchPartner(context: StateContext<IPartnerState>, action: SearchPartner) {
    context.setState({
      ...context.getState(),
      searchFields: action.payload
    })
  }

  @Action(ClearSearchPartner)
  clearSearchPartner(context: StateContext<IPartnerState>) {
    context.setState({
      ...context.getState(),
      searchFields: {},
      partnersList: [],
      pagination: {...initialPagination}
    })
  }

  @Action(ClearPartnerPagination)
  clearPartnerPagination(context: StateContext<IPartnerState>) {
    context.setState({
      ...context.getState(),
      pagination: {...initialPagination}
    })
  }

  @Action(UpdatePartnerPagination)
  UpdatePartnerPagination(context: StateContext<IPartnerState>, action: UpdatePartnerPagination) {
    context.setState({
      ...context.getState(),
      pagination: {...(action.payload || initialPagination)}
    })
  }
}
