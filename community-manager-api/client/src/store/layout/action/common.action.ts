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

export class Protocol {
  static readonly type = '[Common] PROTOCOL_LST';
  constructor(public payload: any) {}
}

export class Rules {
  static readonly type = '[Common] RULES_LST';
  constructor(public payload: any) {}
}

export class Partner {
  static readonly type = '[Common] PARTNER_LST';
  constructor(public payload: any) {}
}

export class Application {
  static readonly type = '[Common] APPLICATION_LST';
  constructor(public payload: any) {}
}
