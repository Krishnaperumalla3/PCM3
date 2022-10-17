/*
 * Copyright (c) 2020 Pragma Edge Inc
 *
 * Licensed under the Pragma Edge Inc.
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

package com.pe.pcm.sterling.sfg.rct;

import com.pe.pcm.sterling.FgProvFactRepository;
import com.pe.pcm.sterling.mailbox.RoutingChannelTempModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Chenchu Kiran.
 */
@Service
public class FgRctProvisionAndGroupPermissionsService {

    private final FgProvFactRepository fgProvFactRepository;
    private final FgRctTmplGpErmRepository fgRctTmplGpErmRepository;

    @Autowired
    public FgRctProvisionAndGroupPermissionsService(FgProvFactRepository fgProvFactRepository, FgRctTmplGpErmRepository fgRctTmplGpErmRepository) {
        this.fgProvFactRepository = fgProvFactRepository;
        this.fgRctTmplGpErmRepository = fgRctTmplGpErmRepository;
    }

    public void save(RoutingChannelTempModel routingChannelTempModel, String routingChanTempKey, boolean isUpdate) {

    }

    public void delete(String routingChanTempKey){

    }
}
