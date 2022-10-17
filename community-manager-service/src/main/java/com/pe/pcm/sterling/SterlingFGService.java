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

package com.pe.pcm.sterling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Kiran Reddy.
 */
@Service
public class SterlingFGService {

    private final FgProvFactRepository fgProvFactRepository;
    private final FgRoutChanRepository fgRoutChanRepository;

    @Autowired
    public SterlingFGService(FgProvFactRepository fgProvFactRepository, FgRoutChanRepository fgRoutChanRepository) {
        this.fgProvFactRepository = fgProvFactRepository;
        this.fgRoutChanRepository = fgRoutChanRepository;
    }

    public String getRCTbyProvFacts(String provFacts) {
        StringBuilder mailBoxNameBuilder = new StringBuilder();
        fgProvFactRepository.findFirstByFactValueOrderByCreatetsDesc(provFacts)
                .ifPresent(fgProvFactEntity -> fgRoutChanRepository.findById(fgProvFactEntity.getRoutchanKey())
                        .ifPresent(fgRouteChanEntity ->
                                mailBoxNameBuilder.append(fgRouteChanEntity.getMailboxName().trim())));
        return mailBoxNameBuilder.toString();
    }

}
