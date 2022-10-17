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

import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VolumePartnersComponent } from './volume-partners.component';

describe('VolumePartnersComponent', () => {
  let component: VolumePartnersComponent;
  let fixture: ComponentFixture<VolumePartnersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VolumePartnersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VolumePartnersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
