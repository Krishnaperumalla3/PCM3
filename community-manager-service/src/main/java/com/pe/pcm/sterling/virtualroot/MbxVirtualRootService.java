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

package com.pe.pcm.sterling.virtualroot;

import com.pe.pcm.sterling.mailbox.SterlingMailboxService;
import com.pe.pcm.sterling.partner.sci.SterlingPartnerProfileService;
import com.pe.pcm.sterling.virtualroot.entity.MbxVirtualRootEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Kiran Reddy.
 */
@Service
public class MbxVirtualRootService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MbxVirtualRootService.class);

    private final MbxVirtualRootRepository mbxVirtualRootRepository;
    private final SterlingMailboxService sterlingMailboxService;

    @Autowired
    public MbxVirtualRootService(MbxVirtualRootRepository mbxVirtualRootRepository, SterlingMailboxService sterlingMailboxService) {
        this.mbxVirtualRootRepository = mbxVirtualRootRepository;
        this.sterlingMailboxService = sterlingMailboxService;
    }

    public void save(String userName, String mailbox, boolean mergeUser) {
        LOGGER.info("VirtualRoot Mailbox : {}", mailbox);
        if (!mergeUser) {
            //Need to do R&D
        }
        delete(userName);
        mbxVirtualRootRepository.save(new MbxVirtualRootEntity().setUserId(userName).setRootMailboxId(sterlingMailboxService.getMailboxId(mailbox)));
    }

    public void delete(String userName) {
        mbxVirtualRootRepository.deleteAllByUserId(userName);
    }

}
