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

package com.pe.pcm.pem.ws;

import java.io.Serializable;

/**
 * @author Chenchu Kiran.
 */
public class RollOutParticipants implements Serializable {

    private String participantKey;
    private String type; //PARTNER, SPONSOR

    public String getParticipantKey() {
        return participantKey;
    }

    public RollOutParticipants setParticipantKey(String participantKey) {
        this.participantKey = participantKey;
        return this;
    }

    public String getType() {
        return type;
    }

    public RollOutParticipants setType(String type) {
        this.type = type;
        return this;
    }
}
