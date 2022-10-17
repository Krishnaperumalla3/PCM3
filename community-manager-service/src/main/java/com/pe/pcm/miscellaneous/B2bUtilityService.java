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

package com.pe.pcm.miscellaneous;

import com.pe.pcm.b2b.B2BUtilityServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Chenchu Kiran.
 */
@Service
public class B2bUtilityService {

    private final B2BUtilityServices b2BUtilityServices;

    @Autowired
    public B2bUtilityService(B2BUtilityServices b2BUtilityServices) {
        this.b2BUtilityServices = b2BUtilityServices;
    }

    public void updateRoutingRule(String interval, String mailbox) {
        if (interval.equalsIgnoreCase("ON")) {
            b2BUtilityServices.updateRoutingRule("On_Arrival", mailbox);
        }
    }

    public void updateRoutingRule(String oldInterval, String newInterval, String mailbox) {
        if (!oldInterval.equals(newInterval) && newInterval.equalsIgnoreCase("ON")) {
            b2BUtilityServices.updateRoutingRule("On_Arrival", mailbox);
        }
    }

}
