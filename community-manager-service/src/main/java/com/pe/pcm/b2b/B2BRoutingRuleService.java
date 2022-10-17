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

package com.pe.pcm.b2b;

import com.pe.pcm.b2b.routing.rules.RemoteRoutingRules;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.sterling.RemoteMailBoxService;
import com.pe.pcm.utils.CommonFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import static com.pe.pcm.utils.CommonFunctions.isNotNull;
import static java.lang.Boolean.FALSE;

/**
 * @author Chenchu Kiran.
 */
@Service
public class B2BRoutingRuleService {

    private final B2BApiService b2BApiService;
    private final RemoteMailBoxService remoteMailBoxService;
    private String inboundCoreBpName;

    @Autowired
    public B2BRoutingRuleService(B2BApiService b2BApiService, RemoteMailBoxService remoteMailBoxService,
                                 @Value("${sterling-b2bi.core-bp.inbound}") String inboundCoreBpName) {
        this.b2BApiService = b2BApiService;
        this.remoteMailBoxService = remoteMailBoxService;
        this.inboundCoreBpName = inboundCoreBpName;
    }

    public void save(String profileName, String mailbox, String oldName) {
        if (mailbox.startsWith("/")) {
            b2BApiService.createMailBoxInSI(true, mailbox, null);
            RemoteRoutingRules remoteRoutingRules = B2BFunctions.mapperToRemoteRoutingRules.apply(profileName, remoteMailBoxService.getIdByMailBoxName(mailbox)).setAction(inboundCoreBpName);
            try {
                if (isNotNull(oldName)) {
                    b2BApiService.getRoutingRules(oldName);
                    b2BApiService.updateRoutingRuleInSI(remoteRoutingRules, null, FALSE, oldName);
                } else {
                    throw GlobalExceptionHandler.internalServerError("Go to catch");
                }

            } catch (CommunityManagerServiceException cme) {
                if (cme.getErrorMessage().contains("Duplicate RoutingRule")) {
                    return;
                }
                b2BApiService.createRoutingRuleInSI(remoteRoutingRules);
            }
        }
    }

    @PostConstruct
    public void getCoreBpName() {
        if (!CommonFunctions.isNotNull(inboundCoreBpName)) {
            inboundCoreBpName = "Pragma_coreprocess";
        }
    }

}
