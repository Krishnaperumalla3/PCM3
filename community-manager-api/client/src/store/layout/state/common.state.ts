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

import {Application, Partner, Protocol, Rules} from '../action/common.action';
import {Common} from '../model/common.model';
import {Action, Selector, State, StateContext} from '@ngxs/store';

export class CommonStateModel {
  common: Common;
}

@State<CommonStateModel>({
  name: 'common',
  defaults: {
    common: {
      protocol: [],
      rules: [],
      partner: [],
      application: []
    }
  }
})

export class CommonState {
  @Selector()
  static getProtocols(state: CommonStateModel) {
    return state.common.protocol;
  }

  @Selector()
  static getRules(state: CommonStateModel) {
    return {...state.common.rules};
  }

  @Selector()
  static getPartner(state: CommonStateModel) {
    return {...state.common.partner};
  }

  @Selector()
  static getApplication(state: CommonStateModel) {
    return {...state.common.application};
  }

  @Action(Protocol)
  setProtocol({
                getState,
                patchState,
                setState
              }: StateContext<CommonStateModel>, {payload}: Protocol) {
    const state = getState();
    setState({
      ...state,
      common: Object.assign({}, state.common, {protocol: payload})
    });
  }

  @Action(Rules)
  setRules({
             getState,
             patchState,
             setState
           }: StateContext<CommonStateModel>, {payload}: Rules) {
    const state = getState();
    setState({
      ...state,
      common: Object.assign({}, state.common, {rules: payload})
    });
  }

  @Action(Partner)
  setPartner({
               getState,
               patchState,
               setState
             }: StateContext<CommonStateModel>, {payload}: Partner) {
    const state = getState();
    setState({
      ...state,
      common: Object.assign({}, state.common, {partner: payload})
    });
  }

  @Action(Application)
  setApplication({
                   getState,
                   patchState,
                   setState
                 }: StateContext<CommonStateModel>, {payload}: Application) {
    const state = getState();
    setState({
      ...state,
      common: Object.assign({}, state.common, {application: payload})
    });
  }
}
