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

import { Layout } from '../model/layout.model';
import { State, Action, StateContext, Selector } from '@ngxs/store';
import { LoaderState, ModuleName } from '../action/layout.action';

export class LayoutStateModel {
  layout: Layout;
}

@State<LayoutStateModel>({
  name: 'pcmLayout',
  defaults: {
    layout: { moduleName: 'File Search', applicationName: 'PCM', loader: false }
  }
})
export class LayoutState {
  @Selector()
  static getModuleName(state: LayoutStateModel) {
    // console.log(state.layout.moduleName);
    return state.layout.moduleName;
  }

  @Selector()
  static getLoader(state: LayoutStateModel) {
    return state.layout.loader;
  }

  @Selector()
  static getApplicationName(state: LayoutStateModel) {
    return state.layout.applicationName;
  }

  @Action(ModuleName)
  setModuleName({
    getState,
    patchState,
    setState
  }: StateContext<LayoutStateModel>, {name}: ModuleName) {
    const state = getState();
    setState({
      ...state,
      layout: Object.assign({}, state.layout, { moduleName: name })
    });
  }

  @Action(LoaderState)
  toggleLoader({
    getState,
    patchState,
    setState
  }: StateContext<LayoutStateModel>) {
    const state = getState();
    setState({
      ...state,
      layout: Object.assign({}, state.layout, { loader: !state.layout.loader })
    });
  }
}
